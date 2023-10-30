package com.strayalphaca.travel_diary.data.calendar.data_source

import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.data.calendar.models.CalendarDiaryDto
import java.text.DecimalFormat
import javax.inject.Inject

class CalendarTestDataSource @Inject constructor(): CalendarDataSource {
    override suspend fun getDiaryData(year: Int, month: Int): BaseResponse<List<CalendarDiaryDto>> {
        val decimalFormat = DecimalFormat("00")
        val formatMonth = decimalFormat.format(month)
        val diaryData = listOf(
            CalendarDiaryDto(id = "${year}_${formatMonth}_1", recordDate = "${year}/${formatMonth}.01", image = null).apply { dateStringFormat = "yyyy/MM.dd" },
            CalendarDiaryDto(id = "${year}_${formatMonth}_2", recordDate = "${year}/${formatMonth}.13", image = null).apply { dateStringFormat = "yyyy/MM.dd" },
            CalendarDiaryDto(id = "${year}_${formatMonth}_3", recordDate = "${year}/${formatMonth}.25", image = null).apply { dateStringFormat = "yyyy/MM.dd" }
        )

        return BaseResponse.Success(data = diaryData)
    }

    override suspend fun checkWrittenOnToday(): BaseResponse<Boolean> {
        return BaseResponse.Success(data = false)
    }
}