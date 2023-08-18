package com.strayalphaca.travel_diary.data.file.repository

import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.domain.file.model.FileInfo
import com.strayalphaca.travel_diary.domain.file.repository.FileRepository
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(

) : FileRepository {
    override suspend fun uploadFile(fileInfo: FileInfo): BaseResponse<String> {
        // TODO not yet implemented
        return BaseResponse.Success<String>(data = "string")
    }

}