package com.strayalphaca.travel_diary.domain.login.use_case

import com.strayalphaca.travel_diary.domain.login.repository.LoginRepository
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.domain.login.utils.removeWhiteSpace
import javax.inject.Inject

class UseCaseCheckAuthCode @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(email : String, authCode : String): BaseResponse<Nothing> {
        val emailWithoutSpace = email.removeWhiteSpace()
        return repository.checkAuthCode(emailWithoutSpace, authCode)
    }
}