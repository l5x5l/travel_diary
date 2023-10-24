package com.strayalphaca.travel_diary.domain.login.use_case

import com.strayalphaca.travel_diary.domain.login.model.Tokens
import com.strayalphaca.travel_diary.domain.login.repository.LoginRepository
import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.domain.login.model.LoginErrorCodes
import com.strayalphaca.travel_diary.domain.login.utils.isEmailFormat
import javax.inject.Inject

class UseCaseLogin @Inject constructor(
    private val repository : LoginRepository
) {
    suspend operator fun invoke(email : String, password : String) : BaseResponse<Tokens> {
        if (email.isEmpty() || password.isEmpty()) {
            return BaseResponse.Failure(errorCode = LoginErrorCodes.INPUT_EMAIL_AND_PASSWORD, errorMessage = "이메일/비밀번호를 입력해 주세요.")
        }
        if (!isEmailFormat(email)) {
            return BaseResponse.Failure(errorCode = LoginErrorCodes.INVALID_EMAIL_FORMAT, errorMessage = "이메일 형식으로 입력해 주세요.")
        }

        return repository.emailLogin(email, password)
    }
}