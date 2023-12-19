package com.strayalphaca.travel_diary.domain.login.use_case

import com.strayalphaca.travel_diary.domain.login.repository.LoginRepository
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalpaca.travel_diary.core.domain.model.ErrorCodeMapper
import com.strayalphaca.travel_diary.domain.login.di.WithdrawalErrorCodeMapperProvide
import javax.inject.Inject

class UseCaseWithdrawal @Inject constructor(
    private val repository : LoginRepository,
    @WithdrawalErrorCodeMapperProvide private val errorCodeMapper : ErrorCodeMapper
) {
    suspend operator fun invoke() : BaseResponse<Nothing> {
        val response = repository.withdrawal()
        return response.mapErrorCodeToMessageIfFailure(errorCodeMapper::mapCodeToString)
    }
}