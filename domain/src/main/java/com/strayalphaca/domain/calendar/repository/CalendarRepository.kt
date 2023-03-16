package com.strayalphaca.domain.calendar.repository

import com.strayalphaca.domain.calendar.model.DiaryInCalendar
import com.strayalphaca.domain.model.BaseResponse

interface CalendarRepository {
    suspend fun getDiaryData(year : Int, month : Int) : BaseResponse<List<DiaryInCalendar>>
}