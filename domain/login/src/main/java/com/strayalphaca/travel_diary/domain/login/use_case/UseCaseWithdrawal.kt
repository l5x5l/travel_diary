package com.strayalphaca.travel_diary.domain.login.use_case

import com.strayalphaca.travel_diary.domain.login.repository.LoginRepository
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import javax.inject.Inject

class UseCaseWithdrawal @Inject constructor(
    private val repository : LoginRepository
) {
    suspend operator fun invoke() : BaseResponse<Nothing> {
        return repository.withdrawal()
    }
}