package com.strayalphaca.travel_diary.data.map.repository

import com.strayalphaca.travel_diary.map.model.Location
import com.strayalphaca.travel_diary.map.model.LocationDiary
import com.strayalphaca.travel_diary.map.model.LocationId
import com.strayalphaca.travel_diary.map.repository.MapRepository
import javax.inject.Inject

// only for testing
class TestMapRepository @Inject constructor() : MapRepository {
    override suspend fun loadProvincePostList(provinceId: Int): List<LocationDiary> {
        return listOf(
            LocationDiary("", Location(LocationId(2), "부산", LocationId(2))),
            LocationDiary("", Location(LocationId(8), "세종", LocationId(8))),
            LocationDiary("", Location(LocationId(1), "서울", LocationId(1))),
            LocationDiary("", Location(LocationId(5), "대전", LocationId(5))),
            LocationDiary("", Location(LocationId(3), "울산", LocationId(3))),
        )
    }

    override suspend fun loadCityPostList(cityIdList: List<Int>): List<LocationDiary> {
        return listOf(
            LocationDiary(
                thumbnailUri = "", Location(LocationId(1), "서울", LocationId(1))
            )
        )
    }

    override suspend fun loadCityPostListByProvince(provinceId: Int): List<LocationDiary> {
        return listOf(
            LocationDiary(
                thumbnailUri = "", Location(LocationId(1), "서울", LocationId(1))
            )
        )
    }
}