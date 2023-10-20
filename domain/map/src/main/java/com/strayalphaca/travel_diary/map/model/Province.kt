package com.strayalphaca.travel_diary.map.model

sealed class Province(val id: Int, val name: String, val group : Int = id) {
    object Seoul : Province(PROVINCE_SEOUL, "서울특별시")
    object Busan : Province(PROVINCE_BUSAN, "부산광역시")
    object Daegu : Province(PROVINCE_DAEGU, "대구광역시")
    object Incheon : Province(PROVINCE_INCHEON, "인천광역시")
    object Gwangju : Province(PROVINCE_GWANGJU, "광주광역시")
    object Daejeon : Province(PROVINCE_DAEJEON, "대전광역시")
    object Ulsan : Province(PROVINCE_ULSAN, "울산광역시")
    object Sejong : Province(PROVINCE_SEJONG, "세종특별자치시")
    object Gyeonggi : Province(PROVINCE_Gyeonggi, "경기도")
    object Gangwon : Province(PROVINCE_Gangwon, "강원도")
    object Chungcheongbuk : Province(PROVINCE_Chungcheongbuk, "충청북도")
    object Chungcheongnam : Province(PROVINCE_Chungcheongnam, "충청남도")
    object Jeollabuk : Province(PROVINCE_Jeollabuk, "전라북도")
    object Jeollanam : Province(PROVINCE_Jeollanam, "전라남도")
    object Gyeongsangbuk : Province(PROVINCE_Gyeongsangbuk, "경상북도")
    object Gyeongsangnam : Province(PROVINCE_Gyeongsangnam, "경상남도")
    object Jeju : Province(PROVINCE_Jeju, "제주특별자치도")
    object Ulreung : Province(PROVINCE_Ulreung, "울릉도/독도")
    companion object {
        private fun listOfProvince() = Province::class.sealedSubclasses.toSet()

        fun findProvince(provinceId : Int) : Province {
            return listOfProvince().find { it.objectInstance?.id == provinceId }?.objectInstance
                ?: throw IllegalArgumentException("Cannot find province which id is : $provinceId")
        }

        fun getSameGroupProvinceList(groupId : Int) : List<Province> {
            return listOfProvince().filter { it.objectInstance?.group == groupId }.mapNotNull { it.objectInstance }
        }

        fun getTotalProvinceList() : List<Province> {
            return listOfProvince().mapNotNull { it.objectInstance }
        }
    }
}