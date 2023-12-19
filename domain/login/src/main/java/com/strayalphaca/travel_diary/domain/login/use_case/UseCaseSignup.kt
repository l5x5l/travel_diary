package com.strayalphaca.travel_diary.domain.login.use_case

import com.strayalphaca.travel_diary.domain.login.repository.LoginRepository
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalpaca.travel_diary.core.domain.model.ErrorCodeMapper
import com.strayalphaca.travel_diary.domain.login.di.SignupErrorCodeMapperProvide
import com.strayalphaca.travel_diary.domain.login.utils.removeWhiteSpace
import javax.inject.Inject

class UseCaseSignup @Inject constructor(
    private val repository: LoginRepository,
    @SignupErrorCodeMapperProvide private val errorCodeMapper: ErrorCodeMapper
) {
    suspend operator fun invoke(email : String, password : String) : BaseResponse<String> {
        val emailWithoutSpace = email.removeWhiteSpace()
        val passwordWithoutSpace = password.removeWhiteSpace()

        val response = repository.emailSignup(emailWithoutSpace, passwordWithoutSpace)
        return response.mapErrorCodeToMessageIfFailure(errorCodeMapper::mapCodeToString)
    }
}