package com.strayalphaca.travel_diary.data.map.repository

import com.strayalphaca.travel_diary.core.data.utils.responseToBaseResponseWithMapping
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.data.map.api.MapApi
import com.strayalphaca.travel_diary.data.map.data_cache_store.MapDataCacheStore
import com.strayalphaca.travel_diary.map.model.Location
import com.strayalphaca.travel_diary.map.model.LocationDiary
import com.strayalphaca.travel_diary.map.model.LocationWithData
import com.strayalphaca.travel_diary.map.repository.MapRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteMapRepository @Inject constructor(
    retrofit : Retrofit,
    private val dataStore: MapDataCacheStore
) : MapRepository {
    private val mapRetrofit = retrofit.create(MapApi::class.java)

    override suspend fun getNationWideData(): BaseResponse<List<LocationDiary>> {
        val response = mapRetrofit.getAllLocationDiary()
        return responseToBaseResponseWithMapping(
            response = response,
            mappingFunction = { it.data.map { responseData -> responseData.toLocationDiary() }}
        ).also {
            if (it is BaseResponse.Success)
                dataStore.setMapData(LocationWithData(null, it.data))
        }
    }

    override suspend fun getProvinceData(provinceId: Int): BaseResponse<List<LocationDiary>> {
        val response = mapRetrofit.getProvinceDiary(provinceId)
        return responseToBaseResponseWithMapping(
            response = response,
            mappingFunction = { it.data.map { responseData -> responseData.toLocationDiary(provinceId) }}
        ).also {
            if (it is BaseResponse.Success)
                dataStore.setMapData(LocationWithData(Location.getInstanceByProvinceId(provinceId), it.data))
        }
    }

    override suspend fun refreshIfContainDiary(diaryId: String) {
        if (!dataStore.checkContainDiary(diaryId)) return
        val currentLocationId = dataStore.getCurrentProvinceId()
        if (currentLocationId == null) {
            getNationWideData()
        } else {
            getProvinceData(currentLocationId)
        }
    }

    override fun getLocationWithDataFlow(): Flow<LocationWithData> {
        return dataStore.getMapData()
    }

}