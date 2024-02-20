package com.strayalphaca.travel_diary

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.strayalphaca.presentation.alarm.NotificationManager
import com.strayalphaca.presentation.models.NotificationChannelInfo
import com.strayalphaca.travel_diary.core.presentation.logger.LoggerLibManager
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application(), Configuration.Provider {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    @Inject lateinit var loggerLibManager: LoggerLibManager<FirebaseAnalytics>
    @Inject lateinit var workerFactory : HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()

        firebaseAnalytics = Firebase.analytics
        loggerLibManager.attachLogger(firebaseAnalytics)
        NotificationManager().initNotificationChannel(baseContext, NotificationChannelInfo.DailyNotification)

        setFileCleanerWorker()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    private fun setFileCleanerWorker() {
        val fileClearWorkRequest = PeriodicWorkRequestBuilder<FileCleanerWorker>(24, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "localFileCleaner",
            ExistingPeriodicWorkPolicy.KEEP,
            fileClearWorkRequest
        )
    }
}