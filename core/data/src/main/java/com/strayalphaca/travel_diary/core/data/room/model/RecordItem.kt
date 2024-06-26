package com.strayalphaca.travel_diary.core.data.room.model

data class RecordItem(
    val id : Int,
    val date : String,
    val fileUri : String?,
    val locationId : Int?,
    val provinceId : Int?,
    val cityGroupId : Int?,
    val fileType : String?
) {
    companion object {
        val recordItemComparatorOnSameId =
            Comparator<RecordItem> { a, b ->
                when {
                    a.fileType == "Image" || a.fileType == "Video" -> return@Comparator -1
                    b.fileType == "Image" || b.fileType == "Video" -> return@Comparator 1
                    a.fileUri != null -> return@Comparator -1
                    b.fileUri != null -> return@Comparator 1
                    else -> return@Comparator 0
                }
            }
    }
}

fun List<RecordItem>.getSingleItemPerId() : List<RecordItem> {
    return this.groupBy { it.id }
        .map { it.value.sortedWith(RecordItem.recordItemComparatorOnSameId).first() }
        .map { if (it.fileType == "Voice") it.copy(fileUri = null) else it } // 일지 아이템에서는 음성 파일 링크는 사용되지 않으므로 null로 변경
}