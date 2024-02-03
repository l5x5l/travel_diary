package com.strayalphaca.travel_diary.data.lock.data_source

import kotlinx.coroutines.flow.Flow

interface LockDataSource {
    suspend fun getPassword() : String?
    suspend fun setPassword(password : String?)
    suspend fun passwordFlow() : Flow<String?>
}