package com.strayalphaca.travel_diary.data.login.repository_impl

import com.strayalphaca.data.all.utils.responseToBaseResponseWithMapping
import com.strayalphaca.data.all.utils.voidResponseToBaseResponse
import com.strayalphaca.travel_diary.data.login.api.LoginApi
import com.strayalphaca.travel_diary.data.login.model.IssueAuthCodeBody
import com.strayalphaca.travel_diary.data.login.model.LoginRequestBody
import com.strayalphaca.travel_diary.data.login.model.SignUpRequestBody
import com.strayalphaca.travel_diary.data.login.utils.tokenDtoToToken
import com.strayalphaca.travel_diary.domain.login.model.Tokens
import com.strayalphaca.travel_diary.domain.login.repository.LoginRepository
import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.data.login.utils.extractIdFromSignupResponse
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

    override suspend fun checkAuthCode(email: String, authCode: String): BaseResponse<Nothing> {
        val response = loginRetrofit.checkAuthCode(email, authCode)
        return voidResponseToBaseResponse(response)
    }

    override suspend fun issueAuthCode(email: String): BaseResponse<Nothing> {
        val response = loginRetrofit.issueAuthCode(IssueAuthCodeBody(email))
        return voidResponseToBaseResponse(response)
    }

    override suspend fun withdrawal(): BaseResponse<Nothing> {
        val response = loginRetrofit.withDraw()
        return voidResponseToBaseResponse(response)
    }
}