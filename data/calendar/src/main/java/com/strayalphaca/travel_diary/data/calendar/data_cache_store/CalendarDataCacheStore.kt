package com.strayalphaca.travel_diary.data.calendar.data_cache_store

import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar
import com.strayalphaca.travel_diary.domain.calendar.model.MonthCalendar
import kotlinx.coroutines.flow.Flow

interface CalendarDataCacheStore {
    suspend fun setCalendarData(year : Int, month : Int, dataList : List<DiaryInCalendar>)
    fun getCalendarData() : Flow<MonthCalendar>
    suspend fun addCalendarCell(data : DiaryInCalendar)
    suspend fun updateCalendarCell(data : DiaryInCalendar)
    suspend fun deleteCalendarCell(id : String)
    suspend fun clearCalendarData()
}