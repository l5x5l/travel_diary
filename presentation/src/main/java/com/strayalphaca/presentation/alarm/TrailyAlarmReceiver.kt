package com.strayalphaca.presentation.alarm

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.models.NotificationChannelInfo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.reflect.KClass

@AndroidEntryPoint
class TrailyAlarmReceiver : BroadcastReceiver() {
    private val notificationManager = NotificationManager()
    @Inject lateinit var rootActivity : KClass<out Activity>

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent?.action == CALL_NOTIFICATION) {
            notificationManager.createNotification(
                context = context,
                channelId = NotificationChannelInfo.DailyNotification.id,
                iconResourceId = R.drawable.ic_logo,
                title = context.getString(R.string.notification_title),
                text = context.getString(R.string.notification_text),
                notificationId = NOTIFICATION_ID,
                target = rootActivity,
                deepLink = intent.getStringExtra("deepLink")
            )
        }
    }

    companion object {
        const val CALL_NOTIFICATION = "traily-call-notification"
        const val NOTIFICATION_ID = 201
    }
}