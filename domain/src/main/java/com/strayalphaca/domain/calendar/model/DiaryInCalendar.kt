package com.strayalphaca.domain.calendar.model

data class DiaryInCalendar(
    val id : String,
    val day : Int = 1,
    val thumbnailUrl : String ?= null
)