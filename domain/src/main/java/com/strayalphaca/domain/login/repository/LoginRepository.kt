package com.strayalphaca.domain.login.repository

import com.strayalphaca.domain.login.model.Tokens
import com.strayalphaca.domain.model.BaseResponse

interface LoginRepository {
    suspend fun emailLogin(email : String, password : String) : BaseResponse<Tokens>
    suspend fun emailSignup(email : String, password : String) : BaseResponse<String>
    suspend fun checkEmailDuplication(email : String) : BaseResponse<Nothing>
    suspend fun checkAuthCode(email : String, authCode : String) : BaseResponse<Nothing>
    suspend fun issueAuthCode(email : String) : BaseResponse<Nothing>
    suspend fun refreshToken() : BaseResponse<Tokens>
    suspend fun withdrawal() : BaseResponse<Nothing>
}