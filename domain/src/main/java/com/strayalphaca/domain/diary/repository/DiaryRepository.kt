package com.strayalphaca.domain.diary.repository

import com.strayalphaca.domain.diary.model.DiaryDetail
import com.strayalphaca.domain.diary.model.DiaryItem
import com.strayalphaca.domain.diary.model.DiaryModifyData
import com.strayalphaca.domain.diary.model.DiaryWriteData
import com.strayalphaca.domain.model.BaseResponse

interface DiaryRepository {
    suspend fun getDiaryDetail(id : String) : BaseResponse<DiaryDetail>
    suspend fun getDiaryList(cityId : Int, perPage : Int, offset : Int) : List<DiaryItem>
    suspend fun getDiaryListByCityGroup(cityGroupId : Int, perPage : Int, offset : Int) : List<DiaryItem>

    suspend fun uploadDiary(diaryWriteData: DiaryWriteData) : BaseResponse<String>
    suspend fun modifyDiary(diaryModifyData: DiaryModifyData) : BaseResponse<Nothing>
}