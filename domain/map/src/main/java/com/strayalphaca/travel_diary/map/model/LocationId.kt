package com.strayalphaca.travel_diary.map.model

@JvmInline
value class LocationId(val id : Int) {
    fun getProvincePosition() : Position {
        return when (id) {
            1 -> { // 서울
                Position(x = 0.282f, y = 0.196f)
            }
            2 -> { // 부산
                Position(x = 0.580f, y = 0.637f)
            }
            3 -> { // 대구
                Position(x = 0.679f, y = 0.420f)
            }
            4 -> { // 인천
                Position(x = 0.282f, y = 0.196f)
            }
            5 -> { // 광주
                Position(x = 0.171f, y = 0.712f)
            }
            6 -> { // 대전
                Position(x = 0.209f, y = 0.375f)
            }
            7 -> { // 울산
                Position(x = 0.580f, y = 0.637f)
            }
            8 -> { // 세종
                Position(x = 0.209f, y = 0.375f)
            }
            9 -> { // 경기도
                Position(x = 0.282f, y = 0.196f)
            }
            10 -> { // 강원도
                Position(x = 0.603f, y = 0.136f)
            }
            11 -> { // 충청북도
                Position(x = 0.450f, y = 0.329f)
            }
            12 -> { // 충청남도
                Position(x = 0.209f, y = 0.375f)
            }
            13 -> { // 전라북도
                Position(x = 0.259f, y = 0.534f)
            }
            14 -> { // 전라남도
                Position(x = 0.171f, y = 0.712f)
            }
            15 -> { // 경상북도
                Position(x = 0.679f, y = 0.420f)
            }
            16 -> { // 경상남도
                Position(x = 0.580f, y = 0.637f)
            }
            17 -> { // 제주도
                Position(x = 0.114f, y = 0.957f)
            }
            else -> {
                Position(x = -1.0f, y = -1.0f)
            }
        }
    }

    fun getCityGroupPosition() : Position {
        return when (id) {
            1 -> { // 서울
                Position(x = 0.358f, y = getYPositionRatioByLocalImage(0.501f, gyeonggi))
            }
            2 -> { // 부산
                Position(x = 0.771f, y = getYPositionRatioByLocalImage(0.579f, gyeongnam))
            }
            3 -> { // 대구
                Position(x = 0.443f, y = getYPositionRatioByLocalImage(0.708f, gyeongbuk))
            }
            4 -> { // 인천
                Position(x = 0.229f, y = getYPositionRatioByLocalImage(0.529f, gyeonggi))
            }
            5 -> { // 광주
                Position(x = 0.374f, y = getYPositionRatioByLocalImage(0.365f, jeonnam))
            }
            6 -> { // 대전
                Position(x = 0.866f, y = getYPositionRatioByLocalImage(0.564f, chungnam))
            }
            7 -> { // 울산
                Position(x = 0.882f, y = getYPositionRatioByLocalImage(0.428f, gyeongnam))
            }
            8 -> { // 세종
                Position(x = 0.771f, y = getYPositionRatioByLocalImage(0.436f, chungnam))
            }
            18 -> { // 김포/고양/파주/연천/동두천/양주/의정부/포천/가평/남양주/구리
                Position(x = 0.458f, y = getYPositionRatioByLocalImage(0.257f, gyeonggi))
            }
            19 -> { // 양평/여주/이천/광주/하남/성남/용인
                Position(x = 0.695f, y = getYPositionRatioByLocalImage(0.620f, gyeonggi))
            }
            20 -> { // 안성/평택/오산/수원/화성/안산/시흥/부천/광명/안양/군포/과청/의왕
                Position(x = 0.282f, y = getYPositionRatioByLocalImage(0.771f, gyeonggi))
            }
            21 -> { // 철원/화천/양구
                Position(x = 0.340f, y = getYPositionRatioByLocalImage(0.347f, gangwon))
            }
            22 -> { // 춘천
                Position(x = 0.271f, y = getYPositionRatioByLocalImage(0.465f, gangwon))
            }
            23 -> { // 원주
                Position(x = 0.347f, y = getYPositionRatioByLocalImage(0.657f, gangwon))
            }
            24 -> { // 홍천/횡성/평창/영원
                Position(x = 0.5f, y = getYPositionRatioByLocalImage(0.564f, gangwon))
            }
            25 -> { // 인제/고성/속초/양양
                Position(x = 0.614f, y = getYPositionRatioByLocalImage(0.347f, gangwon))
            }
            26 -> { // 강릉
                Position(x = 0.767f, y = getYPositionRatioByLocalImage(0.521f, gangwon))
            }
            27 -> { // 정선/동해/삼천/태백
                Position(x = 0.828f, y = getYPositionRatioByLocalImage(0.672f, gangwon))
            }
            28 -> { // 태안/서산/당진
                Position(x = 0.305f, y = getYPositionRatioByLocalImage(0.262f, chungnam))
            }
            29 -> { // 홍성/예산
                Position(x = 0.393f, y = getYPositionRatioByLocalImage(0.435f, chungnam))
            }
            30 -> { // 아산/천안
                Position(x = 0.668f, y = getYPositionRatioByLocalImage(0.295f, chungnam))
            }
            31 -> { // 보령/서천
                Position(x = 0.317f, y = getYPositionRatioByLocalImage(0.610f, chungnam))
            }
            32 -> { // 청양/부여/공주
                Position(x = 0.576f, y = getYPositionRatioByLocalImage(0.531f, chungnam))
            }
            33 -> { // 논산/금산
                Position(x = 0.763f, y = getYPositionRatioByLocalImage(0.693f, chungnam))
            }
            34 -> { // 진천/음성/중평/괴산
                Position(x = 0.271f, y = getYPositionRatioByLocalImage(0.317f, chungbuk))
            }
            35 -> { // 청주/청원
                Position(x = 0.175f, y = getYPositionRatioByLocalImage(0.476f, chungbuk))
            }
            36 -> { // 보은/옥천/영동
                Position(x = 0.298f, y = getYPositionRatioByLocalImage(0.617f, chungbuk))
            }
            37 -> { // 충주
                Position(x = 0.488f, y = getYPositionRatioByLocalImage(0.219f, chungbuk))
            }
            38 -> { // 제천/단양
                Position(x = 0.752f, y = getYPositionRatioByLocalImage(0.284f, chungbuk))
            }
            39 -> { // 군산/익산/김제/전주/완주
                Position(x = 0.424f, y = getYPositionRatioByLocalImage(0.347f, jeonbuk))
            }
            40 -> { // 진안/무주/장수
                Position(x = 0.750f, y = getYPositionRatioByLocalImage(0.423f, jeonbuk))
            }
            41 -> { // 부안/정읍/고창
                Position(x = 0.175f, y = getYPositionRatioByLocalImage(0.514f, jeonbuk))
            }
            42 -> { // 임실/순창/남원
                Position(x = 0.557f, y = getYPositionRatioByLocalImage(0.561f, jeonbuk))
            }
            43 -> { // 장성/영광/함평
                Position(x = 0.179f, y = getYPositionRatioByLocalImage(0.32f, jeonnam))
            }
            44 -> { // 담양/곡성/구례
                Position(x = 0.714f, y = getYPositionRatioByLocalImage(0.34f, jeonnam))
            }
            45 -> { // 신안/무안/목포
                Position(x = 0.145f, y = getYPositionRatioByLocalImage(0.476f, jeonnam))
            }
            46 -> { // 나주/화순
                Position(x = 0.473f, y = getYPositionRatioByLocalImage(0.446f, jeonnam))
            }
            47 -> { // 영암/장흥/강진/완도
                Position(x = 0.347f, y = getYPositionRatioByLocalImage(0.574f, jeonnam))
            }
            48 -> { // 보성/순천/광양/여수/고흥
                Position(x = 0.687f, y = getYPositionRatioByLocalImage(0.499f, jeonnam))
            }
            49 -> { // 해남/진도
                Position(x = 0.179f, y = getYPositionRatioByLocalImage(0.65f, jeonnam))
            }
            50 -> { // 상주/문경/예천/영주/봉화
                Position(x = 0.29f, y = getYPositionRatioByLocalImage(0.307f, gyeongbuk))
            }
            51 -> { // 울진/영양/영덕
                Position(x = 0.844f, y = getYPositionRatioByLocalImage(0.307f, gyeongbuk))
            }
            52 -> { // 안동/청송
                Position(x = 0.626f, y = getYPositionRatioByLocalImage(0.418f, gyeongbuk))
            }
            53 -> { // 의성
                Position(x = 0.435f, y = getYPositionRatioByLocalImage(0.486f, gyeongbuk))
            }
            54 -> { // 김천/구미/칠곡
                Position(x = 0.252f, y = getYPositionRatioByLocalImage(0.564f, gyeongbuk))
            }
            55 -> { // 성주/고령
                Position(x = 0.198f, y = getYPositionRatioByLocalImage(0.713f, gyeongbuk))
            }
            56 -> { // 영천/경산/청도
                Position(x = 0.622f, y = getYPositionRatioByLocalImage(0.655f, gyeongbuk))
            }
            57 -> { // 포항/경주
                Position(x = 0.844f, y = getYPositionRatioByLocalImage(0.632f, gyeongbuk))
            }
            58 -> { // 거창/함양/산청
                Position(x = 0.137f, y = getYPositionRatioByLocalImage(0.348f, gyeongnam))
            }
            59 -> { // 합천/의령/창녕/밀양
                Position(x = 0.408f, y = getYPositionRatioByLocalImage(0.428f, gyeongnam))
            }
            60 -> { // 하동/사천/진주/남해
                Position(x = 0.191f, y = getYPositionRatioByLocalImage(0.584f, gyeongnam))
            }
            61 -> { // 함안/창원/김해/양산
                Position(x = 0.561f, y = getYPositionRatioByLocalImage(0.534f, gyeongnam))
            }
            62 -> { // 고성/거제/통영
                Position(x = 0.382f, y = getYPositionRatioByLocalImage(0.64f, gyeongnam))
            }
            63 -> { // 제주
                Position(x = 0.5f, y = 0.5f)
            }
            64 -> { // 서귀포
                Position(x = 0.5f, y = 0.5f)
            }
            65 -> { // 울릉도
                Position(x = 0.5f, y = 0.5f)
            }
            66 -> { // 독도
                Position(x = 0.5f, y = 0.5f)
            }
            else -> {
                Position(x = -1.0f, y = -1.0f)
            }
        }
    }

    companion object {
        const val gyeonggi = 0.887f
        const val gangwon = 0.612f
        const val chungbuk = 0.705f
        const val chungnam = 0.637f
        const val gyeongbuk = 0.728f
        const val gyeongnam = 0.512f
        const val jeonbuk = 0.463f
        const val jeonnam = 0.574f
    }
}

/**
 * 기존 높이값은 이미지의 공백 높이까지 고려해서 비율값을 구했으나,
 * 공백을 제거한 이미지를 사용하는 방향으로 변경되었기에, 이를 계산하는 함수입니다.
 *
 * positionRatioWithFullHeight : Float, 공백 높이까지 고려한 비율값
 *
 * currentImageHeightRatio : Float, (공백 제거 이미지 높이 / 공백 포함 이미지 높이)
 */
fun getYPositionRatioByLocalImage(
    positionRatioWithFullHeight : Float,
    currentImageHeightRatio : Float
) :Float {
    return (positionRatioWithFullHeight - 0.5f) / currentImageHeightRatio + 0.5f
}