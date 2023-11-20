package com.strayalphaca.presentation.utils

import android.content.Context
import android.net.Uri
import android.provider.MediaStore

import com.strayalphaca.travel_diary.domain.file.model.FileInfo
import com.strayalphaca.travel_diary.domain.file.model.FileType
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UriHandler @Inject constructor(
    @ApplicationContext private val context : Context
) {
    fun uriToFile(uri : Uri) : FileInfo {
        val path = uriToFilePath(uri)
        val fileType = getFileType(uri)
        return FileInfo(file = File(path), fileType = fileType)
    }

    private fun uriToFilePath(uri : Uri) : String {
        if (uri.path?.startsWith("/storage") == true) {
            return uri.path!!
        }

        var path : String ?= null
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            cursor.moveToNext()
            val columnIndex = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA)
            path = cursor.getString(columnIndex)
        }

        return path ?: throw IllegalArgumentException("file path from uri does not exist : $uri")
    }

    fun getFileType(uri: Uri): FileType {
        val mimeType = context.contentResolver.getType(uri) ?: return FileType.Unknown
        return when {
            mimeType.contains("audio") -> {
                FileType.Voice
            }

            mimeType.contains("image") -> {
                FileType.Image
            }

            mimeType.contains("video") -> {
                FileType.Video
            }

            else -> {
                FileType.Unknown
            }
        }
    }

}