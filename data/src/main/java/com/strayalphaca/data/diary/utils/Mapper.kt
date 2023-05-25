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
        files = diaryDto.files.map { fileDtoToFile(it) },
        createdAt = diaryDto.createdAt,
        updatedAt = diaryDto.updatedAt,
        status = DiaryStatus.NORMAL,
        voiceFile = diaryDto.voice?.let { fileDtoToFile(it) }
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
        id = fileDto.id,
        shortLink = fileDto.shortLink,
        originalLink = fileDto.originalLink,
        type = FileType.IMAGE
    )
}