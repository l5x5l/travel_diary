package com.strayalphaca.travel_diary.diary.use_case

import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.diary.repository.DiaryRepository
import javax.inject.Inject

class UseCaseCheckWrittenOn @Inject constructor(
    private val repository : DiaryRepository
) {
    suspend operator fun invoke(year : Int, month : Int, day : Int) : BaseResponse<Boolean> {
        return repository.checkWrittenOn(year, month, day)
    }
}