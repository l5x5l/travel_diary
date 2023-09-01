package com.strayalphaca.data.diary.repository_impl

import com.strayalphaca.data.all.utils.mapBaseResponse
import com.strayalphaca.data.diary.data_source.DiaryDataSource
import com.strayalphaca.data.diary.utils.diaryDtoToDiaryDetail
import com.strayalphaca.domain.diary.model.DiaryDetail
import com.strayalphaca.domain.diary.model.DiaryItem
import com.strayalphaca.domain.diary.model.DiaryModifyData
import com.strayalphaca.domain.diary.model.DiaryWriteData
import com.strayalphaca.domain.diary.repository.DiaryRepository
import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.map.model.City
import javax.inject.Inject

class DiaryRepositoryImpl @Inject constructor(
    private val dataSource: DiaryDataSource
): DiaryRepository {
    override suspend fun getDiaryDetail(id: String): BaseResponse<DiaryDetail> {
        val data = dataSource.getDiaryData(id)
        return mapBaseResponse(data, ::diaryDtoToDiaryDetail)
    }

    override suspend fun getDiaryList(cityId: Int, perPage: Int, offset: Int): List<DiaryItem> {
        val data = dataSource.getDiaryList(cityId, perPage, offset)
        return data.map { diaryItemDto ->
            DiaryItem(
                id = diaryItemDto.id,
                imageUrl = diaryItemDto.image?.shortLink,
                cityName = City.findCity(cityId).name
            )
        }
    }

    override suspend fun getDiaryListByCityGroup(
        cityGroupId: Int,
        perPage: Int,
        offset: Int
    ): List<DiaryItem> {
        val data = dataSource.getDiaryListByCityGroup(cityGroupId, perPage, offset)
        return data.map { diaryItemDto ->
            DiaryItem(
                id = diaryItemDto.id,
                imageUrl = diaryItemDto.image?.shortLink,
                cityName = "groupId $cityGroupId"
            )
        }
    }

    override suspend fun uploadDiary(diaryWriteData: DiaryWriteData): BaseResponse<String> {
        return BaseResponse.Success(data = "1")
    }

    override suspend fun modifyDiary(diaryModifyData: DiaryModifyData): BaseResponse<Nothing> {
        return BaseResponse.EmptySuccess
    }

    override suspend fun deleteDiary(diaryId: String): BaseResponse<Nothing> {
        return BaseResponse.EmptySuccess
    }
}