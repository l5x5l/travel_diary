package com.strayalphaca.travel_diary.domain.calendar.utils

import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar
import java.util.Calendar

/**
 * 각 월마다 일의 총 개수를 리턴합니다.
 */
fun getDayAmountOfMonth(year : Int, month : Int) : Int {
    return when (month) {
        2 -> {
            if (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0)) { 28 }
            else { 29 }
        }
        in listOf(1,3,5,7,8,10,12) -> { 31 }
        else -> { 30 }
    }
}

fun fillEmptyCellToCalendarData(
    year : Int, month : Int, rawData : List<DiaryInCalendar>
) : List<DiaryInCalendar?> {
    var count = 0
    val dataList = (1 .. getDayAmountOfMonth(year, month)).toList().map {
        return@map if (count < rawData.size && rawData[count].day == it) {
            rawData[count++]
        } else {
            null
        }
    }
    return dataList
}



/**
 * 달력 상에서 이전달에 해당하는 요일의 리스트를 리턴합니다.
 */
fun lastDaysOfPrevMonth(year: Int, month: Int): List<Int> {
    val calendar = Calendar.getInstance()
    val prevMonthIndex = month - 1

    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, prevMonthIndex)
    calendar.set(Calendar.DAY_OF_MONTH, 1)

    val prevMonth = if (prevMonthIndex == 0) {
        12
    } else {
        month - 1
    }

    val startDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val amountOfDayOfPrevMonth = getDayAmountOfMonth(year, prevMonth)

    return List(startDayOfWeek - 1) { i -> amountOfDayOfPrevMonth - (startDayOfWeek - 2 - i) }
}

/**
 * 달력 상에서 다음달에 해당하는 요일의 리스트를 리턴합니다.
 */
fun firstDaysOfNextMonth(year : Int, month : Int) : List<Int> {
    val calendar = Calendar.getInstance()

    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month)
    calendar.set(Calendar.DAY_OF_MONTH, 1)

    val startDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

    return List(8 - startDayOfWeek) { it + 1 }
}