package com.strayalphaca.data.auth.repository

import com.strayalphaca.data.all.utils.voidResponseToBaseResponse
import com.strayalphaca.data.auth.api.AuthApi
import com.strayalphaca.domain.auth.repository.AuthRepository
import com.strayalphaca.domain.model.BaseResponse
import retrofit2.Retrofit
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    retrofit: Retrofit
) : AuthRepository {
    private val authRetrofit = retrofit.create(AuthApi::class.java)

    override suspend fun reissueAccessToken(): BaseResponse<Nothing> {
        val response = authRetrofit.reissueToken(
            mapOf("Authorization" to "Bearer ${getAccessToken()}", "RefreshToken" to "${getRefreshToken()}")
        )
        return voidResponseToBaseResponse(response)
    }

    override fun getAccessToken(): String? {
        TODO("Not yet implemented")
    }

    override fun getRefreshToken(): String? {
        TODO("Not yet implemented")
    }

}