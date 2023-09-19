package com.strayalphaca.presentation.models

import com.strayalphaca.presentation.R

sealed class Route(val uri : String?, val screenNameId : Int) {
    object Calendar : Route(uri = "calendar", screenNameId = R.string.calendar_page)
    object Map : Route(uri = "map", screenNameId = R.string.map_page)
    object DiaryWrite : Route(uri = "diary_write", screenNameId = R.string.diary_write_page)
    object Null : Route(uri = null, screenNameId = R.string.not_select)
    companion object {
        val pushAlarmTargetList = listOf(
            Calendar, Map, DiaryWrite, Null
        )

        fun getInstanceByUriString(uriString : String?) : Route {
            return when (uriString) {
                "calendar" -> Calendar
                "map" -> Map
                "diary_write" -> DiaryWrite
                else -> Null
            }
        }
    }
}