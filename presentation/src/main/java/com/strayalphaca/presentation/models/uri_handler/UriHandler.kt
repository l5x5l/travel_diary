package com.strayalphaca.presentation.models.uri_handler

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
    fun fileSizeFromUri(uri : Uri) : Long {
        val byteSize = context.contentResolver.openAssetFileDescriptor(uri, "r").use {
            it?.length
        }
        return byteSize ?: throw IllegalArgumentException("file path from uri does not exist : $uri")
    }

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

            path = if (columnIndex > -1)
                cursor.getString(columnIndex)
            else
                uri.toString()
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