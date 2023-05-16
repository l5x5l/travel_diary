package com.strayalphaca.presentation.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@SuppressLint("ComposableNaming")
@Composable
fun getFileFromLocal(context : Context, callback : (ByteArray) -> Unit) =
     rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            val file = getFileFromUri(uri, context)
            if (file != null) callback(file)
        }
    }

@SuppressLint("ComposableNaming")
@Composable
fun getUriFromLocal(context : Context, callback : (Uri) -> Unit) =
    rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
    if (uri != null) {
        callback(uri)
    }
}