package com.strayalphaca.domain.diary.repository

import com.strayalphaca.domain.diary.model.DiaryDetail
import com.strayalphaca.domain.model.BaseResponse

interface DiaryRepository {
    suspend fun getDiaryDetail(id : String) : BaseResponse<DiaryDetail>
}