package com.strayalphaca.domain.login.use_case

import com.strayalphaca.domain.login.repository.LoginRepository
import com.strayalphaca.domain.model.BaseResponse
import javax.inject.Inject

class UseCaseIssueAuthCode @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(email : String) : BaseResponse<Nothing> {
        return repository.issueAuthCode(email)
    }

    suspend fun withEmailCheck(email : String) : BaseResponse<Nothing> {
        val checkEmailResponse = repository.checkEmailDuplication(email)

        if (checkEmailResponse is BaseResponse.Failure) return checkEmailResponse

        return repository.issueAuthCode(email)
    }
}