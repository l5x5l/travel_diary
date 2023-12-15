package com.strayalphaca.travel_diary.core.data

import com.strayalphaca.travel_diary.core.data.demo_data_source.DemoDataSourceImpl
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import java.util.Calendar

class DemoDataSourceTest {
    private var demoDataSourceImpl = DemoDataSourceImpl()

    @Before
    fun setUp() {
        demoDataSourceImpl = DemoDataSourceImpl()
    }

    @Test
    fun dataList_NationWide() {
        val dataList = demoDataSourceImpl.getTitleDiaryListNationWide()
        val provinceDataListMap = dataList.groupBy { it.provinceId }

        // 중복된 provinceId를 가진 아이템이 나오면 안됩니다.
        assertEquals(provinceDataListMap.size, dataList.size)
    }

    @Test
    fun dataList_province() {
        val targetProvinceId = 1
        val provinceGroup = listOf(1, 4, 9)
        val emptyProvinceId = 3

        val dataList = demoDataSourceImpl.getTitleDiaryListByProvinceId(targetProvinceId)
        val emptyDataList = demoDataSourceImpl.getTitleDiaryListByProvinceId(emptyProvinceId)

        // dataList의 모든 provinceId는 동일한 provinceId 그룹내 존재해야 합니다.
        // 예를 들어, 서울에 대해 데이터를 가져올 경우, [서울, 인천, 경기도]의 데이터를 최대 1개씩 가져와야 합니다.
        assertEquals(dataList.filter { it.provinceId in provinceGroup }.size, dataList.size)

        // 기록이 없는 지역에 대해서는 빈 리스트를 불러와야 합니다.
        assertEquals(0, emptyDataList.size)
    }


    @Test
    fun dataList_cityId() {
        val targetCityId = 22
        val dataList = demoDataSourceImpl.getDiaryList(targetCityId, 10, 1)
        val outRangeDataList = demoDataSourceImpl.getDiaryList(targetCityId, 10, 3)

        assertEquals(dataList.filter { it.city.id == targetCityId }.size, dataList.size)
        assertEquals(0, outRangeDataList.size)
    }

    @Test
    fun dataList_cityGroupId() {
        val targetCityGroupId = 1
        val cityIdRangeInTargetCityGroupId = 1..25

        val dataList = demoDataSourceImpl.getDiaryListByCityGroup(targetCityGroupId, 10, 1)

        // cityGroupId를 통해 데이터를 불러왔다면, 불러온 모든 데이터의 cityId는 해당 cityGroup내 속한 도시 중 하나여야 합니다.
        assertEquals(dataList.filter { it.city.id in cityIdRangeInTargetCityGroupId }.size, dataList.size)
    }

    @Test
    fun dataList_calendar() {
        // 현재 날짜에 대한 테스트
        val current = Calendar.getInstance()
        val dataList = demoDataSourceImpl.getDiaryListByMonth(current.get(Calendar.YEAR), current.get(Calendar.MONTH) + 1)

        val targetDateSubString = "${current.get(Calendar.YEAR)}-${String.format("%02d", current.get(Calendar.MONTH) + 1)}"
        assertEquals(dataList.filter{ it.date.contains(targetDateSubString) }.size, dataList.size)

        // 저번달에 대한 테스트
        val prevOneMonth = Calendar.getInstance().apply { add(Calendar.MONTH, -1) }
        val prevDataList =  demoDataSourceImpl.getDiaryListByMonth(prevOneMonth.get(Calendar.YEAR), prevOneMonth.get(Calendar.MONTH) + 1)

        val prevDateSubString = "${prevOneMonth.get(Calendar.YEAR)}-${String.format("%02d", prevOneMonth.get(Calendar.MONTH) + 1)}"
        assertEquals(prevDataList.filter{ it.date.contains(prevDateSubString) }.size, prevDataList.size)
    }
}