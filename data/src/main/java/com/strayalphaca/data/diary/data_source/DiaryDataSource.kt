package com.strayalphaca.data.diary.data_source

import com.strayalphaca.data.all.model.DiaryDto
import com.strayalphaca.domain.model.BaseResponse

interface DiaryDataSource {
    suspend fun getDiaryData(id : String) : BaseResponse<DiaryDto>
}