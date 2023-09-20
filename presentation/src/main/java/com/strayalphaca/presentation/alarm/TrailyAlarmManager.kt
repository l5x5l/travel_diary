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
    fun cancelAlarm() {
        val intent = Intent(context, TrailyAlarmReceiver::class.java)
        intent.action = CALL_NOTIFICATION
        val alarmManager = getAlarmManager()
        val pendingIntent = getAlarmPendingIntent(intent)
        alarmManager.cancel(pendingIntent)
    }

    fun setAlarm(hour: Int, minute: Int, screenDeepLink: String?) {
        cancelAlarm()
        val intent = Intent(context, TrailyAlarmReceiver::class.java)
        intent.action = CALL_NOTIFICATION
        intent.putExtra("deepLink", screenDeepLink)

        val alarmManager = getAlarmManager()
        val pendingIntent = getAlarmPendingIntent(intent)
        val nextAlarmTime = getNextAlarmTimeMilli(hour, minute)

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextAlarmTime, pendingIntent)
    }

    private fun getAlarmManager() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private fun getAlarmPendingIntent(intent : Intent) : PendingIntent {
        val flags = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
        return PendingIntent.getBroadcast(
            context, NOTIFICATION_ID, intent, flags
        )
    }

    private fun getNextAlarmTimeMilli(hour : Int, minute : Int) : Long {
        val alarmTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }.timeInMillis

        // 10분 전에 설정한 경우, 그날은 알람이 발생하지 않음
        val graceTimeMilli = 1000 * 60 * 10
        val currentTime = Calendar.getInstance().timeInMillis + graceTimeMilli

        return if (alarmTime < currentTime) {
            alarmTime + AlarmManager.INTERVAL_DAY
        } else {
            alarmTime
        }
    }
}