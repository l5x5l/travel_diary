package com.strayalphaca.travel_diary.diary.model

data class File(
    val id : String,
    val type : FileType,
    val fileLink : String,
    val thumbnailLink : String?= null
) {
    fun getThumbnail() : String {
        return thumbnailLink ?: fileLink
    }
}

enum class FileType {
    IMAGE, VIDEO, VOICE
}