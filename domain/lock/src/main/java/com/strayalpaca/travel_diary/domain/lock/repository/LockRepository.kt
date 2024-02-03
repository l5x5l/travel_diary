package com.strayalpaca.travel_diary.domain.lock.repository

import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import kotlinx.coroutines.flow.Flow

interface LockRepository {
    suspend fun checkPassword(password : String) : BaseResponse<Nothing>
    suspend fun setPassword(password : String) : BaseResponse<Nothing>
    suspend fun checkUsingPassword() : Boolean
    suspend fun checkUsingPasswordFlow() : Flow<Boolean>
    suspend fun clearPassword() : BaseResponse<Nothing>
}