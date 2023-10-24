package com.strayalphaca.travel_diary.data.auth.repository

import com.strayalphaca.data.all.utils.voidResponseToBaseResponse
import com.strayalphaca.travel_diary.data.auth.api.AuthApi
import com.strayalphaca.travel_diary.domain.auth.repository.AuthRepository
import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.data.auth.datastore.AuthDataStore
import retrofit2.Retrofit
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    retrofit: Retrofit,
    private val authDataStore: AuthDataStore
) : AuthRepository {
    private val authRetrofit = retrofit.create(AuthApi::class.java)

    override suspend fun reissueAccessToken(): BaseResponse<Nothing> {
        val response = authRetrofit.reissueToken(
            mapOf("Authorization" to "Bearer ${getAccessToken()}", "RefreshToken" to "${getRefreshToken()}")
        )
        val body = response.body()
        return if (response.isSuccessful && body != null) {
            authDataStore.setAccessToken(body.accessToken)
            authDataStore.setRefreshToken(body.refreshToken)
            voidResponseToBaseResponse(response)
        } else {
            BaseResponse.Failure(
                errorMessage = response.message(),
                errorCode = response.code()
            )
        }

    }

    override fun getAccessToken(): String? {
        return authDataStore.getAccessToken()
    }

    override fun getRefreshToken(): String? {
        return authDataStore.getRefreshToken()
    }

    override fun setAccessToken(accessToken: String) {
        authDataStore.setAccessToken(accessToken)
    }

    override fun setRefreshToken(refreshToken: String) {
        authDataStore.setRefreshToken(refreshToken)
    }

    override fun clearToken() {
        authDataStore.clearTokens()
    }

}