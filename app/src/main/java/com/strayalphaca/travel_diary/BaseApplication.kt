package com.strayalphaca.travel_diary

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.strayalphaca.presentation.alarm.NotificationManager
import com.strayalphaca.presentation.models.NotificationChannelInfo
import com.strayalphaca.travel_diary.core.presentation.logger.LoggerLibManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    @Inject lateinit var loggerLibManager: LoggerLibManager<FirebaseAnalytics>

    override fun onCreate() {
        super.onCreate()

        firebaseAnalytics = Firebase.analytics
        loggerLibManager.attachLogger(firebaseAnalytics)
        NotificationManager().initNotificationChannel(baseContext, NotificationChannelInfo.DailyNotification)
    }
}