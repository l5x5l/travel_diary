package com.strayalphaca.travel_diary.domain.login.use_case

import com.strayalphaca.travel_diary.domain.login.model.Tokens
import com.strayalphaca.travel_diary.domain.login.repository.LoginRepository
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalpaca.travel_diary.core.domain.model.ErrorCodeMapper
import com.strayalphaca.travel_diary.domain.login.di.LoginErrorCodeMapperProvide
import com.strayalphaca.travel_diary.domain.login.model.LoginErrorCodes
import com.strayalphaca.travel_diary.domain.login.utils.isEmailFormat
import com.strayalphaca.travel_diary.domain.login.utils.removeWhiteSpace
import javax.inject.Inject

class UseCaseLogin @Inject constructor(
    private val repository : LoginRepository,
    @LoginErrorCodeMapperProvide private val errorCodeMapper: ErrorCodeMapper
) {
    suspend operator fun invoke(email : String, password : String) : BaseResponse<Tokens> {
        val emailWithoutSpace = email.removeWhiteSpace()
        val passwordWithoutSpace = password.removeWhiteSpace()

        val clientCheckableErrorCode = getClientSideErrorCode(emailWithoutSpace, passwordWithoutSpace)
        clientCheckableErrorCode?.let {
            return BaseResponse.Failure(errorCode = it, errorMessage = errorCodeMapper.mapCodeToString(it))
        }

        val response = repository.emailLogin(emailWithoutSpace, passwordWithoutSpace)
        return response.mapErrorCodeToMessageIfFailure(errorCodeMapper::mapCodeToString)
    }

    private fun getClientSideErrorCode(email : String, password : String) : Int? {
        return if (email.isEmpty() || password.isEmpty()) {
            LoginErrorCodes.EMPTY_EMAIL_OR_PASSWORD
        } else if (!isEmailFormat(email)) {
            LoginErrorCodes.INVALID_EMAIL_FORMAT
        } else {
            null
        }
    }
}