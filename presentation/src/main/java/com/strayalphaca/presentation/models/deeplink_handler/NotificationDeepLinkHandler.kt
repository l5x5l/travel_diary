package com.strayalphaca.presentation.models.deeplink_handler

import com.strayalphaca.presentation.models.Route
import com.strayalphaca.presentation.models.event_flow.MutableEventFlow
import com.strayalphaca.presentation.models.event_flow.asEventFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationDeepLinkHandler @Inject constructor() {
    private val _deepLinkEvent = MutableEventFlow<DeepLinkEvent>()
    val deepLinkEvent = _deepLinkEvent.asEventFlow()

    suspend fun setDeepLinkEvent(deepLink : String) {
        when(deepLink) {
            Route.DiaryWrite.uri -> {
                _deepLinkEvent.emit(DeepLinkEvent.WRITE_DIARY)
            }
        }
    }
}

enum class DeepLinkEvent {
    WRITE_DIARY
}