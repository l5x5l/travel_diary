package com.strayalphaca.presentation.models

import com.strayalphaca.presentation.R

sealed class Route(val uri : String?, val screenNameId : Int) {
    object Calendar : Route(uri = "${scheme}://${host}?calendar", screenNameId = R.string.calendar_page)
    object Map : Route(uri = "${scheme}://${host}?map", screenNameId = R.string.map_page)
    object DiaryWrite : Route(uri = "${scheme}://${host}?diary_write", screenNameId = R.string.diary_write_page)
    object Null : Route(uri = null, screenNameId = R.string.not_select)
    companion object {
        val pushAlarmTargetList = listOf(
            Calendar, Map, DiaryWrite, Null
        )

        private const val host = "notification"
        private const val scheme = "traily"

        fun getInstanceByUriString(uriString : String?) : Route {
            return when (uriString) {
                Calendar.uri -> Calendar
                Map.uri -> Map
                DiaryWrite.uri -> DiaryWrite
                else -> Null
            }
        }
    }
}