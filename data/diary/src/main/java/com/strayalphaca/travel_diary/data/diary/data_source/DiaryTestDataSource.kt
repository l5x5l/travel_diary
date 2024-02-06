package com.strayalphaca.travel_diary.data.diary.data_source

import com.strayalphaca.travel_diary.core.data.model.DiaryDto
import com.strayalphaca.travel_diary.core.data.model.DiaryItemDto
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.core.data.demo_data_source.DemoDataSource
import com.strayalphaca.travel_diary.core.data.model.MediaFileInfoDto
import com.strayalphaca.travel_diary.core.data.model.VoiceFileInDiaryDto
import com.strayalphaca.travel_diary.diary.model.DiaryModifyData
import com.strayalphaca.travel_diary.diary.model.DiaryWriteData
import kotlinx.coroutines.delay
import javax.inject.Inject

class DiaryTestDataSource @Inject constructor(
    private val demoDataSource: DemoDataSource
) : DiaryDataSource {
    override suspend fun getDiaryData(id: String): BaseResponse<DiaryDto> {
        delay(1000L)

        val diary = demoDataSource.getDiaryDetail(id)
        return BaseResponse.Success(diary)
    }

    override suspend fun getDiaryList(cityId: Int, perPage: Int, offset: Int): List<DiaryItemDto> {
        delay(1000L)
        return demoDataSource.getDiaryList(cityId, perPage, offset)
    }

    override suspend fun getDiaryListByCityGroup(
        cityGroupId: Int,
        perPage: Int,
        offset: Int
    ): List<DiaryItemDto> {
        delay(1000L)
        return demoDataSource.getDiaryListByCityGroup(cityGroupId, perPage, offset)
    }

    override suspend fun uploadDiaryAndGetId(diaryWriteData: DiaryWriteData) : String {
        val diaryDto = diaryWriteData.run {
            DiaryDto(
                id = "-",
                date = recordDate.toString(),
                feeling = feeling.toString(),
                weather = weather.toString(),
                content = content,
                medias = medias?.map { MediaFileInfoDto(id = it, originName = it, uploadedLink = it, thumbnailLink = it) } ?: emptyList(),
                voice = voice?.let { VoiceFileInDiaryDto(it, it, null) },
                createdAt = "",
                cityId = cityId,
                place = null
            )
        }
        return demoDataSource.addDiaryAndGetID(diaryDto)
    }

    override suspend fun modifyDiary(diaryModifyData: DiaryModifyData) {
        val diaryDto = diaryModifyData.run {
            DiaryDto(
                id = id,
                date = date.toString(),
                feeling = feeling.toString(),
                weather = weather.toString(),
                content = content ?: "-",
                medias = medias?.map { MediaFileInfoDto(id = it, originName = it, uploadedLink = it, thumbnailLink = it) } ?: emptyList(),
                voice = voice?.let { VoiceFileInDiaryDto(it, it, null) },
                createdAt = "",
                cityId = cityId,
                place = cityName
            )
        }
        demoDataSource.modifyDiary(diaryDto)
    }

    override suspend fun removeDiary(id: String) {
        demoDataSource.deleteDiary(id)
    }

    override suspend fun getDiaryCount(): Int {
        return 100
    }
}