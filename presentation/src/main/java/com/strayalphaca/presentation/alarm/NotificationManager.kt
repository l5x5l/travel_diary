package com.strayalphaca.presentation.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.strayalphaca.presentation.models.NotificationChannelInfo

class NotificationManager {

    fun initNotificationChannel(context : Context, notificationChannelInfo: NotificationChannelInfo) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelInfo.id,
                context.getString(notificationChannelInfo.nameResourceId),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannelInfo.description?.let { channel.description = it }

            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createNotification(
        context : Context,
        channelId : String, iconResourceId : Int,
        title : String, text : String,
        notificationId : Int,
        deepLink : String ?= null
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(iconResourceId)
            setContentTitle(title)
            setContentText(text)
            deepLink?.let { link ->
                setContentIntent(createPendingIntent(context, link))
            }
            setAutoCancel(true)
        }.build()

        notificationManager.notify(notificationId, builder)
    }

    private fun createPendingIntent(context : Context, deepLink : String): PendingIntent? {
        val routeIntent = Intent(Intent.ACTION_VIEW, deepLink.toUri()).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        return PendingIntent.getActivity(context, 0, routeIntent, flags)
    }
}