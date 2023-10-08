package com.strayalphaca.travel_diary.domain.calendar.model

import com.strayalphaca.domain.all.DiaryDate

data class DiaryInCalendar(
    val id : String,
    val date : DiaryDate,
    val thumbnailUrl : String ?= null
) {
    val day : Int = date.day
}
