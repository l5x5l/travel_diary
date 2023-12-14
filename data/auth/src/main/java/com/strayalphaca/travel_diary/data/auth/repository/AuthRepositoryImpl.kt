package com.strayalphaca.travel_diary.data.auth.repository

import com.strayalphaca.travel_diary.core.data.utils.voidResponseToBaseResponse
import com.strayalphaca.travel_diary.data.auth.api.AuthApi
import com.strayalphaca.travel_diary.domain.auth.repository.AuthRepository
import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.data.auth.datastore.AuthDataStore
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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
            authDataStore.occurDetectInvalidRefreshToken()
            authDataStore.clearTokens()
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

    override suspend fun setAccessToken(accessToken: String) {
        authDataStore.setAccessToken(accessToken)
    }

    override suspend fun setRefreshToken(refreshToken: String) {
        authDataStore.setRefreshToken(refreshToken)
    }

    override suspend  fun clearToken() {
        authDataStore.clearTokens()
    }

    override fun invalidRefreshToken(): Flow<Boolean> {
        return authDataStore.detectInvalidRefreshToken
    }

}