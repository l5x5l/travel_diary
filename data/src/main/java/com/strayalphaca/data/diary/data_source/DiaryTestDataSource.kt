package com.strayalphaca.data.diary.data_source

import com.strayalphaca.data.all.model.DiaryDto
import com.strayalphaca.data.all.model.FileDto
import com.strayalphaca.domain.model.BaseResponse
import kotlinx.coroutines.delay
import javax.inject.Inject

class DiaryTestDataSource @Inject constructor() : DiaryDataSource {
    override suspend fun getDiaryData(id: String): BaseResponse<DiaryDto> {
        delay(1000L)

        val diary = DiaryDto(
            id = "1", date = "2023/03.01",
            files = listOf(FileDto(id = "", shortLink = "")))

        return BaseResponse.Success(diary)
    }
}