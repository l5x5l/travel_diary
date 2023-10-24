package com.strayalphaca.travel_diary.data.auth.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AuthDataStore @Inject constructor(
    private val dataStore : DataStore<Preferences>
) {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")

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

    fun setRefreshToken(refreshToken : String) {
        runBlocking(Dispatchers.IO) {
            dataStore.edit { it[REFRESH_TOKEN] = refreshToken }
        }
    }

    fun setAccessToken(accessToken : String) {
        runBlocking(Dispatchers.IO) {
            dataStore.edit { it[ACCESS_TOKEN] = accessToken }
        }
    }

    fun clearTokens() {
        runBlocking(Dispatchers.IO) {
            dataStore.edit { it.clear() }
        }
    }
}