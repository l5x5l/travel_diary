package com.strayalphaca.presentation.screens.diary.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Environment
import androidx.exifinterface.media.ExifInterface
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

            val exif = ExifInterface(file.path)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> {
                    matrix.postRotate(90f)
                }
                ExifInterface.ORIENTATION_ROTATE_180 -> {
                    matrix.postRotate(180f)
                }
                ExifInterface.ORIENTATION_ROTATE_270 -> {
                    matrix.postRotate(270f)
                }
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

            val outputBitmap = run {
                val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true)
                Bitmap.createBitmap(resizedBitmap, 0, 0, resizedBitmap.width, resizedBitmap.height, matrix, true).also {
                    resizedBitmap.recycle()
                }
            }

            val outputDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val resizedFile = File(outputDir, "resized_${file.name}")

            val outputStream = FileOutputStream(resizedFile)
            outputBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()

            return resizedFile
        } catch (e: Exception) {
            throw RuntimeException("Error while resizing image: ${e.message}", e)
        }
    }

    // 이미지를 로드할 때 [maxByte, maxByte*4) 로 이미지가 로드되도록 inSampleSize를 리턴
    private fun calculateInSampleSize(options : BitmapFactory.Options, maxByteSize : Int) : Int {
        val originalHeight = options.outHeight
        val originalWidth = options.outWidth
        var inSampleSize = 1

        val totalPixels = originalHeight * originalWidth
        val totalPixelsCap = maxByteSize * 4

        while (totalPixels / (inSampleSize * inSampleSize) > totalPixelsCap) {
            inSampleSize *= 2
        }

        return inSampleSize
    }
}