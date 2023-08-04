package com.strayalphaca.presentation.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import com.strayalphaca.presentation.alarm.TrailyAlarmReceiver.Companion.CALL_NOTIFICATION
import com.strayalphaca.presentation.alarm.TrailyAlarmReceiver.Companion.NOTIFICATION_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TrailyAlarmManager @Inject constructor(
    @ApplicationContext private val context : Context
) {
    fun setAlarmOff() {
        val intent = Intent(context, TrailyAlarmReceiver::class.java)
        intent.action = CALL_NOTIFICATION
        val alarmManager = getAlarmManager()
        val pendingIntent = getAlarmPendingIntent(intent)
        alarmManager.cancel(pendingIntent)
    }

    fun setAlarm(hour: Int, minute: Int, screenDeepLink: String?) {
        setAlarmOff()
        val intent = Intent(context, TrailyAlarmReceiver::class.java)
        intent.action = CALL_NOTIFICATION
        intent.putExtra("deepLink", screenDeepLink)

        val alarmManager = getAlarmManager()
        val pendingIntent = getAlarmPendingIntent(intent)

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    private fun getAlarmManager() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private fun getAlarmPendingIntent(intent : Intent) : PendingIntent {
        val flags = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        return PendingIntent.getBroadcast(
            context, NOTIFICATION_ID, intent, flags
        )
    }
}