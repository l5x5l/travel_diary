package com.strayalphaca.travel_diary.domain.login.use_case

import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.domain.login.repository.LoginRepository
import javax.inject.Inject

class UseCaseResetPassword @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(email : String) : BaseResponse<Nothing> {
        return loginRepository.issueRandomPasswordToEmail(email)
    }
}