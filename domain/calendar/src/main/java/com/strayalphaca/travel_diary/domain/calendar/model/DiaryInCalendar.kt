package com.strayalphaca.travel_diary.domain.calendar.model

import com.strayalpaca.travel_diary.core.domain.model.DiaryDate

data class DiaryInCalendar(
    val id : String,
    val date : DiaryDate,
    val thumbnailUrl : String ?= null
) {
    val day : Int = date.day
}
