package com.strayalphaca.travel_diary.domain.auth.repository

import com.strayalphaca.domain.model.BaseResponse

interface AuthRepository {
    suspend fun reissueAccessToken() : BaseResponse<Nothing>
    fun getAccessToken() : String?
    fun getRefreshToken() : String?
    fun setAccessToken(accessToken : String)
    fun setRefreshToken(refreshToken : String)
    fun clearToken()
}