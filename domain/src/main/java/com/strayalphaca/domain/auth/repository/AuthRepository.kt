package com.strayalphaca.domain.auth.repository

import com.strayalphaca.domain.model.BaseResponse

interface AuthRepository {
    suspend fun reissueAccessToken() : BaseResponse<Nothing>
    fun getAccessToken() : String?
    fun getRefreshToken() : String?
}