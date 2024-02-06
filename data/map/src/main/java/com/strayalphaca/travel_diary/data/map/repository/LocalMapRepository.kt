package com.strayalphaca.travel_diary.data.map.repository

import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.data.map.data_cache_store.MapDataCacheStore
import com.strayalphaca.travel_diary.data.map.data_source.MapDataSource
import com.strayalphaca.travel_diary.map.model.Location
import com.strayalphaca.travel_diary.map.model.LocationDiary
import com.strayalphaca.travel_diary.map.model.LocationWithData
import com.strayalphaca.travel_diary.map.repository.MapRepository
import kotlinx.coroutines.flow.Flow

class LocalMapRepository(
    private val mapDataSource: MapDataSource,
    private val dataCacheStore: MapDataCacheStore
) : MapRepository {
    override suspend fun getNationWideData(): BaseResponse<List<LocationDiary>> {
        return mapDataSource.getDiaryListInNationWide().also {
            if (it is BaseResponse.Success){
                dataCacheStore.setMapData(LocationWithData(null, it.data))
            }
        }
    }

    override suspend fun getProvinceData(provinceId: Int): BaseResponse<List<LocationDiary>> {
        return mapDataSource.getDiaryListInProvince(provinceId).also {
            if (it is BaseResponse.Success) {
                dataCacheStore.setMapData(LocationWithData(Location.getInstanceByProvinceId(provinceId), it.data))
            }
        }
    }

    override suspend fun refreshIfContainDiary(diaryId: String) {
        if (!dataCacheStore.checkContainDiary(diaryId)) return
        val currentLocationId = dataCacheStore.getCurrentProvinceId()
        if (currentLocationId == null) {
            getNationWideData()
        } else {
            getProvinceData(currentLocationId)
        }
    }

    override fun getLocationWithDataFlow(): Flow<LocationWithData> {
        return dataCacheStore.getMapData()
    }

}