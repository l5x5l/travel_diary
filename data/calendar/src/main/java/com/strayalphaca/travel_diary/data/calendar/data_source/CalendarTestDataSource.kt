package com.strayalphaca.travel_diary.data.calendar.data_source

import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.core.data.demo_data_source.DemoDataSource
import com.strayalphaca.travel_diary.core.data.model.ImageDto
import com.strayalphaca.travel_diary.data.calendar.models.CalendarDiaryDto
import javax.inject.Inject

class CalendarTestDataSource @Inject constructor(
    private val demoDataSource: DemoDataSource
): CalendarDataSource {
    override suspend fun getDiaryData(year: Int, month: Int): BaseResponse<List<CalendarDiaryDto>> {
        val diaryData =
            demoDataSource.getDiaryListByMonth(year, month)
                .map { diaryDto ->
                    CalendarDiaryDto(
                        id = diaryDto.id,
                        image = diaryDto.medias.getOrNull(0)?.let { ImageDto(it.id, it.uploadedLink, it.shortLink) },
                        recordDate = diaryDto.date
                    )
                }

        return BaseResponse.Success(data = diaryData)
    }

    override suspend fun checkWrittenOnToday(): BaseResponse<Boolean> {
        return BaseResponse.Success(data = false)
    }
}