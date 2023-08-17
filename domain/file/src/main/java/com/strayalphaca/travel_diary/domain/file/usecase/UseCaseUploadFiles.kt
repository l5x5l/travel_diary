package com.strayalphaca.travel_diary.domain.file.usecase

import com.strayalphaca.domain.model.BaseResponse
import com.strayalphaca.travel_diary.domain.file.model.FileInfo
import com.strayalphaca.travel_diary.domain.file.repository.FileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UseCaseUploadFiles @Inject constructor(
    private val repository : FileRepository
) {
    suspend operator fun invoke(files: List<FileInfo>): BaseResponse<List<String>> =
        withContext(Dispatchers.IO) {
            TODO("UseCaseUploadFiles not implemented yet")
        }
}