package com.strayalphaca.data.diary.utils

import com.strayalphaca.data.all.model.DiaryDto
import com.strayalphaca.data.all.model.FileDto
import com.strayalphaca.domain.diary.model.*

// todo typeScript enum 클래스 사용하는 부분 수정하기
fun diaryDtoToDiaryDetail(diaryDto: DiaryDto) : DiaryDetail {
    val dateString = diaryDto.date
    return DiaryDetail(
        id = diaryDto.id,
        date = dateString,
        weather = null,
        feeling = Feeling.HAPPY,
        content = diaryDto.content,
        files = diaryDto.files.map { fileDtoToFile(it) },
        createdAt = diaryDto.createdAt,
        updatedAt = diaryDto.updatedAt,
        status = DiaryStatus.NORMAL
    )
}

fun fileDtoToFile(fileDto: FileDto) : File {
    return File(
        id = fileDto.id,
        shortLink = fileDto.shortLink,
        originalLink = fileDto.originalLink,
        type = FileType.IMAGE
    )
}