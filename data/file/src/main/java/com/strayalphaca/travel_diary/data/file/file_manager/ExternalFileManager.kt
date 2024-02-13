package com.strayalphaca.travel_diary.data.file.file_manager

import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

class ExternalFileManager constructor(
    private val context : Context
) : FileManager {
    override suspend fun saveFileAndGetPath(file: File): String? = withContext(Dispatchers.IO) {
        if (!isExternalStorageWritable()) return@withContext null

        val fileName = file.name
        val externalStorageDir = context.getExternalFilesDir(null)
        val fileDirPath = externalStorageDir?.absolutePath + File.separator + "medias"
        val filePath = fileDirPath + File.separator + fileName

        File(fileDirPath).run {
            if (!exists()) {
                if (!mkdir()) return@withContext null
            }
        }

        val inputStream = try {
            context.contentResolver.openInputStream(fixContentFilePath(file.path).toUri()) ?: return@withContext null
        } catch (e : FileNotFoundException) {
            FileInputStream(file)
        }
        val outputStream = FileOutputStream(filePath)
        val buffer = ByteArray(1024)
        var length : Int
        while (inputStream.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }

        outputStream.flush()
        outputStream.close()
        inputStream.close()
        return@withContext filePath
    }

    override suspend fun deleteFile(filePath: String): Boolean {
        val file = File(filePath)
        return if (file.exists()) {
            file.delete()
        } else {
            false
        }
    }

    private fun isExternalStorageWritable() : Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    private fun fixContentFilePath(path : String) : String {
        return if (path.contains("content:/") && !path.contains("content://")){
            path.replace("content:/", "content://")
        } else {
            path
        }
    }
}