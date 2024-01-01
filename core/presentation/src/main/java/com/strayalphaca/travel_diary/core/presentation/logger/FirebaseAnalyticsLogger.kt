package com.strayalphaca.travel_diary.core.presentation.logger

import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAnalyticsLogger @Inject constructor() : ScreenLogger, UserEventLogger, LoggerLibManager<FirebaseAnalytics> {
    private var firebaseAnalytics : FirebaseAnalytics? = null

    override fun attachLogger(loggerObj: FirebaseAnalytics) {
        firebaseAnalytics = loggerObj
    }

    override fun detachLogger() {
        firebaseAnalytics = null
    }

    override fun log(event: ScreenLogEvent) {
        firebaseAnalytics?.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, event.route)
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "RootActivity")
        }
    }

    override fun log(event: UserLogEvent) {
        val bundle = getEventParamBundle(event)
        firebaseAnalytics?.logEvent(event.eventName, bundle)
    }

    private fun getEventParamBundle(event : UserLogEvent) : Bundle {
        return when (event) {
            is UserLogEvent.ChangeMonth -> {
                bundleOf(
                    "year" to event.year,
                    "month" to event.month
                )
            }
            is UserLogEvent.CreateDiary -> {
                bundleOf(
                    "image_count" to event.imageCount,
                    "has_voice_file" to event.voiceFileExist,
                    "location_name" to event.locationName
                )
            }
            is UserLogEvent.ModifyDiary -> {
                bundleOf(
                    "image_count" to event.imageCount,
                    "has_voice_file" to event.voiceFileExist,
                    "location_name" to event.locationName
                )
            }
            else -> {
                bundleOf()
            }
        }
    }
}