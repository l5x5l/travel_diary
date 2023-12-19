package com.strayalphaca.travel_diary.domain.login.use_case

import com.strayalphaca.travel_diary.domain.login.repository.LoginRepository
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalpaca.travel_diary.core.domain.model.ErrorCodeMapper
import com.strayalphaca.travel_diary.domain.login.di.AuthCodeErrorCodeMapperProvide
import com.strayalphaca.travel_diary.domain.login.model.LoginErrorCodes
import com.strayalphaca.travel_diary.domain.login.utils.isEmailFormat
import com.strayalphaca.travel_diary.domain.login.utils.removeWhiteSpace
import javax.inject.Inject

class UseCaseIssueAuthCode @Inject constructor(
    private val repository: LoginRepository,
    @AuthCodeErrorCodeMapperProvide private val errorCodeMapper: ErrorCodeMapper
) {
    suspend operator fun invoke(email : String) : BaseResponse<Nothing> {
        val emailWithoutSpace = email.removeWhiteSpace()
        if (!isEmailFormat(emailWithoutSpace)) {
            val errorCode = LoginErrorCodes.INVALID_EMAIL_FORMAT
            return BaseResponse.Failure(errorCode = errorCode, errorMessage = errorCodeMapper.mapCodeToString(errorCode))
        }

        val response = repository.issueAuthCode(emailWithoutSpace)
        return response.mapErrorCodeToMessageIfFailure(errorCodeMapper::mapCodeToString)
    }
}