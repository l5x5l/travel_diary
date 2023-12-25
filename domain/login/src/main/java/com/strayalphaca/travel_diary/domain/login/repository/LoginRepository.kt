package com.strayalphaca.travel_diary.domain.login.repository

import com.strayalphaca.travel_diary.domain.login.model.Tokens
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.domain.login.model.AuthCodeCaseType

interface LoginRepository {
    suspend fun emailLogin(email : String, password : String) : BaseResponse<Tokens>
    suspend fun emailSignup(email : String, password : String) : BaseResponse<String>
    suspend fun checkAuthCode(email : String, authCode : String, type : AuthCodeCaseType) : BaseResponse<Nothing>
    suspend fun issueAuthCode(email : String, type : AuthCodeCaseType) : BaseResponse<Nothing>
    suspend fun withdrawal() : BaseResponse<Nothing>
    suspend fun issueRandomPasswordToEmail(email : String) : BaseResponse<Nothing>
    suspend fun changePassword(prevPassword : String, newPassword : String) : BaseResponse<Nothing>
}