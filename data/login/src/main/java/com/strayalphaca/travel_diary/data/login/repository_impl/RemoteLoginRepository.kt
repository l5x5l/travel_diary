package com.strayalphaca.travel_diary.data.login.repository_impl

import com.strayalphaca.travel_diary.core.data.utils.responseToBaseResponseWithMapping
import com.strayalphaca.travel_diary.core.data.utils.voidResponseToBaseResponse
import com.strayalphaca.travel_diary.data.login.api.LoginApi
import com.strayalphaca.travel_diary.data.login.model.IssueAuthCodeBody
import com.strayalphaca.travel_diary.data.login.model.LoginRequestBody
import com.strayalphaca.travel_diary.data.login.model.SignUpRequestBody
import com.strayalphaca.travel_diary.data.login.utils.tokenDtoToToken
import com.strayalphaca.travel_diary.domain.login.model.Tokens
import com.strayalphaca.travel_diary.domain.login.repository.LoginRepository
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.data.login.model.ChangePasswordRequestBody
import com.strayalphaca.travel_diary.data.login.utils.extractIdFromSignupResponse
import com.strayalphaca.travel_diary.domain.login.model.AuthCodeCaseType
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteLoginRepository @Inject constructor(
    retrofit: Retrofit
) : LoginRepository {
    private val loginRetrofit = retrofit.create(LoginApi::class.java)

    override suspend fun emailLogin(email: String, password: String): BaseResponse<Tokens> {
        val response = loginRetrofit.login(LoginRequestBody(email = email, password = password))
        return responseToBaseResponseWithMapping(
            response = response,
            mappingFunction = ::tokenDtoToToken
        )
    }

    override suspend fun emailSignup(email: String, password: String): BaseResponse<String> {
        val response = loginRetrofit.signUpByEmail(SignUpRequestBody(email = email, password = password))
        return responseToBaseResponseWithMapping(
            response = response,
            mappingFunction = ::extractIdFromSignupResponse
        )
    }

    override suspend fun checkAuthCode(email: String, authCode: String, type : AuthCodeCaseType): BaseResponse<Nothing> {
        val response = loginRetrofit.checkAuthCode(email, authCode, type.name)
        return voidResponseToBaseResponse(response)
    }

    override suspend fun issueAuthCode(email: String, type : AuthCodeCaseType): BaseResponse<Nothing> {
        val response = loginRetrofit.issueAuthCode(IssueAuthCodeBody(email, type.name))
        return voidResponseToBaseResponse(response)
    }

    override suspend fun withdrawal(): BaseResponse<Nothing> {
        val response = loginRetrofit.withDraw()
        return voidResponseToBaseResponse(response)
    }

    // todo - 비밀번호 초기화 api 구현시 연동
    override suspend fun issueRandomPasswordToEmail(email: String): BaseResponse<Nothing> {
        return BaseResponse.EmptySuccess
    }

    override suspend fun changePassword(
        prevPassword: String,
        newPassword: String
    ): BaseResponse<Nothing> {
        val requestBody = ChangePasswordRequestBody(prevPassword = prevPassword, newPassword = newPassword)
        val response = loginRetrofit.changePassword(requestBody)
        return voidResponseToBaseResponse(response)
    }
}