package com.strayalphaca.data.diary.utils

import com.strayalphaca.data.all.model.DiaryDto
import com.strayalphaca.data.all.model.FileDto
import com.strayalphaca.domain.diary.model.*

fun diaryDtoToDiaryDetail(diaryDto: DiaryDto) : DiaryDetail {
    val dateString = diaryDto.date
    return DiaryDetail(
        id = diaryDto.id,
        date = dateString,
        weather = weatherStringToEnum(diaryDto.weather),
        feeling = feelingStringToEnum(diaryDto.feeling),
        content = diaryDto.content,
        files = diaryDto.medias.map { fileDtoToFile(it) },
        createdAt = diaryDto.createdAt,
        voiceFile = diaryDto.voice?.let { fileDtoToFile(it) },
        cityId = diaryDto.cityId
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

internal fun weatherStringToEnum(weather : String?) : Weather? {
    return when (weather) {
        "SUNNY" -> Weather.SUNNY
        "PARTLY_CLOUDY" -> Weather.PARTLY_CLOUDY
        "CLOUDY" -> Weather.CLOUDY
        "STORMY" -> Weather.STORMY
        "RAINY" -> Weather.RAINY
        "SNOWY" -> Weather.SNOWY
        "WINDY" -> Weather.WINDY
        else -> null
    }
}

fun fileDtoToFile(fileDto: FileDto) : File {
    return File(
        id = fileDto.originName,
        shortLink = fileDto.shortLink ?: fileDto.uploadedLink,
        originalLink = fileDto.uploadedLink,
        type = when (fileDto.type) {
            "video", "VIDEO" -> FileType.VIDEO
            "voice", "VOICE" -> FileType.VOICE
            else -> FileType.IMAGE
        }
    )
}