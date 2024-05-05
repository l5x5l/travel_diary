package com.strayalphaca.travel_diary.map.model

sealed class Province(val id: Int, val name: String, val group : Int = id) {
    object Seoul : Province(PROVINCE_SEOUL, "서울특별시", PROVINCE_GROUP_SEOUL_INCHEON_Gyeonggi)
    object Busan : Province(PROVINCE_BUSAN, "부산광역시", PROVINCE_GROUP_BUSAN_ULSAN_Gyeongsangnam)
    object Daegu : Province(PROVINCE_DAEGU, "대구광역시", PROVINCE_GROUP_DAEGU_Gyeongsangbuk)
    object Incheon : Province(PROVINCE_INCHEON, "인천광역시", PROVINCE_GROUP_SEOUL_INCHEON_Gyeonggi)
    object Gwangju : Province(PROVINCE_GWANGJU, "광주광역시", PROVINCE_GROUP_GWANGJU_Jeollanam)
    object Daejeon : Province(PROVINCE_DAEJEON, "대전광역시", PROVINCE_GROUP_DAEJEON_SEJONG_Chungcheongnam)
    object Ulsan : Province(PROVINCE_ULSAN, "울산광역시", PROVINCE_GROUP_BUSAN_ULSAN_Gyeongsangnam)
    object Sejong : Province(PROVINCE_SEJONG, "세종특별자치시", PROVINCE_GROUP_DAEJEON_SEJONG_Chungcheongnam)
    object Gyeonggi : Province(PROVINCE_Gyeonggi, "경기도", PROVINCE_GROUP_SEOUL_INCHEON_Gyeonggi)
    object Gangwon : Province(PROVINCE_Gangwon, "강원도", PROVINCE_GROUP_Gangwon)
    object Chungcheongbuk : Province(PROVINCE_Chungcheongbuk, "충청북도", PROVINCE_GROUP_Chungcheongbuk)
    object Chungcheongnam : Province(PROVINCE_Chungcheongnam, "충청남도", PROVINCE_GROUP_DAEJEON_SEJONG_Chungcheongnam)
    object Jeollabuk : Province(PROVINCE_Jeollabuk, "전라북도", PROVINCE_GROUP_Jeollabuk)
    object Jeollanam : Province(PROVINCE_Jeollanam, "전라남도", PROVINCE_GROUP_GWANGJU_Jeollanam)
    object Gyeongsangbuk : Province(PROVINCE_Gyeongsangbuk, "경상북도", PROVINCE_GROUP_DAEGU_Gyeongsangbuk)
    object Gyeongsangnam : Province(PROVINCE_Gyeongsangnam, "경상남도", PROVINCE_GROUP_BUSAN_ULSAN_Gyeongsangnam)
    object Jeju : Province(PROVINCE_Jeju, "제주특별자치도", PROVINCE_GROUP_Jeju)
    object Ulreung : Province(PROVINCE_Ulreung, "울릉도/독도", PROVINCE_GROUP_Ulreung)
    companion object {
        private fun listOfProvince() = Province::class.sealedSubclasses.toSet()

        fun findProvince(provinceId : Int) : Province {
            return listOfProvince().find { it.objectInstance?.id == provinceId }?.objectInstance
                ?: throw IllegalArgumentException("Cannot find province which id is : $provinceId")
        }

        fun getSameGroupProvinceList(province: Province) : List<Province> {
            return listOfProvince().filter { it.objectInstance?.group == province.group }.mapNotNull { it.objectInstance }
        }

        fun getTotalProvinceList() : List<Province> {
            return listOfProvince().mapNotNull { it.objectInstance }
        }
    }
}