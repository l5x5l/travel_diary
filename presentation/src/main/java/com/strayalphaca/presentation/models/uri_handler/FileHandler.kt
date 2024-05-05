package com.strayalphaca.presentation.models.uri_handler

import android.content.Context
import android.graphics.Bitmap
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileHandler @Inject constructor(
    @ApplicationContext private val context : Context
){
    fun bitmapToFile(bitmap: Bitmap, fileName : String) : File? {
        val externalStorageDir = context.getExternalFilesDir(null)
        val fileDirPath = externalStorageDir?.absolutePath + File.separator + "medias"

        File(fileDirPath).run {
            if (!exists()) {
                if (!mkdir()) return null
            }
        }

        val file = File(fileDirPath, fileName)
        FileOutputStream(file).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }

        return file
    }
}