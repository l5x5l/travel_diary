package com.strayalphaca.presentation.utils

import com.strayalphaca.presentation.R
import com.strayalphaca.travel_diary.map.model.PROVINCE_BUSAN
import com.strayalphaca.travel_diary.map.model.PROVINCE_Chungcheongbuk
import com.strayalphaca.travel_diary.map.model.PROVINCE_Chungcheongnam
import com.strayalphaca.travel_diary.map.model.PROVINCE_DAEGU
import com.strayalphaca.travel_diary.map.model.PROVINCE_DAEJEON
import com.strayalphaca.travel_diary.map.model.PROVINCE_GWANGJU
import com.strayalphaca.travel_diary.map.model.PROVINCE_Gangwon
import com.strayalphaca.travel_diary.map.model.PROVINCE_Gyeonggi
import com.strayalphaca.travel_diary.map.model.PROVINCE_Gyeongsangbuk
import com.strayalphaca.travel_diary.map.model.PROVINCE_Gyeongsangnam
import com.strayalphaca.travel_diary.map.model.PROVINCE_INCHEON
import com.strayalphaca.travel_diary.map.model.PROVINCE_Jeju
import com.strayalphaca.travel_diary.map.model.PROVINCE_Jeollabuk
import com.strayalphaca.travel_diary.map.model.PROVINCE_Jeollanam
import com.strayalphaca.travel_diary.map.model.PROVINCE_SEJONG
import com.strayalphaca.travel_diary.map.model.PROVINCE_SEOUL
import com.strayalphaca.travel_diary.map.model.PROVINCE_ULSAN
import com.strayalphaca.travel_diary.map.model.PROVINCE_Ulreung


fun getMapImageResourceById(id: Int?): Int {
    return when (id) {
        null -> {
            R.drawable.ic_map_korea
        }
        PROVINCE_Gyeonggi, PROVINCE_SEOUL, PROVINCE_INCHEON -> {
            R.mipmap.img_map_gyeonggi
        }
        PROVINCE_Gangwon -> {
            R.mipmap.img_map_gangwon
        }
        PROVINCE_Chungcheongbuk -> {
            R.mipmap.img_map_chungbuk
        }
        PROVINCE_Chungcheongnam, PROVINCE_DAEJEON, PROVINCE_SEJONG -> {
            R.mipmap.img_map_chungnam
        }
        PROVINCE_Jeollabuk -> {
            R.mipmap.img_map_jeonbuk
        }
        PROVINCE_Jeollanam, PROVINCE_GWANGJU -> {
            R.mipmap.img_map_jeonnam
        }
        PROVINCE_Gyeongsangbuk, PROVINCE_DAEGU -> {
            R.mipmap.img_map_gyeongbuk
        }
        PROVINCE_Gyeongsangnam, PROVINCE_BUSAN, PROVINCE_ULSAN -> {
            R.mipmap.img_map_gyeongnam
        }
        PROVINCE_Jeju -> {
            R.mipmap.img_map_jeju
        }
        else -> {
            R.drawable.ic_map_korea
        }
    }
}

fun getMapImageRatioById(id : Int?) : Float {
    return when (id) {
        null -> {
            0.66f
        }
        PROVINCE_Gyeonggi, PROVINCE_SEOUL, PROVINCE_INCHEON -> {
            0.74f
        }
        PROVINCE_Gangwon -> {
            1.07f
        }
        PROVINCE_Chungcheongbuk -> {
            0.935f
        }
        PROVINCE_Chungcheongnam, PROVINCE_DAEJEON, PROVINCE_SEJONG  -> {
            1.035f
        }
        PROVINCE_Jeollabuk -> {
            1.40f
        }
        PROVINCE_Jeollanam, PROVINCE_GWANGJU -> {
            1.15f
        }
        PROVINCE_Gyeongsangbuk, PROVINCE_DAEGU -> {
            0.9f
        }
        PROVINCE_Gyeongsangnam, PROVINCE_BUSAN, PROVINCE_ULSAN -> {
            1.26f
        }
        PROVINCE_Jeju -> {
            1.959f
        }
        PROVINCE_Ulreung -> {
            0.66f
        }
        else -> {
            0.66f
        }
    }
}