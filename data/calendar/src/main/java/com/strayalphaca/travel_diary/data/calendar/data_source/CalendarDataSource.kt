package com.strayalphaca.travel_diary.data.calendar.data_source

import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.data.calendar.models.CalendarDiaryDto

interface CalendarDataSource {
    suspend fun getDiaryData(year : Int, month : Int) : BaseResponse<List<CalendarDiaryDto>>
    suspend fun checkWrittenOnToday() : BaseResponse<Boolean>
}