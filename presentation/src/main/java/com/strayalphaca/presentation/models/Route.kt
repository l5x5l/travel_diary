package com.strayalphaca.presentation.models

import com.strayalphaca.presentation.R

data class Route(val uri : String?, val screenNameId : Int) {
    companion object {
        val pushAlarmTargetList = listOf(
            Route(uri = "calendar", screenNameId = R.string.calendar_page),
            Route(uri = "map", screenNameId = R.string.map_page),
            Route(uri = "diary_write", screenNameId = R.string.diary_write_page),
            Route(uri = null, screenNameId = R.string.not_select),
        )
    }
}