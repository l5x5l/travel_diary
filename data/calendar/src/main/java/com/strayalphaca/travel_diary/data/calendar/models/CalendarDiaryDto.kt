package com.strayalphaca.travel_diary.data.calendar.models

import com.strayalphaca.travel_diary.core.data.model.ImageDto
import com.strayalpaca.travel_diary.core.domain.model.DiaryDate
import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar

data class CalendarDiaryDto(
    val id : String,
    val image : ImageDto?,
    val recordDate : String
) {
    var dateStringFormat : String = "yyyy-MM-dd"

    fun toDiaryInCalendar() : DiaryInCalendar {
        return DiaryInCalendar(
            id = id,
            thumbnailUrl = image?.shortLink ?: image?.uploadedLink,
            date = DiaryDate.getInstanceFromDateStringBySimpleDateFormat(recordDate, dateStringFormat)
        )
    }
}