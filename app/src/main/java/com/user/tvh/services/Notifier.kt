package com.user.tvh.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.user.tvh.R


class Notifier(private val context: Context) {
    companion object {
        const val CHANNEL_ID = "tvh.notification.channel"
    }

    init {
        createNotificationChannel()
    }
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, "Notifications Channel", importance).apply {
                description = "Description"
            }
            with(NotificationManagerCompat.from(context)) {
                createNotificationChannel(channel)
            }
        }
    }

    fun push(title: String, message: String = "") {

//        val contentIntent = PendingIntent.getActivity(
//            context, 0,
//            Intent(context, MainActivity::class.java),
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )

        val notificationBuilder = NotificationCompat
            .Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
//            .setContentIntent(contentIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            val notificationId = java.util.Calendar.getInstance().time.time.toInt()
            notify(notificationId, notificationBuilder.build())
        }
    }
}