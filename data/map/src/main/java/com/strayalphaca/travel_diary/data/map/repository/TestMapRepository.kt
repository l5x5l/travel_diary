package com.strayalphaca.travel_diary.data.map.repository

import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.core.data.demo_data_source.DemoDataSource
import com.strayalphaca.travel_diary.data.map.data_cache_store.MapDataCacheStore
import com.strayalphaca.travel_diary.map.model.City
import com.strayalphaca.travel_diary.map.model.Location
import com.strayalphaca.travel_diary.map.model.LocationDiary
import com.strayalphaca.travel_diary.map.model.LocationId
import com.strayalphaca.travel_diary.map.model.LocationType
import com.strayalphaca.travel_diary.map.model.LocationWithData
import com.strayalphaca.travel_diary.map.repository.MapRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestMapRepository @Inject constructor(
    private val mapDataCacheStore: MapDataCacheStore,
    private val demoDataSource: DemoDataSource
) : MapRepository {
    override suspend fun getNationWideData(): BaseResponse<List<LocationDiary>> {
        val data = demoDataSource.getTitleDiaryListNationWide().map { diaryItemDto ->
            LocationDiary(
                thumbnailUri = diaryItemDto.image?.uploadedLink,
                location = Location.getInstanceByProvinceId(diaryItemDto.provinceId),
                id = diaryItemDto.id
            )
        }
        mapDataCacheStore.setMapData(LocationWithData(null, data = data))
        return BaseResponse.Success(data)
    }

    override suspend fun getProvinceData(provinceId : Int): BaseResponse<List<LocationDiary>> {
        val data = demoDataSource.getTitleDiaryListByProvinceId(provinceId).map { diaryItemDto ->
            val city = City.findCity(diaryItemDto.city.id)
            LocationDiary(
                thumbnailUri = diaryItemDto.image?.uploadedLink,
                location = Location(
                    id = LocationId(city.group),
                    name = city.name,
                    provinceId = LocationId(provinceId),
                    type = LocationType.CITY_GROUP
                ),
                id = diaryItemDto.id
            )
        }
        mapDataCacheStore.setMapData(LocationWithData(Location.getInstanceByProvinceId(provinceId), data))
        return BaseResponse.Success(data)
    }

    override suspend fun refreshIfContainDiary(diaryId: String) {
        if (!mapDataCacheStore.checkContainDiary(diaryId)) return
        val currentLocationId = mapDataCacheStore.getCurrentProvinceId()
        if (currentLocationId == null) {
            getNationWideData()
        } else {
            getProvinceData(currentLocationId)
        }
    }

    override fun getLocationWithDataFlow(): Flow<LocationWithData> {
        return mapDataCacheStore.getMapData()
    }

}