package com.strayalphaca.data.lock.data_source

interface LockDataSource {
    suspend fun getPassword() : String?
    suspend fun setPassword(password : String)
}