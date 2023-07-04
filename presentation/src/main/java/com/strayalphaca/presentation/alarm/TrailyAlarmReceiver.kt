package com.strayalphaca.presentation.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.strayalphaca.presentation.R

class TrailyAlarmReceiver : BroadcastReceiver() {
    private val notificationManager = NotificationManager()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent?.action == CALL_NOTIFICATION) {
            notificationManager.createNotification(
                context = context,
                channelName = context.getString(R.string.notification_channel_name),
                channelId = CHANNEL_ID,
                channelDescription = null,
                iconResourceId = R.drawable.ic_logo,
                title = context.getString(R.string.notification_title),
                text = context.getString(R.string.notification_text),
                notificationId = NOTIFICATION_ID,
                target = null
            )
        }
    }

    companion object {
        const val CALL_NOTIFICATION = "traily-call-notification"
        const val CHANNEL_ID = "201"
        const val NOTIFICATION_ID = 201
    }
}