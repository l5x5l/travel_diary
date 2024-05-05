package com.strayalphaca.presentation.screens.camera.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.camera.core.ImageProxy

// 이미지 포맷에 따라 다르게 구현해야 할 가능성 존재
fun imageProxyToBitmap(imageProxy: ImageProxy) : Bitmap {
    val rotateDegree = imageProxy.imageInfo.rotationDegrees

    val imageBuffer = imageProxy.planes[0].buffer
    val bytes = ByteArray(imageBuffer.remaining())
    imageBuffer.get(bytes)

    imageProxy.close()
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size).rotate(rotateDegree.toFloat())
}

internal fun Bitmap.rotate(degree : Float) : Bitmap = Bitmap.createBitmap(this, 0, 0, width, height, Matrix().apply { postRotate(degree) }, true)

internal fun Bitmap.reverseHorizontal() : Bitmap = Bitmap.createBitmap(this, 0, 0, width, height, Matrix().apply { setScale(-1f, 1f) }, false)