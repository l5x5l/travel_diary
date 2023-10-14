package com.strayalphaca.travel_diary.domain.login.use_case

import com.strayalphaca.travel_diary.domain.login.repository.LoginRepository
import com.strayalphaca.domain.model.BaseResponse
import javax.inject.Inject

class UseCaseCheckAuthCode @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(email : String, authCode : String): BaseResponse<Nothing> {
        return repository.checkAuthCode(email, authCode)
    }
}