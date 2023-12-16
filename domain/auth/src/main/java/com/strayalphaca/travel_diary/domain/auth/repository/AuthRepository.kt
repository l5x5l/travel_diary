package com.strayalphaca.travel_diary.domain.auth.repository

import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun reissueAccessToken() : BaseResponse<Nothing>
    fun getAccessToken() : String?
    fun getRefreshToken() : String?
    suspend fun setAccessToken(accessToken : String)
    suspend fun setRefreshToken(refreshToken : String)
    suspend fun clearToken()
    fun invalidRefreshToken() : Flow<Boolean>
}