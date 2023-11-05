package com.strayalphaca.travel_diary.data.file.repository

import com.strayalphaca.data.all.utils.responseToBaseResponseWithMapping
import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.data.file.api.FileApi
import com.strayalphaca.travel_diary.domain.file.model.FileInfo
import com.strayalphaca.travel_diary.domain.file.model.FileType
import com.strayalphaca.travel_diary.domain.file.repository.FileRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import javax.inject.Inject

class RemoteFileRepository @Inject constructor(
    retrofit : Retrofit
) : FileRepository {
    private val fileRetrofit = retrofit.create(FileApi::class.java)

    override suspend fun uploadFile(fileInfo: FileInfo): BaseResponse<String> {
        val partMap = mutableMapOf<String, RequestBody>()
        when (fileInfo.fileType) {
            FileType.Image -> {
                val file = fileInfo.file.asRequestBody("image/*".toMediaType())
                partMap["image"] = file
            }
            FileType.Video -> {
                val file = fileInfo.file.asRequestBody("video/*".toMediaType())
                partMap["video"] = file
            }
            FileType.Voice -> {
                val file = fileInfo.file.asRequestBody("audio/*".toMediaType())
                partMap["voice"] = file
            }
            else -> {
                throw IllegalArgumentException("cannot find file's type : $fileInfo")
            }
        }
        val response = fileRetrofit.uploadFile(partMap)
        return responseToBaseResponseWithMapping(response) { it.id }
    }
}