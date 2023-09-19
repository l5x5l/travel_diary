package com.strayalphaca.travel_diary.data.alarm.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.strayalphaca.travel_diary.domain.alarm.model.AlarmInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AlarmDataStore @Inject constructor(
    private val dataStore : DataStore<Preferences>
) {
    val USE_ALARM = booleanPreferencesKey("use_alarm")
    val ALARM_HOUR = intPreferencesKey("alarm_hour")
    val ALARM_MINUTE = intPreferencesKey("alarm_minute")
    val ALARM_ROUTE = stringPreferencesKey("alarm_route")

    fun getUseAlarm() : Flow<Boolean> {
        return dataStore.data.map{prefs -> prefs[USE_ALARM] ?: false}
    }

    fun getAlarmHour() : Flow<Int> {
        return dataStore.data.map { prefs -> prefs[ALARM_HOUR] ?: 20}
    }

    fun getAlarmMinute() : Flow<Int> {
        return dataStore.data.map { prefs -> prefs[ALARM_MINUTE] ?: 0}
    }

    fun getAlarmRoute() : Flow<String?> {
        return dataStore.data.map { prefs -> prefs[ALARM_ROUTE] }.map { if (it == "") null else it }
    }

    fun setAlarmInfo(alarmInfo: AlarmInfo) {
        runBlocking(Dispatchers.IO) {
            dataStore.edit {
                it[USE_ALARM] = alarmInfo.alarmOn
                it[ALARM_HOUR] = alarmInfo.hour
                it[ALARM_MINUTE] = alarmInfo.minute
                it[ALARM_ROUTE] = alarmInfo.routeLink ?: ""
            }
        }
    }
}