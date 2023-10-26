package com.strayalphaca.presentation.utils

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.strayalphaca.presentation.R

@Composable
fun UseFinishByBackPressTwice() {
    val context = LocalContext.current
    var backPressedTime = 0L

    BackHandler(enabled = true) {
        if (System.currentTimeMillis() - backPressedTime <= 400L) {
            (context as Activity).finish()
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.finish_app_if_press_back_button_again),
                Toast.LENGTH_SHORT
            ).show()
            backPressedTime = System.currentTimeMillis()
        }
    }

}