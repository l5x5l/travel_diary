package com.strayalphaca.presentation.utils

import java.util.*

/**
 * 인자로 전달된 날자가 오늘인지 여부를 리턴합니다.
 */
fun checkToday(year : Int, month : Int, day : Int) : Boolean {
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH) + 1
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    return (year == currentYear && month == currentMonth && day == currentDay)
}

/**
 * 달력 상에서 이전달에 해당하는 요일의 리스트를 리턴합니다.
 */
fun lastDaysOfPrevMonth(year: Int, month: Int): List<Int> {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.DAY_OF_MONTH, 1)

    val startDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val amountOfDayOfPrevMonth = getDayAmountOfMonth(year, month - 1)

    return List(startDayOfWeek - 1) { i -> amountOfDayOfPrevMonth - (startDayOfWeek - 2 - i) }
}

/**
 * 달력 상에서 다음달에 해당하는 요일의 리스트를 리턴합니다.
 */
fun firstDaysOfNextMonth(용year : Int, month : Int) : List<Int> {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.MONTH, month)
    calendar.set(Calendar.DAY_OF_MONTH, 1)

    val startDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

    return List(8 - startDayOfWeek) { it + 1 }
}

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