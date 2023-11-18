package com.strayalphaca.presentation.screens.diary.util

import com.strayalphaca.travel_diary.diary.model.FileType

private typealias FileTypeInFile = com.strayalphaca.travel_diary.domain.file.model.FileType
private typealias FileTypeInDiary = FileType

fun fileTypeTransfer(fileType : FileTypeInDiary) : FileTypeInFile {
    return when (fileType) {
        FileTypeInDiary.IMAGE -> {
            FileTypeInFile.Image
        }
        FileTypeInDiary.VIDEO -> {
            FileTypeInFile.Video
        }
        FileTypeInDiary.VOICE -> {
            FileTypeInFile.Voice
        }
    }
}