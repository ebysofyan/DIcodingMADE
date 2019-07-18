package ebysofyan.app.made.submission.services.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import ebysofyan.app.made.submission.R
import ebysofyan.app.made.submission.common.extensions.forceLocale2to1
import ebysofyan.app.made.submission.common.extensions.toDateFormat
import ebysofyan.app.made.submission.common.utils.Constants
import ebysofyan.app.made.submission.common.utils.NetworkConfig
import ebysofyan.app.made.submission.common.utils.NotificationUtil
import ebysofyan.app.made.submission.data.server.BaseResponse
import ebysofyan.app.made.submission.data.server.Movie
import ebysofyan.app.made.submission.repository.services.MovieService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


/**
 * Created by @ebysofyan on 7/13/19
 */

class ReleaseTodayAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        requestTodayRelease(context)
    }

    private fun requestTodayRelease(context: Context?) {
        val queryMap =
            hashMapOf(
                "language" to context?.forceLocale2to1().toString(),
                "primary_release_date.gte" to Date().toDateFormat("yyyy-MM-dd"),
                "primary_release_date.lte" to Date().toDateFormat("yyyy-MM-dd"),
                "release_date.gte" to Date().toDateFormat("yyyy-MM-dd"),
                "release_date.lte" to Date().toDateFormat("yyyy-MM-dd")
            )

        val request = NetworkConfig.client.create(MovieService::class.java).fetchMovies(queryMap = queryMap)
        request.enqueue(object : Callback<BaseResponse<Movie>> {
            override fun onFailure(call: Call<BaseResponse<Movie>>, t: Throwable) {}

            override fun onResponse(call: Call<BaseResponse<Movie>>, response: Response<BaseResponse<Movie>>) {
                if (response.isSuccessful) {
                    response.body()?.results?.sortedByDescending { it.popularity }
                    response.body()?.results?.subList(0, 4)?.forEach { movie ->
                        showNotification(context, movie)
                        Thread.sleep(2000)
                    }
                }
            }
        })
    }

    private fun showNotification(context: Context?, movie: Movie) {
        val data = hashMapOf(
            "title" to "${context?.getString(R.string.NEW).toString()} : ${movie.title}",
            "body" to movie.overview,
            "large_icon" to Constants.getImageUrl(fileName = movie.posterPath)
        )
        if (context != null)
            NotificationUtil.createNotification(
                context,
                context.getString(R.string.app_name).toString(),
                data,
                movie.id.toInt()
            )
    }

    private fun getPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, ReleaseTodayAlarmReceiver::class.java)
        return PendingIntent.getBroadcast(context, 1011, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }

    fun scheduleAlarm(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 20)
        calendar.set(Calendar.MINUTE, 10)
        calendar.set(Calendar.SECOND, 0)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                getPendingIntent(context)
            )
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                getPendingIntent(context)
            )
        }
    }

    fun cancelAlarm(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(getPendingIntent(context))
    }
}