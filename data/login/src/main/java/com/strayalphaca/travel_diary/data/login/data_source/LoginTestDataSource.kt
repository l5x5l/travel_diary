package com.strayalphaca.travel_diary.data.login.data_source

import android.util.Log
import com.strayalphaca.travel_diary.data.login.model.TokensDto
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import kotlinx.coroutines.delay
import javax.inject.Inject

// 서버 연결 전 테스트용 클래스
class LoginTestDataSource @Inject constructor() : LoginDataSource {
    override suspend fun postSignup(): BaseResponse<String> {
        delay(1500L)
        return BaseResponse.Success("nice!")
    }

    override suspend fun postAuthCode(email: String): BaseResponse<Nothing> {
        delay(1500L)
        return BaseResponse.EmptySuccess
    }

    override suspend fun getAuthCode(email: String, authCode: String): BaseResponse<Nothing> {
        delay(1500L)
        return BaseResponse.EmptySuccess
    }

    override suspend fun postLogin(email: String, password: String): BaseResponse<TokensDto> {
        delay(1500L)
        Log.d("postLogin", "call!")
        return BaseResponse.Success(TokensDto("accessToken", "refreshToken"))
    }

    override suspend fun postRefresh(): BaseResponse<TokensDto> {
        delay(500L)
        return BaseResponse.Success(TokensDto("accessToken", "refreshToken"))
    }

    override suspend fun deleteUser(): BaseResponse<Nothing> {
        return BaseResponse.EmptySuccess
    }

    override suspend fun getCheckEmail(email: String): BaseResponse<Nothing> {
        delay(1500L)
        return BaseResponse.EmptySuccess
    }
}