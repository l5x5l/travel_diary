package com.strayalphaca.travel_diary.data.diary.repository_impl

import com.strayalphaca.travel_diary.core.data.utils.mapBaseResponse
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.data.diary.data_source.DiaryDataSource
import com.strayalphaca.travel_diary.data.diary.utils.diaryDtoToDiaryDetail
import com.strayalphaca.travel_diary.diary.model.DiaryDetail
import com.strayalphaca.travel_diary.diary.model.DiaryItem
import com.strayalphaca.travel_diary.diary.model.DiaryItemUpdate
import com.strayalphaca.travel_diary.diary.model.DiaryModifyData
import com.strayalphaca.travel_diary.diary.model.DiaryWriteData
import com.strayalphaca.travel_diary.diary.repository.DiaryRepository
import com.strayalphaca.travel_diary.map.model.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class DiaryRepositoryImpl @Inject constructor(
    private val dataSource: DiaryDataSource
): DiaryRepository {
    private val diaryItemUpdateChannel = MutableSharedFlow<DiaryItemUpdate>()

    override suspend fun getDiaryDetail(id: String): BaseResponse<DiaryDetail> {
        val data = dataSource.getDiaryData(id)
        return mapBaseResponse(data, ::diaryDtoToDiaryDetail)
    }

    override suspend fun getDiaryList(cityId: Int, perPage: Int, offset: Int): List<DiaryItem> {
        val data = dataSource.getDiaryList(cityId, perPage, offset)
        return data.map { diaryItemDto ->
            DiaryItem(
                id = diaryItemDto.id,
                imageUrl = diaryItemDto.image?.shortLink ?: diaryItemDto.image?.uploadedLink,
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
                imageUrl = diaryItemDto.image?.shortLink ?: diaryItemDto.image?.uploadedLink,
                cityName = City.findCity(diaryItemDto.city.id).name
            )
        }
    }

    override suspend fun uploadDiary(diaryWriteData: DiaryWriteData): BaseResponse<String> {
        return BaseResponse.Success(data = diaryWriteData.recordDate.toString())
    }

    override suspend fun modifyDiary(diaryModifyData: DiaryModifyData): BaseResponse<Nothing> {
        diaryItemUpdateChannel.emit(DiaryItemUpdate.Modify(diaryModifyData.toDiaryItem()))
        return BaseResponse.EmptySuccess
    }

    override suspend fun deleteDiary(diaryId: String): BaseResponse<Nothing> {
        diaryItemUpdateChannel.emit(DiaryItemUpdate.Delete(DiaryItem(id = diaryId, cityName = "-", imageUrl = null)))
        return BaseResponse.EmptySuccess
    }

    override suspend fun getDiaryItemUpdate(): Flow<DiaryItemUpdate> {
        return diaryItemUpdateChannel
    }

    override suspend fun getDiaryCount(): BaseResponse<Int> {
        return BaseResponse.Success(data = 100)
    }
}