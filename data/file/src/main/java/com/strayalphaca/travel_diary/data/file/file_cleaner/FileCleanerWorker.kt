package com.strayalphaca.travel_diary.data.file.file_cleaner

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.strayalphaca.travel_diary.core.data.room.dao.RecordDao
import com.strayalphaca.travel_diary.data.file.file_manager.FileManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class FileCleanerWorker @AssistedInject constructor(
    @Assisted appContext : Context,
    @Assisted workerParams : WorkerParameters,
    private val fileManager: FileManager,
    private val recordDao: RecordDao
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        recordDao
            .getDeleteTargetFilePaths()
            .also {  paths ->
                recordDao.deleteFile(paths)
            }
            .map { path ->
                fileManager.deleteFile(path)
            }
        return Result.success()
    }

}