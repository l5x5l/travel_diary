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
                Position(x = 0.358f, y = 0.501f)
            }
            2 -> { // 부산
                Position(x = 0.771f, y = 0.579f)
            }
            3 -> { // 대구
                Position(x = 0.443f, y = 0.708f)
            }
            4 -> { // 인천
                Position(x = 0.229f, y = 0.529f)
            }
            5 -> { // 광주
                Position(x = 0.374f, y = 0.365f)
            }
            6 -> { // 대전
                Position(x = 0.866f, y = 0.564f)
            }
            7 -> { // 울산
                Position(x = 0.882f, y = 0.428f)
            }
            8 -> { // 세종
                Position(x = 0.771f, y = 0.436f)
            }
            18 -> { // 김포/고양/파주/연천/동두천/양주/의정부/포천/가평/남양주/구리
                Position(x = 0.458f, y = 0.257f)
            }
            19 -> { // 양평/여주/이천/광주/하남/성남/용인
                Position(x = 0.695f, y = 0.620f)
            }
            20 -> { // 안성/평택/오산/수원/화성/안산/시흥/부천/광명/안양/군포/과청/의왕
                Position(x = 0.282f, y = 0.771f)
            }
            21 -> { // 철원/화천/양구
                Position(x = 0.340f, y = 0.347f)
            }
            22 -> { // 춘천
                Position(x = 0.271f, y = 0.465f)
            }
            23 -> { // 원주
                Position(x = 0.347f, y = 0.657f)
            }
            24 -> { // 홍천/횡성/평창/영원
                Position(x = 0.5f, y = 0.564f)
            }
            25 -> { // 인제/고성/속초/양양
                Position(x = 0.614f, y = 0.347f)
            }
            26 -> { // 강릉
                Position(x = 0.767f, y = 0.521f)
            }
            27 -> { // 정선/동해/삼천/태백
                Position(x = 0.828f, y = 0.672f)
            }
            28 -> { // 태안/서산/당진
                Position(x = 0.305f, y = 0.262f)
            }
            29 -> { // 홍성/예산
                Position(x = 0.393f, y = 0.435f)
            }
            30 -> { // 아산/천안
                Position(x = 0.668f, y = 0.295f)
            }
            31 -> { // 보령/서천
                Position(x = 0.317f, y = 0.610f)
            }
            32 -> { // 청양/부여/공주
                Position(x = 0.576f, y = 0.531f)
            }
            33 -> { // 논산/금산
                Position(x = 0.763f, y = 0.693f)
            }
            34 -> { // 진천/음성/중평/괴산
                Position(x = 0.271f, y = 0.317f)
            }
            35 -> { // 청주/청원
                Position(x = 0.175f, y = 0.476f)
            }
            36 -> { // 보은/옥천/영동
                Position(x = 0.298f, y = 0.617f)
            }
            37 -> { // 충주
                Position(x = 0.488f, y = 0.219f)
            }
            38 -> { // 제천/단양
                Position(x = 0.752f, y = 0.284f)
            }
            39 -> { // 군산/익산/김제/전주/완주
                Position(x = 0.424f, y = 0.347f)
            }
            40 -> { // 진안/무주/장수
                Position(x = 0.750f, y = 0.423f)
            }
            41 -> { // 부안/정읍/고창
                Position(x = 0.175f, y = 0.514f)
            }
            42 -> { // 임실/순창/남원
                Position(x = 0.557f, y = 0.561f)
            }
            43 -> { // 장성/영광/함평
                Position(x = 0.179f, y = 0.32f)
            }
            44 -> { // 전담양/곡성/구례
                Position(x = 0.714f, y = 0.34f)
            }
            45 -> { // 신안/무안/목포
                Position(x = 0.145f, y = 0.476f)
            }
            46 -> { // 나주/화순
                Position(x = 0.473f, y = 0.446f)
            }
            47 -> { // 영암/장흥/강진/완도
                Position(x = 0.347f, y = 0.574f)
            }
            48 -> { // 보성/순천/광양/여수/고흥
                Position(x = 0.687f, y = 0.499f)
            }
            49 -> { // 해남/진도
                Position(x = 0.179f, y = 0.65f)
            }
            50 -> { // 상주/문경/예천/영주/봉화
                Position(x = 0.29f, y = 0.307f)
            }
            51 -> { // 울진/영양/영덕
                Position(x = 0.844f, y = 0.307f)
            }
            52 -> { // 안동/청송
                Position(x = 0.626f, y = 0.418f)
            }
            53 -> { // 의성
                Position(x = 0.435f, y = 0.486f)
            }
            54 -> { // 김천/구미/칠곡
                Position(x = 0.252f, y = 0.564f)
            }
            55 -> { // 성주/고령
                Position(x = 0.198f, y = 0.713f)
            }
            56 -> { // 영천/경산/청도
                Position(x = 0.622f, y = 0.655f)
            }
            57 -> { // 포항/경주
                Position(x = 0.844f, y = 0.632f)
            }
            58 -> { // 거창/함양/산청
                Position(x = 0.137f, y = 0.348f)
            }
            59 -> { // 합천/의령/창녕/밀양
                Position(x = 0.408f, y = 0.428f)
            }
            60 -> { // 하동/사천/진주/남해
                Position(x = 0.191f, y = 0.384f)
            }
            61 -> { // 함안/창원/김해/양산
                Position(x = 0.561f, y = 0.534f)
            }
            62 -> { // 고성/거제
                Position(x = 0.382f, y = 0.64f)
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
}