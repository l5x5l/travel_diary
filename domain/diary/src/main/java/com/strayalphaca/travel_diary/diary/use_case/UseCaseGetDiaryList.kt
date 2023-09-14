package com.strayalphaca.travel_diary.diary.use_case

import com.strayalphaca.travel_diary.diary.model.DiaryItem
import com.strayalphaca.travel_diary.diary.repository.DiaryRepository
import javax.inject.Inject

class UseCaseGetDiaryList @Inject constructor(
    private val repository: DiaryRepository
) {
    suspend operator fun invoke(cityId : Int, perPage : Int, offset : Int) : List<DiaryItem> {
        return repository.getDiaryList(cityId, perPage, offset)
    }

    suspend fun getByCityGroupId(cityGroupId : Int, perPage: Int, offset: Int) : List<DiaryItem> {
        return repository.getDiaryListByCityGroup(cityGroupId, perPage, offset)
    }
}