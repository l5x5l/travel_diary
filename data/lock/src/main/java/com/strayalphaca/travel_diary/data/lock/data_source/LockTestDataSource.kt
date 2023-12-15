package com.strayalphaca.travel_diary.data.lock.data_source

import kotlinx.coroutines.delay
import javax.inject.Inject

class LockTestDataSource @Inject constructor() : LockDataSource {
    override suspend fun getPassword(): String? {
        delay(1000L)
        return "1111"
    }

    override suspend fun setPassword(password: String) {
        delay(1000L)
        return
    }
}