package com.strayalphaca.travel_diary.data.lock.data_source

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LockTestDataSource @Inject constructor() : LockDataSource {
    private val password = MutableStateFlow<String?>("1111")

    override suspend fun getPassword(): String? {
        return password.value
    }

    override suspend fun setPassword(password: String?) {
        this.password.update { password }
        return
    }

    override suspend fun passwordFlow(): Flow<String?> = password
}