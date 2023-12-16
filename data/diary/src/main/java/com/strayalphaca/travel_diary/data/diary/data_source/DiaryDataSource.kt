package com.strayalphaca.travel_diary.data.diary.data_source

import com.strayalphaca.travel_diary.core.data.model.DiaryDto
import com.strayalphaca.travel_diary.core.data.model.DiaryItemDto
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse

interface DiaryDataSource {
    suspend fun getDiaryData(id : String) : BaseResponse<DiaryDto>
    suspend fun getDiaryList(cityId : Int, perPage : Int, offset : Int) : List<DiaryItemDto>
    suspend fun getDiaryListByCityGroup(cityGroupId : Int, perPage: Int, offset: Int) : List<DiaryItemDto>
}