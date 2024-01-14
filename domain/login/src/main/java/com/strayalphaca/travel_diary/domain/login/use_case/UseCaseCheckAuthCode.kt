package com.strayalphaca.travel_diary.domain.login.use_case

import com.strayalphaca.travel_diary.domain.login.repository.LoginRepository
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalpaca.travel_diary.core.domain.model.ErrorCodeMapper
import com.strayalphaca.travel_diary.domain.login.di.AuthCodeErrorCodeMapperProvide
import com.strayalphaca.travel_diary.domain.login.model.AuthCodeCaseType
import com.strayalphaca.travel_diary.domain.login.model.LoginErrorCodes.INVALID_AUTH_CODE_FORMAT
import com.strayalphaca.travel_diary.domain.login.model.LoginErrorCodes.INVALID_EMAIL_FORMAT
import com.strayalphaca.travel_diary.domain.login.utils.isEmailFormat
import com.strayalphaca.travel_diary.domain.login.utils.removeWhiteSpace
import javax.inject.Inject

class UseCaseCheckAuthCode @Inject constructor(
    private val repository: LoginRepository,
    @AuthCodeErrorCodeMapperProvide private val errorCodeMapper: ErrorCodeMapper
) {
    suspend operator fun invoke(email : String, authCode : String, type : AuthCodeCaseType = AuthCodeCaseType.REGISTER): BaseResponse<Nothing> {
        val emailWithoutSpace = email.removeWhiteSpace()
        val authCodeWithoutSpace = authCode.removeWhiteSpace()

        val clientSideErrorCode = getClientSideErrorCode(emailWithoutSpace, authCodeWithoutSpace)
        clientSideErrorCode?.let {
            return BaseResponse.Failure(errorCode = it, errorMessage = errorCodeMapper.mapCodeToString(it))
        }

        val response = repository.checkAuthCode(emailWithoutSpace, authCode, type)
        return response.mapErrorCodeToMessageIfFailure(errorCodeMapper::mapCodeToString)
    }

    private fun getClientSideErrorCode(email : String, authCode : String) : Int? {
        return if (!isEmailFormat(email)) {
            INVALID_EMAIL_FORMAT
        } else if (authCode.length != 6) {
            INVALID_AUTH_CODE_FORMAT
        } else {
            null
        }
    }
}