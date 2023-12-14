package com.strayalphaca.travel_diary.core.data.demo_data_source

import com.strayalphaca.domain.all.DiaryDate
import com.strayalphaca.travel_diary.core.data.model.CityDto
import com.strayalphaca.travel_diary.core.data.model.DiaryDto
import com.strayalphaca.travel_diary.core.data.model.DiaryItemDto
import com.strayalphaca.travel_diary.core.data.model.ImageDto
import java.util.Calendar

class DemoDataSourceImpl : DemoDataSource {
    override fun getDiaryDetail(id: String): DiaryDto {
        return dataList.find { it.id == id } ?: throw IllegalStateException("Cannot found diary, id : $id")
    }

    override fun getDiaryList(cityId: Int, perPage: Int, offset: Int): List<DiaryItemDto> {
        val filteredData = dataList.filter { it.cityId == cityId }.map { it.toDiaryItemDto() }

        val startIndex =  perPage * (offset - 1)
        val endIndex = (startIndex + perPage).coerceAtMost(filteredData.size)

        return if (filteredData.size <= startIndex) emptyList() else filteredData.subList(startIndex, endIndex)
    }

    override fun getDiaryListByCityGroup(
        cityGroupId: Int,
        perPage: Int,
        offset: Int
    ): List<DiaryItemDto> {
        val filteredData = dataList.filter { getCityGroupIdByCityId(it.cityId) == cityGroupId }.map { it.toDiaryItemDto() }

        val startIndex =  perPage * (offset - 1)
        val endIndex = (startIndex + perPage).coerceAtMost(filteredData.size)

        return if (filteredData.size <= startIndex) emptyList() else filteredData.subList(startIndex, endIndex)
    }

    override fun getDiaryListByMonth(year: Int, month: Int): List<DiaryDto> {
        val startDate = Calendar.getInstance().apply{ set(Calendar.DAY_OF_MONTH, 1) }
        val endDate = Calendar.getInstance().apply{
            set(Calendar.DAY_OF_MONTH, 1)
            add(Calendar.MONTH, 1)
        }

        val startDateString = DiaryDate.getInstanceFromCalendar(startDate).toString()
        val endDateString = DiaryDate.getInstanceFromCalendar(endDate).toString()

        return dataList.filter { it.date >= startDateString && it.date < endDateString }
    }

    override fun getTitleDiaryListByProvinceId(provinceId: Int): List<DiaryItemDto> {
        return dataList
            .filter { getProvinceIdByCityId(it.cityId) == provinceId }
            .groupBy { getCityGroupIdByCityId(it.cityId) }
            .values
            .map { it[0].toDiaryItemDto() }
    }

    override fun getTitleDiaryListNationWide(): List<DiaryItemDto> {
        return dataList
            .groupBy { getProvinceIdByCityId(it.cityId) }
            .values
            .map { it[0].toDiaryItemDto() }
    }

    private fun getAddedDateCalendar(dayAdd : Int) : Calendar {
        val todayCalendar = Calendar.getInstance()
        todayCalendar.add(Calendar.DAY_OF_MONTH, dayAdd)
        return todayCalendar
    }

    private val dataList = listOf(
        DiaryDto(
            id = "1",
            date = DiaryDate.getInstanceFromCalendar(getAddedDateCalendar(-1)).toString(),
            content = "오늘은 도서관에서 조용한 오후를 보냈다. 책 속 세계에 빠져들면서 시간이 빠르게 흘렀고, 마음이 편안해졌다.",
            medias = listOf(),
            cityId = 22
        ).apply { dateStringFormat = "yyyy-MM-dd" },
        DiaryDto(
            id = "2",
            date = DiaryDate.getInstanceFromCalendar(getAddedDateCalendar(-3)).toString(),
            content = "바쁜 도심 속에서 찾은 작은 정원에서 휴식을 취했다. 푸릇한 잔디밭과 시원한 바람은 마음을 가라앉히는 데에 딱이었다.",
            medias = listOf(),
            cityId = 22
        ).apply { dateStringFormat = "yyyy-MM-dd" },
        DiaryDto(
            id = "3",
            date = DiaryDate.getInstanceFromCalendar(getAddedDateCalendar(-4)).toString(),
            content = "오늘은 뭔가 일이 손에 안잡혔다. 저녁 노을이 내려앉을 때, 혼자 공원을 돌아다니며 마음을 가다듬었다.\n내일은 나아지겠지",
            medias = listOf(),
            cityId = 22
        ).apply { dateStringFormat = "yyyy-MM-dd" },
        DiaryDto(
            id = "4",
            date = DiaryDate.getInstanceFromCalendar(getAddedDateCalendar(-7)).toString(),
            content = "비가 내릴 때, 창가에 앉아 커피를 마시며 책을 읽었다. 비소리와 함께 느껴지는 여유는 일상의 소소한 행복이었다.",
            medias = listOf(),
            cityId = 34
        ).apply { dateStringFormat = "yyyy-MM-dd" },
        DiaryDto(
            id = "5",
            date = DiaryDate.getInstanceFromCalendar(getAddedDateCalendar(-9)).toString(),
            content = "오늘은 컬러링 북을 펴고 마음껏 색칠하며 스트레스를 해소했다. 다양한 컬러로 가득 채워진 그림은 마음을 즐겁게 만들었다.",
            medias = listOf(),
            cityId = 53
        ).apply { dateStringFormat = "yyyy-MM-dd" },
        DiaryDto(
            id = "6",
            date = DiaryDate.getInstanceFromCalendar(getAddedDateCalendar(-10)).toString(),
            content = "미술 갤러리를 돌아다니며 다양한 작품을 감상했다. 예술의 아름다움에 감동하며, 창의성이 자극된 하루였다.",
            medias = listOf(),
            cityId = 69
        ).apply { dateStringFormat = "yyyy-MM-dd" },
        DiaryDto(
            id = "7",
            date = DiaryDate.getInstanceFromCalendar(getAddedDateCalendar(-11)).toString(),
            content = "친구들과 함께 한 소소한 소풍은 정말 즐거웠다. 함께 나눈 대화와 웃음 속에서 나의 일상이 더욱 풍성해졌다.",
            medias = listOf(),
            cityId = 23
        ).apply { dateStringFormat = "yyyy-MM-dd" },
        DiaryDto(
            id = "8",
            date = DiaryDate.getInstanceFromCalendar(getAddedDateCalendar(-12)).toString(),
            content = "자전거를 타고 모르던 공원을 발견했다. 신선한 공기와 새소리에 감사하며, 자전거 타기의 재미를 느낄 수 있었다.",
            medias = listOf(),
            cityId = 23
        ).apply { dateStringFormat = "yyyy-MM-dd" },
        DiaryDto(
            id = "9",
            date = DiaryDate.getInstanceFromCalendar(getAddedDateCalendar(-15)).toString(),
            content = "오랜만에 가까운 친구를 만났다. 오랜만에 만나도 변하지 않는 모습을 보니 이유는 모르겠지만 마음이 편해졌다.",
            medias = listOf(),
            cityId = 128
        ).apply { dateStringFormat = "yyyy-MM-dd" },
        DiaryDto(
            id = "10",
            date = DiaryDate.getInstanceFromCalendar(getAddedDateCalendar(-17)).toString(),
            content = "이른 아침 바다에서 해돋이를 감상했다. 푸른 바다와 떠오르는 해는 새로운 시작을 의미하며, 마음을 활기차게 했다.",
            medias = listOf(),
            cityId = 235
        ).apply { dateStringFormat = "yyyy-MM-dd" },
        DiaryDto(
            id = "11",
            date = DiaryDate.getInstanceFromCalendar(getAddedDateCalendar(-18)).toString(),
            content = "오늘은 오래된 건물들과 복잡한 골목길이 얽힌 역사의 향기 가득한 도시를 돌아다녔다. 각별한 순간들이 나를 과거로 이끌어 갔다.",
            medias = listOf(),
            cityId = 156
        ).apply { dateStringFormat = "yyyy-MM-dd" },
    )
}

// 이 아래부터는 오직 데모용으로 만든 함수, 상수입니다
// 데모 데이터를 다루는 것 이외의 사용을 금합니다.
private fun getProvinceIdByCityId(cityId : Int?) : Int {
    return when(cityId) {
        22, 23 -> 1
        34 -> 2
        53 -> 4
        69 -> 6
        128 -> 11
        156 -> 13
        235 -> 17
        else -> 0
    }
}

private fun getCityGroupIdByCityId(cityId : Int?) : Int {
    return when(cityId) {
        22, 23 -> 1
        34 -> 2
        53 -> 4
        69 -> 6
        128 -> 37
        156 -> 39
        235 -> 63
        else -> 0
    }
}

private fun DiaryDto.toDiaryItemDto() : DiaryItemDto {
    val imageLink = medias.getOrNull(0)?.uploadedLink
    val provinceId = getProvinceIdByCityId(cityId)

    return DiaryItemDto(
        id = id,
        image = imageLink?.let { ImageDto(it, it, null) },
        city = cityId?.let { CityDto(it, "") } ?: CityDto(1, ""),
        provinceId = provinceId
    )
}