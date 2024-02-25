package com.strayalphaca.travel_diary.map.model

data class LocationWithData(
    val location : Location? = null,
    val data : List<LocationDiary> = emptyList()
) {
    // 두 객체가 서로 다른 참조를 가지고 있고
    // 두 객체 모두 location == null, data == emptyList 인 경우, false 리턴
    override fun equals(other: Any?): Boolean {
        if (
            other !== this && // 반사성 체크
            other is LocationWithData &&
            (location == null && other.location == null) &&
            (data.isEmpty() && other.data.isEmpty())
        ) {
            return false
        }

        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = location?.hashCode() ?: 0
        result = 31 * result + data.hashCode()
        return result
    }
}