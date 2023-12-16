package com.strayalphaca.presentation.screens.diary.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import android.media.MediaMuxer
import android.os.Build
import android.os.Environment
import androidx.exifinterface.media.ExifInterface
import com.strayalphaca.travel_diary.domain.file.model.FileResizeHandler
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer
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

    override fun cutAudioFile(file: File, startSecond : Int, endSecond: Int): File {
        try {
            val outputFileDir = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                context.getExternalFilesDir(Environment.DIRECTORY_RECORDINGS)
            } else {
                context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
            }
            val outputFile = File(outputFileDir, "output_cut${file.extension}")
            val extractor = MediaExtractor()
            extractor.setDataSource(file.path)

            var audioTrackIndex = -1
            for (i in 0 until extractor.trackCount) {
                val format = extractor.getTrackFormat(i)
                val mime = format.getString(MediaFormat.KEY_MIME)
                if (mime?.startsWith("audio/") == true) {
                    audioTrackIndex = i
                    break
                }
            }

            if (audioTrackIndex == -1) {
                throw IOException("No audio track found in the input file")
            }

            val muxer = MediaMuxer(outputFile.path, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4)

            val audioFormat = extractor.getTrackFormat(audioTrackIndex)
            val muxerAudioTrackIndex = muxer.addTrack(audioFormat)

            muxer.start()

            val startMicros = startSecond * 1000000L
            extractor.seekTo(startMicros, MediaExtractor.SEEK_TO_CLOSEST_SYNC)

            val bufferSize = 1024 * 256
            val buffer = ByteBuffer.allocate(bufferSize)

            val targetDurationMicros = (endSecond - startSecond) * 1000000L

            var totalBytesWritten = 0
            var presentationTimeMicros = 0L

            while (true) {
                val sampleSize = extractor.readSampleData(buffer, 0)
                if (sampleSize < 0 || presentationTimeMicros > targetDurationMicros) {
                    break
                }

                val byteArray = ByteArray(sampleSize)
                buffer.get(byteArray)

                val bufferInfo = MediaCodec.BufferInfo()
                bufferInfo.size = sampleSize
                bufferInfo.presentationTimeUs = presentationTimeMicros
                muxer.writeSampleData(muxerAudioTrackIndex, ByteBuffer.wrap(byteArray), bufferInfo)

                // Move to the next sample
                extractor.advance()

                // Update presentation time
                presentationTimeMicros = extractor.sampleTime
                totalBytesWritten += sampleSize
            }

            extractor.release()
            muxer.stop()
            muxer.release()

            println("Audio cut successfully. Total bytes written: $totalBytesWritten")

            return outputFile

        } catch (e : Exception) {
            throw RuntimeException("Error while cutting audio: ${e.message}", e)
        }
    }
}