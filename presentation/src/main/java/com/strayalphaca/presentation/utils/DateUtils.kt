package com.strayalphaca.presentation.utils

import com.strayalphaca.domain.all.getDayAmountOfMonth
import java.util.*

/**
 * 인자로 전달된 날자가 오늘인지 여부를 리턴합니다.
 * @param year 비교대상 날짜의 연도
 * @param month 비교대상 하는 날짜의 월
 * @param day 비교대상 하는 날짜의 일
 * @param calendar 비교하고자 하는 날짜의 calendar 객체, 기본값은 현재 시간을 기준으로 생성된 calendar 객체를 사용합니다.
 */
fun compareDate(year : Int, month : Int, day : Int, calendar : Calendar = Calendar.getInstance()) : Boolean {
    val targetYear = calendar.get(Calendar.YEAR)
    val targetMonth = calendar.get(Calendar.MONTH) + 1
    val targetDay = calendar.get(Calendar.DAY_OF_MONTH)

    return (year == targetYear && month == targetMonth && day == targetDay)
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

    val startDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val amountOfDayOfPrevMonth = getDayAmountOfMonth(year, month - 1)

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