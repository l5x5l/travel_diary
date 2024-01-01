package com.strayalphaca.travel_diary.di

import com.google.firebase.analytics.FirebaseAnalytics
import com.strayalphaca.travel_diary.core.presentation.logger.FirebaseAnalyticsLogger
import com.strayalphaca.travel_diary.core.presentation.logger.LoggerLibManager
import com.strayalphaca.travel_diary.core.presentation.logger.ScreenLogger
import com.strayalphaca.travel_diary.core.presentation.logger.UserEventLogger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LoggerModule {
    @Binds
    abstract fun bindScreenLogger(firebaseScreenLogger : FirebaseAnalyticsLogger) : ScreenLogger

    @Binds
    abstract fun bindUserEventLogger(firebaseUserEventLogger: FirebaseAnalyticsLogger) : UserEventLogger

    @Binds
    abstract fun bindLoggerLibManager(firebaseLogger : FirebaseAnalyticsLogger) : LoggerLibManager<FirebaseAnalytics>
}