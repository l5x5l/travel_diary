package com.strayalphaca.presentation.utils

import com.strayalphaca.presentation.R
import com.strayalphaca.travel_diary.map.model.PROVINCE_Chungcheongbuk
import com.strayalphaca.travel_diary.map.model.PROVINCE_Chungcheongnam
import com.strayalphaca.travel_diary.map.model.PROVINCE_Gangwon
import com.strayalphaca.travel_diary.map.model.PROVINCE_Gyeonggi
import com.strayalphaca.travel_diary.map.model.PROVINCE_Gyeongsangbuk
import com.strayalphaca.travel_diary.map.model.PROVINCE_Gyeongsangnam
import com.strayalphaca.travel_diary.map.model.PROVINCE_Jeju
import com.strayalphaca.travel_diary.map.model.PROVINCE_Jeollabuk
import com.strayalphaca.travel_diary.map.model.PROVINCE_Jeollanam


fun getMapImageResourceById(id: Int?): Int {
    return when (id) {
        null -> {
            R.drawable.ic_map_korea
        }
        PROVINCE_Gyeonggi -> {
            R.mipmap.img_map_gyeonggi
        }
        PROVINCE_Gangwon -> {
            R.mipmap.img_map_gangwon
        }
        PROVINCE_Chungcheongbuk -> {
            R.mipmap.img_map_chungbuk
        }
        PROVINCE_Chungcheongnam -> {
            R.mipmap.img_map_chungnam
        }
        PROVINCE_Jeollabuk -> {
            R.mipmap.img_map_jeonbuk
        }
        PROVINCE_Jeollanam -> {
            R.mipmap.img_map_jeonnam
        }
        PROVINCE_Gyeongsangbuk -> {
            R.mipmap.img_map_gyeongbuk
        }
        PROVINCE_Gyeongsangnam -> {
            R.mipmap.img_map_gyeongnam
        }
        PROVINCE_Jeju -> {
            // 제주도 이미지 필요!
            R.drawable.ic_map_korea
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
        PROVINCE_Gyeonggi -> {
            0.74f
        }
        PROVINCE_Gangwon -> {
            1.07f
        }
        PROVINCE_Chungcheongbuk -> {
            0.935f
        }
        PROVINCE_Chungcheongnam -> {
            1.035f
        }
        PROVINCE_Jeollabuk -> {
            1.40f
        }
        PROVINCE_Jeollanam -> {
            1.15f
        }
        PROVINCE_Gyeongsangbuk -> {
            0.9f
        }
        PROVINCE_Gyeongsangnam -> {
            1.26f
        }
        PROVINCE_Jeju -> {
            0.66f
        }
        else -> {
            0.66f
        }
    }
}