package com.strayalphaca.travel_diary.data.file.repository

import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalpaca.travel_diary.core.domain.model.DiaryDate
import com.strayalphaca.travel_diary.core.data.room.dao.RecordDao
import com.strayalphaca.travel_diary.core.data.room.entity.FileEntity
import com.strayalphaca.travel_diary.data.file.file_manager.FileManager
import com.strayalphaca.travel_diary.domain.file.model.FileInfo
import com.strayalphaca.travel_diary.domain.file.repository.FileRepository
import java.util.Calendar

class LocalFileRepository(
    private val recordDao: RecordDao,
    private val fileManager: FileManager
) : FileRepository {
    override suspend fun uploadFile(fileInfo: FileInfo): BaseResponse<String> {
        val path = fileManager.saveFileAndGetPath(fileInfo.file)
            ?: return BaseResponse.Failure(errorCode = -1, errorMessage = "error invoked when save file to external storage")

        val response = recordDao.addFile(FileEntity(
            type = fileInfo.fileType.toString(),
            filePath = path,
            createdAt = DiaryDate.getInstanceFromCalendar(Calendar.getInstance()).toString()
        ))
        return BaseResponse.Success(data = response.toString())
    }
}