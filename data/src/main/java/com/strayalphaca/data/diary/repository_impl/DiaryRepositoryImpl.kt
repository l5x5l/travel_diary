package com.strayalphaca.data.diary.repository_impl

import com.strayalphaca.data.all.utils.mapBaseResponse
import com.strayalphaca.data.diary.data_source.DiaryDataSource
import com.strayalphaca.data.diary.utils.diaryDtoToDiaryDetail
import com.strayalphaca.domain.diary.model.DiaryDetail
import com.strayalphaca.domain.diary.repository.DiaryRepository
import com.strayalphaca.domain.model.BaseResponse
import javax.inject.Inject

class DiaryRepositoryImpl @Inject constructor(
    private val dataSource: DiaryDataSource
): DiaryRepository {
    override suspend fun getDiaryDetail(id: String): BaseResponse<DiaryDetail> {
        val data = dataSource.getDiaryData(id)
        return mapBaseResponse(data, ::diaryDtoToDiaryDetail)
    }
}