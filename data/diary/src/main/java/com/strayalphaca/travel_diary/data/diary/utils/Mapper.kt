package com.strayalphaca.travel_diary.data.diary.utils

import com.strayalphaca.data.all.model.DiaryDto
import com.strayalphaca.data.all.model.DiaryItemDto
import com.strayalphaca.data.all.model.MediaFileInfoDto
import com.strayalphaca.data.all.model.VoiceFileInDiaryDto
import com.strayalphaca.domain.all.DiaryDate
import com.strayalphaca.travel_diary.data.diary.model.UploadResponseBody
import com.strayalphaca.travel_diary.diary.model.DiaryDetail
import com.strayalphaca.travel_diary.diary.model.DiaryItem
import com.strayalphaca.travel_diary.diary.model.Feeling
import com.strayalphaca.travel_diary.diary.model.File
import com.strayalphaca.travel_diary.diary.model.FileType
import com.strayalphaca.travel_diary.diary.model.Weather
import com.strayalphaca.travel_diary.map.model.City

fun diaryDtoToDiaryDetail(diaryDto: DiaryDto) : DiaryDetail {
    val dateString = diaryDto.date
    return DiaryDetail(
        id = diaryDto.id,
        date = DiaryDate.getInstanceFromDateStringBySimpleDateFormat(dateString, diaryDto.dateStringFormat ?: "yyyy-MM-dd"),
        weather = weatherStringToEnum(diaryDto.weather),
        feeling = feelingStringToEnum(diaryDto.feeling),
        content = diaryDto.content,
        files = diaryDto.medias.map { mediaFileInfoDtoToFile(it) },
        createdAt = diaryDto.createdAt,
        voiceFile = diaryDto.voice?.let { voiceFileInFileDtoToFile(it) },
        cityId = diaryDto.cityId,
        cityName = diaryDto.place ?: diaryDto.cityId?.let { City.findCity(it).name }
    )
}

fun diaryListDtoToDiaryItem(diaryItemDto: DiaryItemDto) : DiaryItem {
    return DiaryItem(
        id = diaryItemDto.id,
        imageUrl = diaryItemDto.image?.shortLink ?: diaryItemDto.image?.uploadedLink,
        cityName = City.findCity(diaryItemDto.city.id).name
    )
}

internal fun feelingStringToEnum(feeling : String) : Feeling {
    return when(feeling) {
        "HAPPY" -> Feeling.HAPPY
        "CALM" -> Feeling.CALM
        "SATISFIED" -> Feeling.SATISFIED
        "EXCITING" -> Feeling.EXCITING
        "ANGRY" -> Feeling.ANGRY
        "SAD" -> Feeling.SAD
        else -> Feeling.HAPPY
    }
}

internal fun weatherStringToEnum(weather : String?) : Weather {
    return when (weather) {
        "SUNNY" -> Weather.SUNNY
        "PARTLY_CLOUDY" -> Weather.PARTLY_CLOUDY
        "CLOUDY" -> Weather.CLOUDY
        "STORMY" -> Weather.STORMY
        "RAINY" -> Weather.RAINY
        "SNOWY" -> Weather.SNOWY
        "WINDY" -> Weather.WINDY
        else -> Weather.SUNNY
    }
}

fun mediaFileInfoDtoToFile(fileDto: MediaFileInfoDto) : File {
    return File(
        id = fileDto.id,
        fileLink = fileDto.shortLink ?: fileDto.uploadedLink,
        type = when (fileDto.type) {
            "video", "VIDEO" -> FileType.VIDEO
            "voice", "VOICE" -> FileType.VOICE
            else -> FileType.IMAGE
        },
        thumbnailLink = fileDto.thumbnailShortLink ?: fileDto.thumbnailLink
    )
}

fun voiceFileInFileDtoToFile(voiceFileInDiaryDto: VoiceFileInDiaryDto) : File {
    return File(
        id = voiceFileInDiaryDto.originName,
        fileLink = voiceFileInDiaryDto.uploadedLink,
        type = FileType.VOICE,
        thumbnailLink = null
    )
}

fun extractIdFromSignupResponse(uploadResponseBody: UploadResponseBody) : String {
    return uploadResponseBody.id
}