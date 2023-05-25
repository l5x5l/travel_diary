package com.strayalphaca.presentation.utils

import android.content.Context
import android.net.Uri
import java.io.IOException
import java.io.InputStream

fun getFileFromUri(uri : Uri, context : Context) : ByteArray? {
    var inputStream : InputStream ?= null
    try {
        inputStream = context.contentResolver.openInputStream(uri)
        return inputStream?.readBytes()
    } catch (e : IOException) {
        e.printStackTrace()
    } finally {
        inputStream?.close()
    }
    return null
}

fun checkUriIsVideo(uri : Uri, context : Context) : Boolean {
    val contentResolver = context.contentResolver
    val result = contentResolver.getType(uri)
    return result?.startsWith("video") ?: false
}