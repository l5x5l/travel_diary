package com.strayalphaca.travel_diary.data.calendar.data_source

import com.strayalphaca.data.all.model.DiaryDto
import com.strayalphaca.data.all.model.MediaFileInfoDto
import com.strayalphaca.domain.model.BaseResponse
import javax.inject.Inject

class CalendarTestDataSource @Inject constructor(): CalendarDataSource {
    override suspend fun getDiaryData(year: Int, month: Int): BaseResponse<List<DiaryDto>> {
        val diaryData = listOf(
            DiaryDto(id = "${year}_${month}_1", date = "${year}/${month}.01",
                medias = listOf(MediaFileInfoDto(originName = "", shortLink = "",))).apply { dateStringFormat = "yyyy/MM.dd" },
            DiaryDto(id = "${year}_${month}_2", date = "${year}/${month}.13",
                medias = listOf(MediaFileInfoDto(originName = "", shortLink = "",))).apply { dateStringFormat = "yyyy/MM.dd" },
            DiaryDto(id = "${year}_${month}_3", date = "${year}/${month}.25",
                medias = listOf(MediaFileInfoDto(originName = "", shortLink = "",))).apply { dateStringFormat = "yyyy/MM.dd" }
        )

        return BaseResponse.Success(data = diaryData)
    }

    override suspend fun checkWrittenOnToday(): BaseResponse<Boolean> {
        return BaseResponse.Success(data = false)
    }
}