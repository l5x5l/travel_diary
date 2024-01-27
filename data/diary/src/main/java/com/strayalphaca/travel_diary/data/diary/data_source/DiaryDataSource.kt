package com.strayalphaca.travel_diary.data.diary.data_source

import com.strayalphaca.travel_diary.core.data.model.DiaryDto
import com.strayalphaca.travel_diary.core.data.model.DiaryItemDto
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.diary.model.DiaryModifyData
import com.strayalphaca.travel_diary.diary.model.DiaryWriteData

interface DiaryDataSource {
    suspend fun getDiaryData(id : String) : BaseResponse<DiaryDto>
    suspend fun getDiaryList(cityId : Int, perPage : Int, offset : Int) : List<DiaryItemDto>
    suspend fun getDiaryListByCityGroup(cityGroupId : Int, perPage: Int, offset: Int) : List<DiaryItemDto>
    suspend fun uploadDiaryAndGetId(diaryWriteData : DiaryWriteData) : String
    suspend fun modifyDiary(diaryModifyData: DiaryModifyData)
    suspend fun removeDiary(id : String)
}