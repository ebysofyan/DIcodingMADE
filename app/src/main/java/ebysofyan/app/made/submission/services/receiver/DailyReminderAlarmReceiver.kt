package ebysofyan.app.made.submission.services.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import ebysofyan.app.made.submission.R
import ebysofyan.app.made.submission.common.utils.NotificationUtil
import ebysofyan.app.made.submission.views.MainActivity
import java.util.*


/**
 * Created by @ebysofyan on 7/13/19
 */
class DailyReminderAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val data = hashMapOf(
            "title" to context?.getString(R.string.daily_reminder_msg_title).toString(),
            "body" to context?.getString(R.string.daily_reminder_msg_body).toString(),
            "large_icon" to ""
        )
        if (context != null)
            NotificationUtil.createNotification(
                context,
                context.getString(R.string.app_name).toString(),
                data,
                11
            )
    }

    private fun getPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        return PendingIntent.getBroadcast(context, 1011, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }

    fun scheduleAlarm(context: Context?) {
        cancelAlarm(context)
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)
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