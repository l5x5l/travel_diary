package com.strayalphaca.travel_diary.data.diary.repository_impl

import com.strayalphaca.travel_diary.core.data.utils.responseToBaseResponseWithMapping
import com.strayalphaca.travel_diary.core.data.utils.voidResponseToBaseResponse
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.data.diary.api.DiaryApi
import com.strayalphaca.travel_diary.data.diary.model.ModifyDiaryRequestBody
import com.strayalphaca.travel_diary.data.diary.model.UploadDiaryRequestBody
import com.strayalphaca.travel_diary.data.diary.utils.diaryDtoToDiaryDetail
import com.strayalphaca.travel_diary.data.diary.utils.diaryListDtoToDiaryItem
import com.strayalphaca.travel_diary.data.diary.utils.extractIdFromSignupResponse
import com.strayalphaca.travel_diary.diary.model.DiaryDetail
import com.strayalphaca.travel_diary.diary.model.DiaryItem
import com.strayalphaca.travel_diary.diary.model.DiaryItemUpdate
import com.strayalphaca.travel_diary.diary.model.DiaryModifyData
import com.strayalphaca.travel_diary.diary.model.DiaryWriteData
import com.strayalphaca.travel_diary.diary.repository.DiaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteDiaryRepository @Inject constructor(
    retrofit : Retrofit
) : DiaryRepository {
    private val diaryRetrofit = retrofit.create(DiaryApi::class.java)
    private val diaryItemUpdateChannel = MutableSharedFlow<DiaryItemUpdate>()

    override suspend fun getDiaryDetail(id: String): BaseResponse<DiaryDetail> {
        val response = diaryRetrofit.loadDetailDiary(id)
        return responseToBaseResponseWithMapping(
            response =  response,
            mappingFunction = ::diaryDtoToDiaryDetail
        )
    }

    override suspend fun getDiaryList(cityId: Int, perPage: Int, offset: Int): List<DiaryItem> {
        val response = diaryRetrofit.loadDiaryList(cityId = cityId, page = offset, offset = perPage)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.data.map { diaryListDtoToDiaryItem(it) }
        } else {
            throw Exception("error occur when load diaryList : $cityId, $perPage, $offset")
        }
    }

    override suspend fun getDiaryListByCityGroup(
        cityGroupId: Int,
        perPage: Int,
        offset: Int
    ): List<DiaryItem> {
        val response = diaryRetrofit.loadDiaryListByCityGroup(cityGroupId = cityGroupId, page = offset, offset = perPage)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.data.map { diaryListDtoToDiaryItem(it) }
        } else {
            throw Exception("error occur when load diaryList : $cityGroupId, $perPage, $offset")
        }
    }

    override suspend fun uploadDiary(diaryWriteData: DiaryWriteData): BaseResponse<String> {
        val response = diaryRetrofit.uploadDiary(params = UploadDiaryRequestBody.fromDiaryWriteData(diaryWriteData))
        return responseToBaseResponseWithMapping(response, ::extractIdFromSignupResponse)
    }

    override suspend fun modifyDiary(diaryModifyData: DiaryModifyData): BaseResponse<Nothing> {
        val response = diaryRetrofit.modifyDiary(
            recordId = diaryModifyData.id,
            params = ModifyDiaryRequestBody.fromDiaryModifyData(diaryModifyData)
        )
        return voidResponseToBaseResponse(response).also {
            if (it is BaseResponse.EmptySuccess) {
                diaryItemUpdateChannel.emit(DiaryItemUpdate.Modify(diaryModifyData.toDiaryItem()))
            }
        }
    }

    override suspend fun deleteDiary(diaryId: String): BaseResponse<Nothing> {
        val response = diaryRetrofit.deleteDiary(diaryId)
        return voidResponseToBaseResponse(response).also {
            if (it is BaseResponse.EmptySuccess) {
                diaryItemUpdateChannel.emit(DiaryItemUpdate.Delete(DiaryItem(id = diaryId, imageUrl = null, cityName = "-")))
            }
        }
    }

    override suspend fun getDiaryItemUpdate(): Flow<DiaryItemUpdate> {
        return diaryItemUpdateChannel
    }

    override suspend fun getDiaryCount(): BaseResponse<Int> {
        val response = diaryRetrofit.diaryTotalCount()
        return responseToBaseResponseWithMapping(response) { it.total }
    }

}