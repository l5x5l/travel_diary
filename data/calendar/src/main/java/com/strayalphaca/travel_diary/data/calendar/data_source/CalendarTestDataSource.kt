package com.strayalphaca.travel_diary.data.calendar.data_source

import com.strayalphaca.data.all.model.DiaryDto
import com.strayalphaca.data.all.model.MediaFileInfoDto
import com.strayalphaca.domain.model.BaseResponse
import javax.inject.Inject

class CalendarTestDataSource @Inject constructor(): CalendarDataSource {
    override suspend fun getDiaryData(year: Int, month: Int): BaseResponse<List<DiaryDto>> {
        val diaryData = listOf(
            DiaryDto(id = "1", date = "2023/03.01",
                medias = listOf(MediaFileInfoDto(originName = "", shortLink = "",))),
            DiaryDto(id = "2", date = "2023/03.13",
                medias = listOf(MediaFileInfoDto(originName = "", shortLink = "",))),
            DiaryDto(id = "3", date = "2023/03.25",
                medias = listOf(MediaFileInfoDto(originName = "", shortLink = "",)))
        )


        return BaseResponse.Success(data = diaryData)
    }

    override suspend fun checkWrittenOnToday(): BaseResponse<Boolean> {
        return BaseResponse.Success(data = false)
    }
}