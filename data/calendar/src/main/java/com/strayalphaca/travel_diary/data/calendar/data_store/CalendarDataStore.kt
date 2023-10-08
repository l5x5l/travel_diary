package com.strayalphaca.travel_diary.data.calendar.data_store

import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar
import com.strayalphaca.travel_diary.domain.calendar.model.MonthCalendar
import kotlinx.coroutines.flow.Flow

interface CalendarDataStore {
    suspend fun setCalendarData(year : Int, month : Int, dataList : List<DiaryInCalendar>)
    fun getCalendarData() : Flow<MonthCalendar>
    suspend fun addCalendarCell(data : DiaryInCalendar)
    suspend fun updateCalendarCell(data : DiaryInCalendar)
    suspend fun deleteCalendarCell(data : DiaryInCalendar)
    suspend fun clearCalendarData()
}