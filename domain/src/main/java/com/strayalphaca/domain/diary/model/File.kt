package com.strayalphaca.domain.diary.model

data class File(
    val id : String,
    val type : FileType,
    val shortLink : String,
    val originalLink : String
)

enum class FileType {
    IMAGE, VIDEO, VOICE
}