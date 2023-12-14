package com.strayalphaca.travel_diary.data.diary.data_source

import com.strayalphaca.travel_diary.core.data.model.CityDto
import com.strayalphaca.travel_diary.core.data.model.DiaryDto
import com.strayalphaca.travel_diary.core.data.model.DiaryItemDto
import com.strayalphaca.travel_diary.core.data.model.MediaFileInfoDto
import com.strayalphaca.domain.model.BaseResponse
import kotlinx.coroutines.delay
import javax.inject.Inject

class DiaryTestDataSource @Inject constructor() : DiaryDataSource {
    override suspend fun getDiaryData(id: String): BaseResponse<DiaryDto> {
        delay(1000L)

        val diary = DiaryDto(
            id = "1", date = "2023/03.01",
            medias = listOf(
                MediaFileInfoDto(originName = "1", shortLink = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", type = "video")
            )).apply { dateStringFormat = "yyyy/MM.dd" }

        return BaseResponse.Success(diary)
    }

    override suspend fun getDiaryList(cityId: Int, perPage: Int, offset: Int): List<DiaryItemDto> {
        delay(1000L)
        if (offset >= 5) return emptyList()

        val result = mutableListOf<DiaryItemDto>()

        for (i in 0 until perPage) {
            result.add(DiaryItemDto("${offset * perPage + i}", null, CityDto(id=cityId, name=""), provinceId = 1))
        }

        return result
    }

    override suspend fun getDiaryListByCityGroup(
        cityGroupId: Int,
        perPage: Int,
        offset: Int
    ): List<DiaryItemDto> {
        delay(1000L)
        if (offset >= 5) return emptyList()
        val result = mutableListOf<DiaryItemDto>()

        for (i in 0 until perPage) {
            result.add(DiaryItemDto("${offset * perPage + i}", null, CityDto(id=1, name=""), provinceId = 1))
        }

        return result
    }
}