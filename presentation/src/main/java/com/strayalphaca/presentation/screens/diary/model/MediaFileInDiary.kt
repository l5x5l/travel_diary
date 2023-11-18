package com.strayalphaca.presentation.screens.diary.model

import android.net.Uri
import androidx.core.net.toUri
import com.strayalphaca.presentation.screens.diary.util.fileTypeTransfer
import com.strayalphaca.travel_diary.diary.model.File
import com.strayalphaca.travel_diary.domain.file.model.FileType

sealed class MediaFileInDiary(
    val fileType: FileType,
    val uri: Uri,
) {
    class UploadedFile(
        val id: String,
        fileUri: Uri,
        fileType: FileType,
        val thumbnailUri: Uri? = null
    ) : MediaFileInDiary(fileType, fileUri) {
        companion object {
            fun createFromFile(file: File): UploadedFile {
                return UploadedFile(
                    id = file.id,
                    fileUri = file.fileLink.toUri(),
                    fileType = fileTypeTransfer(file.type),
                    thumbnailUri = file.thumbnailLink?.toUri()
                )
            }
        }
    }

    class LocalFile(
        localUri: Uri,
        fileType: FileType
    ) : MediaFileInDiary(fileType, localUri)

    fun checkUri(targetUri: Uri): Boolean {
        return when (this) {
            is UploadedFile -> {
                targetUri == uri || targetUri == thumbnailUri
            }

            is LocalFile -> {
                targetUri == uri
            }
        }
    }

    fun getThumbnailUriOrFileUri(): Uri {
        return when (this) {
            is UploadedFile -> {
                thumbnailUri ?: uri
            }

            is LocalFile -> {
                uri
            }
        }
    }
}