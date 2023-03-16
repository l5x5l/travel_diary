package com.strayalphaca.data.calendar.date_source

import com.strayalphaca.data.all.model.DiaryDto
import com.strayalphaca.domain.model.BaseResponse

interface CalendarDataSource {
    suspend fun getDiaryData(year : Int, month : Int) : BaseResponse<List<DiaryDto>>
}