package com.strayalphaca.travel_diary.data.file.repository

import com.strayalphaca.data.all.utils.responseToBaseResponseWithMapping
import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.data.file.api.FileApi
import com.strayalphaca.travel_diary.domain.file.model.FileInfo
import com.strayalphaca.travel_diary.domain.file.model.FileType
import com.strayalphaca.travel_diary.domain.file.repository.FileRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteFileRepository @Inject constructor(
    retrofit : Retrofit
) : FileRepository {
    private val fileRetrofit = retrofit.create(FileApi::class.java)

    override suspend fun uploadFile(fileInfo: FileInfo): BaseResponse<String> {
        return when (fileInfo.fileType) {
            FileType.Image -> {
                val file = fileInfo.file.asRequestBody("image/*".toMediaType())
                val part = MultipartBody.Part.createFormData("image", fileInfo.file.name, file)
                val response = fileRetrofit.uploadImage(listOf(part))
                responseToBaseResponseWithMapping(response) { it.data[0].id }
            }
            FileType.Video -> {
                val file = fileInfo.file.asRequestBody("video/*".toMediaType())
                val part = MultipartBody.Part.createFormData("image", fileInfo.file.name, file)
                val response = fileRetrofit.uploadVideo(listOf(part))
                responseToBaseResponseWithMapping(response) { it.data[0].id }
            }
            FileType.Voice -> {
                val file = fileInfo.file.asRequestBody("audio/*".toMediaType())
                val part = MultipartBody.Part.createFormData("image", fileInfo.file.name, file)
                val response = fileRetrofit.uploadVoice(listOf(part))
                responseToBaseResponseWithMapping(response) { it.data[0].id }
            }
            else -> {
                throw IllegalArgumentException("cannot find file's type : $fileInfo")
            }
        }
    }
}