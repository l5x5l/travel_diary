package com.strayalphaca.travel_diary.data.map.data_source

import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.core.data.room.dao.RecordDao
import com.strayalphaca.travel_diary.core.data.room.model.getSingleItemPerId
import com.strayalphaca.travel_diary.map.model.City
import com.strayalphaca.travel_diary.map.model.Location
import com.strayalphaca.travel_diary.map.model.LocationDiary
import com.strayalphaca.travel_diary.map.model.LocationId
import com.strayalphaca.travel_diary.map.model.LocationType
import com.strayalphaca.travel_diary.map.model.Province
import javax.inject.Inject

class MapLocalDataSource @Inject constructor(
    private val recordDao: RecordDao
) : MapDataSource {
    override suspend fun getDiaryListInNationWide(): BaseResponse<List<LocationDiary>> {
        val recordItemList = recordDao.getRecordInMapNationWide()
            .getSingleItemPerId()
            .groupBy {
                it.provinceId?.let { provinceId ->
                    Province.findProvince(provinceId).group
                }
            }.values
            .map { recordItemList -> recordItemList.maxBy { it.date } }

        val response = recordItemList.map { recordItem ->
            LocationDiary(
                thumbnailUri = recordItem.fileUri,
                location = Location.getInstanceByProvinceId(recordItem.provinceId!!),
                id = recordItem.id.toString()
            )
        }
        return BaseResponse.Success(data = response)
    }

    override suspend fun getDiaryListInProvince(provinceId: Int): BaseResponse<List<LocationDiary>> {
        val recordItemList = Province.getSameGroupProvinceList(Province.findProvince(provinceId))
            .map { it.id }
            .map { recordDao.getRecordInMapProvince(it) }
            .flatten()
            .getSingleItemPerId()
            .groupBy { it.cityGroupId }.values
            .map { recordItemList -> recordItemList.maxBy{ it.date } }

        val response = recordItemList.map { recordItem ->
            LocationDiary(
                thumbnailUri = recordItem.fileUri,
                location = Location(
                    id = LocationId(recordItem.cityGroupId!!),
                    name = City.getGroupName(recordItem.provinceId!!),
                    provinceId = LocationId(recordItem.provinceId!!),
                    type = LocationType.CITY_GROUP
                ),
                id = recordItem.id.toString()
            )
        }
        return BaseResponse.Success(data = response)
    }
}