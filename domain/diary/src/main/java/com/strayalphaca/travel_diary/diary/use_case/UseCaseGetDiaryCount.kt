package com.strayalphaca.travel_diary.diary.use_case

import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalpaca.travel_diary.core.domain.model.ErrorCodeMapper
import com.strayalphaca.travel_diary.diary.di.DiaryErrorCodeMapperProvide
import com.strayalphaca.travel_diary.diary.repository.DiaryRepository
import javax.inject.Inject

class UseCaseGetDiaryCount @Inject constructor(
    private val repository : DiaryRepository,
    @DiaryErrorCodeMapperProvide private val errorCodeMapper : ErrorCodeMapper
) {
    suspend operator fun invoke() : BaseResponse<Int> {
        val response = repository.getDiaryCount()
        return response.mapErrorCodeToMessageIfFailure(errorCodeMapper::mapCodeToString)
    }
}