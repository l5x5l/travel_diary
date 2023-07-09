package com.strayalphaca.data.app_info.data_source

interface AppInfoDataSource {
    suspend fun getIsFirstLogin() : Boolean
    suspend fun setIsFirstLogin(isFirstLogin : Boolean)
}