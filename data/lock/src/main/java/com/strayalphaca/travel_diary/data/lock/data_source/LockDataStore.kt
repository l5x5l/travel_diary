package com.strayalphaca.travel_diary.data.lock.data_source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LockDataStore @Inject constructor(
    private val dataStore : DataStore<Preferences>
) : LockDataSource {
    val SCREEN_PASSWORD = stringPreferencesKey("screen_password")

    override suspend fun getPassword(): String? {
        return dataStore.data.map { prefs -> prefs[SCREEN_PASSWORD] }.map { if (it == "" || it == "null") null else it }.first()
    }

    override suspend fun setPassword(password: String?) {
        dataStore.edit {
            it[SCREEN_PASSWORD] = password ?: ""
        }
    }

    override suspend fun passwordFlow(): Flow<String?> {
        return dataStore.data.map { prefs -> prefs[SCREEN_PASSWORD] }.map { if (it == "" || it == "null") null else it }
    }

}