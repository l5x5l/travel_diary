package com.strayalphaca.presentation.alarm

import android.app.Activity
import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.models.NotificationChannelInfo
import com.strayalphaca.travel_diary.domain.alarm.usecase.UseCaseGetAlarmInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.reflect.KClass

@AndroidEntryPoint
class TrailyAlarmReceiver : BroadcastReceiver() {
    private val notificationManager = NotificationManager()
    @Inject lateinit var rootActivity : KClass<out Activity>
    @Inject lateinit var useCaseGetAlarmInfo: UseCaseGetAlarmInfo
    @Inject lateinit var trailyAlarmManager: TrailyAlarmManager

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent?.action == null) return

        when (intent.action) {
            CALL_NOTIFICATION -> {
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

                createNextAlarm()
            }
            AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED -> {
                if (checkCanUseScheduleExactAlarms(context)) {
                    createNextAlarm()
                } else {
                    trailyAlarmManager.cancelAlarm()
                }
            }
            Intent.ACTION_BOOT_COMPLETED -> {
                createNextAlarm()
            }
        }
    }

    private fun createNextAlarm() = goAsync {
        val alarmInfo = useCaseGetAlarmInfo.invoke().first()

        if (!alarmInfo.alarmOn) return@goAsync

        trailyAlarmManager.cancelAlarm()
        trailyAlarmManager.setAlarm(alarmInfo.hour, alarmInfo.minute, alarmInfo.routeLink)
    }
    private fun goAsync(
        context : CoroutineContext = EmptyCoroutineContext,
        block : suspend CoroutineScope.() -> Unit
    ) {
        val pendingResult = goAsync()
        @OptIn(DelicateCoroutinesApi::class)
        GlobalScope.launch(context) {
            try {
                block()
            } finally {
                pendingResult.finish()
            }
        }
    }

    private fun checkCanUseScheduleExactAlarms(context : Context) : Boolean {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        return (Build.VERSION.SDK_INT < Build.VERSION_CODES.S || alarmManager.canScheduleExactAlarms())
    }

    companion object {
        const val CALL_NOTIFICATION = "traily-call-notification"
        const val NOTIFICATION_ID = 201
    }
}