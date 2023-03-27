package com.strayalphaca.domain.diary.model

data class DiaryDetail(
    val id : String,
    val date : String,
    val weather : Weather?,
    val feeling: Feeling,
    val content : String,
    val files : List<File>,
    val createdAt : String,
    val updatedAt : String,
    val status : DiaryStatus
)
