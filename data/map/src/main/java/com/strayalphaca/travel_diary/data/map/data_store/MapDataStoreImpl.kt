package com.strayalphaca.travel_diary.data.map.data_store

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

    override suspend fun getCurrentProvinceId(): Int? {
        return mapWithData.value.location?.id?.id
    }

    override suspend fun checkContainDiary(id: String): Boolean {
        return mapWithData.value.data.find { it.id == id } != null
    }

}