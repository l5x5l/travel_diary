package com.strayalphaca.travel_diary.domain.login.repository

import com.strayalphaca.travel_diary.domain.login.model.Tokens
import com.strayalphaca.domain.model.BaseResponse

interface LoginRepository {
    suspend fun emailLogin(email : String, password : String) : BaseResponse<Tokens>
    suspend fun emailSignup(email : String, password : String) : BaseResponse<String>
    suspend fun checkAuthCode(email : String, authCode : String) : BaseResponse<Nothing>
    suspend fun issueAuthCode(email : String) : BaseResponse<Nothing>
    suspend fun withdrawal() : BaseResponse<Nothing>
    suspend fun issueRandomPasswordToEmail(email : String) : BaseResponse<Nothing>
    suspend fun changePassword(prevPassword : String, newPassword : String) : BaseResponse<Nothing>
}