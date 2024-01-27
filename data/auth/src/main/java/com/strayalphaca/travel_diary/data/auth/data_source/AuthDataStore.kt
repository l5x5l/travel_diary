package com.strayalphaca.travel_diary.data.auth.data_source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthDataStore @Inject constructor(
    private val dataStore : DataStore<Preferences>
) {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")

    val detectInvalidRefreshToken = MutableSharedFlow<Boolean>()

    suspend fun occurDetectInvalidRefreshToken() {
        detectInvalidRefreshToken.emit(true)
    }

    fun getAccessToken() : String? {
        return runBlocking(Dispatchers.IO) {
            dataStore.data.map { prefs -> prefs[ACCESS_TOKEN] }.first()
        }
    }

    fun getRefreshToken() : String? {
        return runBlocking(Dispatchers.IO) {
            dataStore.data.map { prefs -> prefs[REFRESH_TOKEN] }.first()
        }
    }

    suspend fun setRefreshToken(refreshToken : String) {
        dataStore.edit { it[REFRESH_TOKEN] = refreshToken }
    }

    suspend fun setAccessToken(accessToken : String) {
        dataStore.edit { it[ACCESS_TOKEN] = accessToken }
    }

    suspend fun clearTokens() {
        dataStore.edit { it.clear() }
    }
}