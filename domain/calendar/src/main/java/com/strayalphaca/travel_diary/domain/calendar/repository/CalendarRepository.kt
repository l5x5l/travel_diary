package com.strayalphaca.travel_diary.domain.calendar.repository

import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar
import com.strayalphaca.domain.model.BaseResponse

interface CalendarRepository {
    suspend fun getDiaryData(year : Int, month : Int) : BaseResponse<List<DiaryInCalendar>>
    suspend fun checkWrittenOnToday() : BaseResponse<Boolean>
}