package com.strayalphaca.travel_diary.data.map.repository

import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.map.model.Location
import com.strayalphaca.travel_diary.map.model.LocationDiary
import com.strayalphaca.travel_diary.map.model.LocationId
import com.strayalphaca.travel_diary.map.model.LocationType
import com.strayalphaca.travel_diary.map.repository.MapRepository
import javax.inject.Inject

// only for testing
class TestMapRepository @Inject constructor() : MapRepository {
    override suspend fun getNationWideData(): BaseResponse<List<LocationDiary>> {
        val data = listOf(
            LocationDiary("", Location(LocationId(2), "부산", LocationId(2))),
            LocationDiary("", Location(LocationId(8), "세종", LocationId(8))),
            LocationDiary("", Location(LocationId(1), "서울", LocationId(1))),
            LocationDiary("", Location(LocationId(5), "대전", LocationId(5))),
            LocationDiary("", Location(LocationId(3), "울산", LocationId(3))),
        )
        return BaseResponse.Success(data)
    }

    override suspend fun getProvinceData(provinceId : Int): BaseResponse<List<LocationDiary>> {
        val data = when(provinceId) {
            1, 4, 9 -> { // 경기도
                listOf(
                    LocationDiary("", Location(LocationId(1), "서울", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(18), "김포", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(19), "양평", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(20), "안성", LocationId(provinceId), LocationType.CITY_GROUP)),
                )
            }
            10 -> { // 강원도
                listOf(
                    LocationDiary("", Location(LocationId(21), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(22), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(23), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(24), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(25), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(26), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(27), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                )
            }
            11 -> { // 충청북도
                listOf(
                    LocationDiary("", Location(LocationId(34), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(35), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(36), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(37), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(38), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                )
            }
            6, 8, 12 -> { // 충청남도
                listOf(
                    LocationDiary("", Location(LocationId(28), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(29), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(30), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(31), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(32), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(33), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                )
            }
            13 -> { // 전라북도
                listOf(
                    LocationDiary("", Location(LocationId(39), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(40), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(41), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(42), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                )
            }
            5, 14 -> { // 전라남도
                listOf(
                    LocationDiary("", Location(LocationId(43), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(44), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(45), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(46), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(47), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(48), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(49), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                )
            }
            3, 15 -> { // 경상북도
                listOf(
                    LocationDiary("", Location(LocationId(50), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(51), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(52), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(53), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(54), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(55), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(56), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(57), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                )
            }
            2, 7, 16 -> { // 경상남도
                listOf(
                    LocationDiary("", Location(LocationId(58), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(59), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(60), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(61), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                    LocationDiary("", Location(LocationId(62), "", LocationId(provinceId), LocationType.CITY_GROUP)),
                )
            }
            17 -> { // 제주도
                listOf()
            }
            else -> {
                listOf(
                    LocationDiary("", Location(LocationId(2), "", LocationId(provinceId))),
                    LocationDiary("", Location(LocationId(8), "", LocationId(provinceId))),
                    LocationDiary("", Location(LocationId(1), "", LocationId(provinceId))),
                    LocationDiary("", Location(LocationId(5), "", LocationId(provinceId))),
                    LocationDiary("", Location(LocationId(3), "", LocationId(provinceId))),
                )
            }
        }
        return BaseResponse.Success(data)
    }

}