package com.strayalphaca.data.diary.data_source

import com.strayalphaca.data.all.model.DiaryDto
import com.strayalphaca.data.all.model.DiaryItemDto
import com.strayalphaca.domain.model.BaseResponse

interface DiaryDataSource {
    suspend fun getDiaryData(id : String) : BaseResponse<DiaryDto>
    suspend fun getDiaryList(cityId : Int, perPage : Int, offset : Int) : List<DiaryItemDto>
    suspend fun getDiaryListByCityGroup(cityGroupId : Int, perPage: Int, offset: Int) : List<DiaryItemDto>
}