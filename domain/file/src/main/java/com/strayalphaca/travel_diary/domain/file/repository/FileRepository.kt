package com.strayalphaca.travel_diary.domain.file.repository

import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.travel_diary.domain.file.model.FileInfo

interface FileRepository {
    suspend fun uploadFile(fileInfo: FileInfo) : BaseResponse<String>
}