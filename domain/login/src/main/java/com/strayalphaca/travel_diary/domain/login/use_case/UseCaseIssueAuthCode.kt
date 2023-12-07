package com.strayalphaca.travel_diary.domain.login.use_case

import com.strayalphaca.travel_diary.domain.login.repository.LoginRepository
import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.domain.login.model.LoginErrorCodes
import com.strayalphaca.travel_diary.domain.login.utils.isEmailFormat
import com.strayalphaca.travel_diary.domain.login.utils.removeWhiteSpace
import javax.inject.Inject

class UseCaseIssueAuthCode @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(email : String) : BaseResponse<Nothing> {
        val emailWithoutSpace = email.removeWhiteSpace()
        if (!isEmailFormat(emailWithoutSpace)) {
            return BaseResponse.Failure(errorCode = LoginErrorCodes.INVALID_EMAIL_FORMAT, errorMessage = "이메일 형식으로 입력해 주세요.")
        }

        return repository.issueAuthCode(emailWithoutSpace)
    }
}