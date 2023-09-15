package com.strayalphaca.travel_diary.data.diary.repository_impl

import com.strayalphaca.data.all.utils.responseToBaseResponse
import com.strayalphaca.data.all.utils.responseToBaseResponseWithMapping
import com.strayalphaca.data.all.utils.voidResponseToBaseResponse
import com.strayalphaca.travel_diary.data.diary.api.DiaryApi
import com.strayalphaca.travel_diary.data.diary.model.ModifyDiaryRequestBody
import com.strayalphaca.travel_diary.data.diary.model.UploadDiaryRequestBody
import com.strayalphaca.travel_diary.data.diary.utils.diaryDtoToDiaryDetail
import com.strayalphaca.travel_diary.data.diary.utils.diaryListDtoToDiaryItem
import com.strayalphaca.travel_diary.diary.model.DiaryDetail
import com.strayalphaca.travel_diary.diary.model.DiaryItem
import com.strayalphaca.travel_diary.diary.model.DiaryModifyData
import com.strayalphaca.travel_diary.diary.model.DiaryWriteData
import com.strayalphaca.travel_diary.diary.repository.DiaryRepository
import com.strayalphaca.domain.model.BaseResponse
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteDiaryRepository @Inject constructor(
    retrofit : Retrofit
) : DiaryRepository {
    private val diaryRetrofit = retrofit.create(DiaryApi::class.java)

    override suspend fun getDiaryDetail(id: String): BaseResponse<DiaryDetail> {
        val response = diaryRetrofit.loadDetailDiary(id)
        return responseToBaseResponseWithMapping(
            response =  response,
            mappingFunction = ::diaryDtoToDiaryDetail
        )
    }

    override suspend fun getDiaryList(cityId: Int, perPage: Int, offset: Int): List<DiaryItem> {
        val response = diaryRetrofit.loadDiaryList(cityId = cityId, page = perPage, offset = offset)
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
        val response = diaryRetrofit.loadDiaryListByCityGroup(cityGroupId = cityGroupId, page = perPage, offset = offset)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.data.map { diaryListDtoToDiaryItem(it) }
        } else {
            throw Exception("error occur when load diaryList : $cityGroupId, $perPage, $offset")
        }
    }

    override suspend fun uploadDiary(diaryWriteData: DiaryWriteData): BaseResponse<String> {
        val response = diaryRetrofit.uploadDiary(params = UploadDiaryRequestBody.fromDiaryWriteData(diaryWriteData))
        return responseToBaseResponse(response)
    }

    override suspend fun modifyDiary(diaryModifyData: DiaryModifyData): BaseResponse<Nothing> {
        val response = diaryRetrofit.modifyDiary(
            recordId = diaryModifyData.id,
            params = ModifyDiaryRequestBody.fromDiaryModifyData(diaryModifyData)
        )
        return voidResponseToBaseResponse(response)
    }

    override suspend fun deleteDiary(diaryId: String): BaseResponse<Nothing> {
        val response = diaryRetrofit.deleteDiary(diaryId)
        return voidResponseToBaseResponse(response)
    }

}