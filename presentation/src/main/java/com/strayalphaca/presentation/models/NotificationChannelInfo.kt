package com.strayalphaca.presentation.models

import com.strayalphaca.presentation.R

data class NotificationChannelInfo(
    val id : String,
    val nameResourceId : Int,
    val description : String?
) {
    companion object {
        val DailyNotification = NotificationChannelInfo(
            id = "201",
            nameResourceId = R.string.notification_channel_name,
            description = null
        )
    }
}