package com.strayalphaca.domain.lock.repository

import com.strayalphaca.domain.model.BaseResponse

interface LockRepository {
    suspend fun checkPassword(password : String) : BaseResponse<Nothing>
    suspend fun setPassword(password : String) : BaseResponse<Nothing>
    suspend fun checkUsingPassword() : Boolean
}