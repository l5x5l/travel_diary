package com.strayalphaca.travel_diary.data.calendar.utils

import com.strayalphaca.data.all.model.DiaryDto
import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar

fun diaryDtoToDiaryInCalendar(diaryDto: DiaryDto) : DiaryInCalendar {
    val dateString = diaryDto.date
    val day = dateString.split(".")[1].toIntOrNull()

    return DiaryInCalendar(
        id = diaryDto.id,
        day = day ?: 1,
        thumbnailUrl = diaryDto.medias[0].shortLink,
    )
}

fun diaryDtoListToDiaryInCalendarList(diaryDtoList : List<DiaryDto>) : List<DiaryInCalendar> {
    return diaryDtoList.map { diaryDtoToDiaryInCalendar(it) }
}