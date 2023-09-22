package com.strayalphaca.travel_diary

import android.app.Application
import com.strayalphaca.presentation.alarm.NotificationManager
import com.strayalphaca.presentation.models.NotificationChannelInfo
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationManager().initNotificationChannel(baseContext, NotificationChannelInfo.DailyNotification)
    }
}