package com.strayalphaca.travel_diary.domain.file.usecase

import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.domain.file.model.FileInfo
import com.strayalphaca.travel_diary.domain.file.repository.FileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UseCaseUploadFiles @Inject constructor(
    private val repository : FileRepository
) {
    suspend operator fun invoke(files: List<FileInfo>): BaseResponse<List<String>> =
        withContext(Dispatchers.IO) {
            val deferred = files.map {
                async { repository.uploadFile(it) }
            }
            val response = deferred.awaitAll()

            response.find { it is BaseResponse.Failure }?.let {
                return@withContext it as BaseResponse.Failure
            }

            val stringList = response.filterIsInstance<BaseResponse.Success<String>>().map { it.data }
            return@withContext BaseResponse.Success(data = stringList)
        }
}