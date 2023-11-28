package com.strayalphaca.presentation.screens.diary.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import com.strayalphaca.travel_diary.domain.file.model.FileResizeHandler
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import kotlin.math.sqrt

class FileResizeHandlerImpl @Inject constructor(
    val context : Context
) : FileResizeHandler {
    override fun resizeImageFile(file: File, maxByteSize: Int): File {
        if (!file.exists() || !file.isFile) {
            throw IllegalArgumentException("Input file does not exist or is not a regular file.")
        }

        try {
            val originalSize = file.length()
            if (originalSize <= maxByteSize) {
                return file
            }

            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(file.absolutePath, options)

            options.inSampleSize = calculateInSampleSize(options, maxByteSize)

            options.inJustDecodeBounds = false
            val originalBitmap = BitmapFactory.decodeFile(file.absolutePath, options)

            val scale = sqrt(maxByteSize.toDouble() / originalSize)
            val newWidth = (originalBitmap.width * scale).toInt()
            val newHeight = (originalBitmap.height * scale).toInt()

            val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true)

            val outputDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val resizedFile = File(outputDir, "resized_${file.name}")

            val outputStream = FileOutputStream(resizedFile)
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()

            return resizedFile
        } catch (e: Exception) {
            throw RuntimeException("Error while resizing image: ${e.message}", e)
        }
    }

    private fun calculateInSampleSize(options : BitmapFactory.Options, maxByteSize : Int) : Int {
        val originalHeight = options.outHeight
        val originalWidth = options.outWidth
        var inSampleSize = 1

        val totalPixels = originalHeight * originalWidth
        val totalPixelsCap = maxByteSize / 2

        while (totalPixels / (inSampleSize * inSampleSize) > totalPixelsCap) {
            inSampleSize *= 2
        }

        return inSampleSize
    }
}