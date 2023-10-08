package com.strayalphaca.travel_diary.domain.calendar.model

import java.util.Calendar

data class MonthCalendar(
    val year : Int,
    val month : Int,
    val diaryList : List<DiaryInCalendar>
) {
    companion object {
        fun getInstanceFromCalendar(calendar : Calendar = Calendar.getInstance()) : MonthCalendar {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            return MonthCalendar(year = year, month = month, diaryList = emptyList())
        }
    }
}