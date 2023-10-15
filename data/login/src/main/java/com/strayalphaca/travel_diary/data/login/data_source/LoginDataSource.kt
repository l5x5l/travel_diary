package com.strayalphaca.travel_diary.data.login.data_source

import com.strayalphaca.travel_diary.data.login.model.TokensDto
import com.strayalphaca.domain.model.BaseResponse

interface LoginDataSource {
    suspend fun postSignup() : BaseResponse<String>
    suspend fun postAuthCode(email : String) : BaseResponse<Nothing>
    suspend fun getAuthCode(email : String, authCode : String) : BaseResponse<Nothing>
    suspend fun postLogin(email : String, password : String) : BaseResponse<TokensDto>
    suspend fun postRefresh() : BaseResponse<TokensDto>
    suspend fun deleteUser() : BaseResponse<Nothing>
    suspend fun getCheckEmail(email : String) : BaseResponse<Nothing>
}