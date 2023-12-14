package com.strayalphaca.travel_diary.core.data.demo_data_source

import com.strayalphaca.travel_diary.core.data.model.DiaryDto
import com.strayalphaca.travel_diary.core.data.model.DiaryItemDto

interface DemoDataSource {
    fun getDiaryDetail(id : String) : DiaryDto
    fun getDiaryList(cityId : Int, perPage : Int, offset : Int) : List<DiaryItemDto>
    fun getDiaryListByCityGroup(cityGroupId : Int, perPage : Int, offset : Int) : List<DiaryItemDto>
    fun getDiaryListByMonth(year : Int, month : Int) : List<DiaryDto>
    fun getTitleDiaryListByProvinceId(provinceId : Int) : List<DiaryItemDto>
    fun getTitleDiaryListNationWide() : List<DiaryItemDto>
}