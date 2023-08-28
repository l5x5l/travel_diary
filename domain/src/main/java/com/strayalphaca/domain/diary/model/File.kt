package com.strayalphaca.domain.diary.model

data class File(
    val id : String,
    val type : FileType,
    val fileLink : String,
    val thumbnailLink : String?= null
)

enum class FileType {
    IMAGE, VIDEO, VOICE
}