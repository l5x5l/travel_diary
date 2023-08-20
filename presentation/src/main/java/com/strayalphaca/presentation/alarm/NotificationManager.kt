package com.strayalphaca.presentation.alarm

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import kotlin.reflect.KClass

class NotificationManager {
    fun createNotification(
        context : Context, channelName : String,
        channelDescription : String ?= null,
        channelId : String, iconResourceId : Int,
        title : String, text : String,
        notificationId : Int,
        target : KClass<out Activity>,
        deepLink : String ?= null
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channelDescription?.let { channel.description = it }

            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(iconResourceId)
            setContentTitle(title)
            setContentText(text)
            deepLink?.let { link ->
                setContentIntent(createPendingIntent(context, target, link))
            }
            setAutoCancel(true)
        }.build()

        notificationManager.notify(notificationId, builder)
    }

    private fun createPendingIntent(context : Context, target : KClass<out Activity>, deepLink : String) : PendingIntent? {
        val resultIntent = Intent(context, target.java).apply { putExtra("deepLink", deepLink) }
        val resultPendingIntent : PendingIntent? = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }
        return resultPendingIntent
    }
}