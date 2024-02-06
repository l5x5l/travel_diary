package com.strayalphaca.travel_diary.data.map.data_source

import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.map.model.LocationDiary

interface MapDataSource {
    suspend fun getDiaryListInNationWide() : BaseResponse<List<LocationDiary>>
    suspend fun getDiaryListInProvince(provinceId : Int) : BaseResponse<List<LocationDiary>>
}