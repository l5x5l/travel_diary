package com.strayalphaca.travel_diary.domain.calendar.utils

import com.strayalphaca.domain.all.getDayAmountOfMonth
import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar

fun fillEmptyCellToCalendarData(
    year : Int, month : Int, rawData : List<DiaryInCalendar>
) : List<DiaryInCalendar?> {
    var count = 0
    val dataList = (0 until getDayAmountOfMonth(year, month)).toList().map {
        return@map if (count < rawData.size && rawData[count].day == it) {
            rawData[count++]
        } else {
            null
        }
    }
    return dataList
}