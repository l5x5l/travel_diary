package com.strayalphaca.travel_diary.map.model

sealed class Province(val id: Int, val name: String, val group : Int = id) {
    object Seoul : Province(1, "서울특별시")
    object Busan : Province(2, "부산광역시")
    object Daegu : Province(3, "대구광역시")
    object Incheon : Province(4, "인천광역시")
    object Gwangju : Province(5, "광주광역시")
    object Daejeon : Province(6, "대전광역시")
    object Ulsan : Province(7, "울산광역시")
    object Sejong : Province(8, "세종특별자치시")
    object Gyeonggi : Province(9, "경기도")
    object Gangwon : Province(10, "강원도")
    object Chungcheongbuk : Province(11, "충청북도")
    object Chungcheongnam : Province(12, "충청남도")
    object Jeollabuk : Province(13, "전라북도")
    object Jeollanam : Province(14, "전라남도")
    object Gyeongsangbuk : Province(15, "경상북도")
    object Gyeongsangnam : Province(16, "경상남도")
    object Jeju : Province(17, "제주특별자치도")

    companion object {
        fun listOfProvince() = Province::class.sealedSubclasses.toSet()

        fun findProvince(provinceId : Int) : Province {
            return listOfProvince().find { it.objectInstance?.id == provinceId }?.objectInstance
                ?: throw IllegalArgumentException("Cannot find province which id is : $provinceId")
        }

        fun getSameGroupProvinceList(groupId : Int) : List<Province> {
            return listOfProvince().filter { it.objectInstance?.group == groupId }.mapNotNull { it.objectInstance }
        }
    }
}