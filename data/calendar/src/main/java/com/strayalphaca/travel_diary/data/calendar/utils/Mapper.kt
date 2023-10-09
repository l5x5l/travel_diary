package com.strayalphaca.travel_diary.data.calendar.utils

import com.strayalphaca.data.all.model.DiaryDto
import com.strayalphaca.domain.all.DiaryDate
import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar

fun diaryDtoToDiaryInCalendar(diaryDto: DiaryDto) : DiaryInCalendar {
    val dateString = diaryDto.date
    val diaryDate = DiaryDate.getInstanceFromDateStringBySimpleDateFormat(dateString, diaryDto.dateStringFormat ?: "yyyy-MM-dd")

    return DiaryInCalendar(
        id = diaryDto.id,
        date = diaryDate,
        thumbnailUrl = diaryDto.medias[0].shortLink,
    )
}

fun diaryDtoListToDiaryInCalendarList(diaryDtoList : List<DiaryDto>) : List<DiaryInCalendar> {
    return diaryDtoList.map { diaryDtoToDiaryInCalendar(it) }
}