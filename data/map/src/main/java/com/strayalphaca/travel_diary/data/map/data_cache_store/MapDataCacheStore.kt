package com.strayalphaca.travel_diary.data.map.data_cache_store

import com.strayalphaca.travel_diary.map.model.LocationWithData
import kotlinx.coroutines.flow.Flow

interface MapDataCacheStore {
    suspend fun setMapData(locationWithData: LocationWithData)
    fun getMapData() : Flow<LocationWithData>
    suspend fun getCurrentProvinceId() : Int?
    suspend fun checkContainDiary(id : String) : Boolean
}