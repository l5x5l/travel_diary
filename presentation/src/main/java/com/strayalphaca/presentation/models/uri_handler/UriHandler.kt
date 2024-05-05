package com.strayalphaca.presentation.models.uri_handler

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap

import com.strayalphaca.travel_diary.domain.file.model.FileInfo
import com.strayalphaca.travel_diary.domain.file.model.FileType
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar
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
        val fileType = getFileType(uri)
        return FileInfo(file = uriToCacheFile(uri), fileType = fileType)
    }

    private fun uriToCacheFile(uri : Uri) : File {
        if (uri.path?.startsWith("/storage") == true) {
            return File(uri.path!!)
        }

        val extension = if (uri.scheme?.equals(ContentResolver.SCHEME_CONTENT) == true) {
            MimeTypeMap.getSingleton().getExtensionFromMimeType(context.contentResolver.getType(uri))
        } else {
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path!!)).toString())
        }

        val fileName = uri.path?.let { path ->
            path.substring(path.lastIndexOf("/") + 1) + ".$extension"
        } ?: (Calendar.getInstance().timeInMillis.toString() + ".$extension")

        val file = File(context.cacheDir, fileName.replace(":", ""))
        file.createNewFile()

        val inputStream = context.contentResolver.openInputStream(uri)

        inputStream?.use { inputStreamLamb ->
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var length : Int
            while (inputStreamLamb.read(buffer).also{ length = it } > 0 ) {
                outputStream.write(buffer, 0, length)
            }

            outputStream.flush()
            outputStream.close()
        }

        return file
    }

    fun getFileType(uri: Uri): FileType {
        val mimeType = context.contentResolver.getType(uri) ?: return getFileTypeByFileExtension(uri)
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

    private fun getFileTypeByFileExtension(uri : Uri) : FileType {
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        return when(fileExtension) {
            "jpg", "jpeg", "png" -> { FileType.Image }
            "mp4", "webm" -> { FileType.Video }
            "mp3", "wav" -> { FileType.Voice }
            else -> { FileType.Unknown }
        }
    }

}