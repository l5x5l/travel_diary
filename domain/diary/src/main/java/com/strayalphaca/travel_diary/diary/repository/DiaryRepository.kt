package com.strayalphaca.travel_diary.diary.repository

import com.strayalphaca.travel_diary.diary.model.DiaryDetail
import com.strayalphaca.travel_diary.diary.model.DiaryItem
import com.strayalphaca.travel_diary.diary.model.DiaryModifyData
import com.strayalphaca.travel_diary.diary.model.DiaryWriteData
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.diary.model.DiaryItemUpdate
import kotlinx.coroutines.flow.Flow

interface DiaryRepository {
    suspend fun getDiaryDetail(id : String) : BaseResponse<DiaryDetail>
    suspend fun getDiaryList(cityId : Int, perPage : Int, offset : Int) : List<DiaryItem>
    suspend fun getDiaryListByCityGroup(cityGroupId : Int, perPage : Int, offset : Int) : List<DiaryItem>

    suspend fun uploadDiary(diaryWriteData: DiaryWriteData) : BaseResponse<String>
    suspend fun modifyDiary(diaryModifyData: DiaryModifyData) : BaseResponse<Nothing>
    suspend fun deleteDiary(diaryId : String) : BaseResponse<Nothing>
    suspend fun getDiaryItemUpdate() : Flow<DiaryItemUpdate>
    suspend fun getDiaryCount() : BaseResponse<Int>
}