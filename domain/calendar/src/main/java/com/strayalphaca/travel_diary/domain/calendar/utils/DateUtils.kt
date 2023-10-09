package com.strayalphaca.travel_diary.domain.calendar.utils

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