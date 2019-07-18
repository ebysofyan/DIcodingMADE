package ebysofyan.app.made.submission.common.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import ebysofyan.app.made.submission.R
import ebysofyan.app.made.submission.views.MainActivity



object NotificationUtil {

    @SuppressLint("WrongConstant")
    fun createNotification(
        context: Context,
        notificationChannelId: String,
        data: HashMap<String, String> = hashMapOf(),
        notificationId: Int = 1
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(
                    notificationChannelId,
                    "${context.getString(R.string.app_name)} channel",
                    NotificationManager.IMPORTANCE_MAX
                )

            notificationChannel.description = data["title"]
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 1000, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(context, notificationChannelId)
            .setTicker(data["body"])
            .setSmallIcon(R.mipmap.ic_launcher)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
            .setContentTitle(data["title"])
            .setContentText(data["body"])
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .build()

//        notificationManager.notify(notificationID.incrementAndGet(), notification)
        notificationManager.notify(notificationId, notification)
    }

}