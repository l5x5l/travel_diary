package com.strayalphaca.travel_diary.data.map.data_store

import com.strayalphaca.travel_diary.map.model.LocationDiary
import com.strayalphaca.travel_diary.map.model.LocationWithData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class MapDataStoreImpl @Inject constructor() : MapDataStore {
    private val mapWithData = MutableStateFlow(LocationWithData())

    override suspend fun setMapData(locationWithData: LocationWithData) {
        mapWithData.value = locationWithData
    }

    override fun getMapData(): Flow<LocationWithData> {
        return mapWithData
    }

    override suspend fun updateDiary(locationDiary: LocationDiary) {
        val diaryList = mapWithData.value.data
        val targetLocationDiary = diaryList.find{ it.id == locationDiary.id } ?: return
        val dataList = diaryList.map {
            if (it.id == targetLocationDiary.id)
                locationDiary
            else
                it
        }
        mapWithData.value = mapWithData.value.copy(data = dataList)
    }

    override suspend fun deleteDiary(id: String) {
        val dataList = mapWithData.value.data.filter { it.id != id }
        mapWithData.value = mapWithData.value.copy(data = dataList)
    }

    override suspend fun clear() {
        mapWithData.value = LocationWithData()
    }
}