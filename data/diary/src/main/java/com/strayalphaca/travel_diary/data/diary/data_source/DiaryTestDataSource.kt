package com.strayalphaca.travel_diary.data.diary.data_source

import com.strayalphaca.travel_diary.core.data.model.DiaryDto
import com.strayalphaca.travel_diary.core.data.model.DiaryItemDto
import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.core.data.demo_data_source.DemoDataSource
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
}