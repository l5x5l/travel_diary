package com.strayalphaca.travel_diary.diary.use_case

import com.strayalphaca.travel_diary.diary.model.DiaryDetail
import com.strayalphaca.travel_diary.diary.repository.DiaryRepository
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalpaca.travel_diary.core.domain.model.ErrorCodeMapper
import com.strayalphaca.travel_diary.diary.di.DiaryErrorCodeMapperProvide
import javax.inject.Inject

class UseCaseGetDiaryDetail @Inject constructor(
    private val repository: DiaryRepository,
    @DiaryErrorCodeMapperProvide private val errorCodeMapper : ErrorCodeMapper
) {
    suspend operator fun invoke(id : String) : BaseResponse<DiaryDetail> {
        val response = repository.getDiaryDetail(id)
        return response.mapErrorCodeToMessageIfFailure(errorCodeMapper::mapCodeToString)
    }
}