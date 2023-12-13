package com.strayalphaca.travel_diary.domain.login.use_case

import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.domain.login.repository.LoginRepository
import javax.inject.Inject

class UseCaseChangePassword @Inject constructor(
    private val repository : LoginRepository
) {
    suspend operator fun invoke(prevPassword : String, newPassword : String) : BaseResponse<Nothing> {
        if (prevPassword.isEmpty() || newPassword.isEmpty()) return BaseResponse.Failure(errorCode = -1, errorMessage = "비밀번호를 입력해 주세요.")

        return repository.changePassword(prevPassword, newPassword)
    }
}