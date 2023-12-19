package com.strayalphaca.travel_diary.data.file.repository

import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.domain.file.model.FileInfo
import com.strayalphaca.travel_diary.domain.file.repository.FileRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileRepositoryImpl @Inject constructor() : FileRepository {
    override suspend fun uploadFile(fileInfo: FileInfo): BaseResponse<String> {
        return BaseResponse.Success(data = "string")
    }

}