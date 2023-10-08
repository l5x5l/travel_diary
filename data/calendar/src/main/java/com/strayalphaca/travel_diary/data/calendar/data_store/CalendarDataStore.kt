package com.strayalphaca.travel_diary.data.calendar.data_store

import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar

interface CalendarDataStore {
    suspend fun setCalendarData(dataList : List<DiaryInCalendar>)
    suspend fun getCalendarData() : List<DiaryInCalendar>
    suspend fun updateCalendarCell(data : DiaryInCalendar)
    suspend fun deleteCalendarCell(data : DiaryInCalendar)
    suspend fun clearCalendarData()
}