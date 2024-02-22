package com.strayalphaca.presentation.utils

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

val EXACT_ALARM_PERMISSION = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    Manifest.permission.SCHEDULE_EXACT_ALARM
} else {
    null
}


fun checkExactAlarmAvailable(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.canScheduleExactAlarms()
    } else {
        true
    }
}


val POST_NOTIFICATIONS_33 =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.POST_NOTIFICATIONS
    } else {
        null
    }

val WRITE_EXTERNAL_STORAGE_28 = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
    Manifest.permission.WRITE_EXTERNAL_STORAGE
} else {
    null
}

@Composable
fun rememberSinglePermissionRequestLauncher(
    onPermissionGranted : () -> Unit,
    onPermissionDenied : () -> Unit
) : ManagedActivityResultLauncher<String, Boolean> {
    val requestWriteExternalStoragePermissionLauncherForVoice = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }
    )
    return requestWriteExternalStoragePermissionLauncherForVoice
}