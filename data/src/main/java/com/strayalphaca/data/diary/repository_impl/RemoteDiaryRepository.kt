package com.strayalphaca.data.diary.repository_impl

import com.strayalphaca.data.all.utils.responseToBaseResponseWithMapping
import com.strayalphaca.data.diary.api.DiaryApi
import com.strayalphaca.data.diary.utils.diaryDtoToDiaryDetail
import com.strayalphaca.data.diary.utils.diaryListDtoToDiaryItem
import com.strayalphaca.domain.diary.model.DiaryDetail
import com.strayalphaca.domain.diary.model.DiaryItem
import com.strayalphaca.domain.diary.repository.DiaryRepository
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
            return response.body()!!.map { diaryListDtoToDiaryItem(it) }
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
            return response.body()!!.map { diaryListDtoToDiaryItem(it) }
        } else {
            throw Exception("error occur when load diaryList : $cityGroupId, $perPage, $offset")
        }
    }

}