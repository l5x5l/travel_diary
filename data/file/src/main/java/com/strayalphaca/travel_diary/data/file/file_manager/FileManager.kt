package com.strayalphaca.travel_diary.data.file.file_manager

import java.io.File

interface FileManager {
    suspend fun saveFileAndGetPath(file : File) : String?
    suspend fun deleteFile(filePath : String) : Boolean
}