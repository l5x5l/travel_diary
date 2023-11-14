package com.strayalphaca.travel_diary.diary.model

sealed class DiaryItemUpdate(val item : DiaryItem) {
    class Modify(item : DiaryItem) : DiaryItemUpdate(item)
    class Delete(item : DiaryItem) : DiaryItemUpdate(item)
}