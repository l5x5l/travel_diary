package com.strayalphaca.presentation.utils

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

fun calculateInSampleSize(options : BitmapFactory.Options, reqWidth : Int, reqHeight : Int) : Int {
    val (height : Int, width : Int) = options.run { outHeight to outWidth }
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {
        val halfHeight = height / 2
        val halfWidth = width / 2

        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }

    return inSampleSize
}

fun decodeSampledBitmapFromResource(byteArray: ByteArray, reqWidth: Int, reqHeight : Int ) : ImageBitmap {
    return BitmapFactory.Options().run {
        inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)
        inJustDecodeBounds = false
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }.asImageBitmap()
}