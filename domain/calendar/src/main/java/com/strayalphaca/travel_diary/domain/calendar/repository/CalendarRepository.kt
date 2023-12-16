package com.strayalphaca.travel_diary.domain.calendar.repository

import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar
import com.strayalphaca.travel_diary.domain.calendar.model.MonthCalendar
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {
    suspend fun loadDiaryData(year : Int, month : Int) : BaseResponse<Nothing>
    suspend fun checkWrittenOnToday() : BaseResponse<Boolean>
    fun getMonthCalendarFlow() : Flow<MonthCalendar>
    suspend fun updateCachedDiaryInCalendar(diaryInCalendar: DiaryInCalendar)
    suspend fun deleteCachedDiaryInCalendar(id : String)
    suspend fun addCachedDiaryInCalendar(diaryInCalendar: DiaryInCalendar)
}