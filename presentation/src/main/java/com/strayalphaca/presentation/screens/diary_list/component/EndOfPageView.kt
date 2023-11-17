package com.strayalphaca.presentation.screens.diary_list.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.strayalphaca.presentation.ui.theme.Gray2
import com.strayalphaca.presentation.ui.theme.Gray4

@Composable
fun EndOfPageView(
    modifier : Modifier = Modifier,
) {
    val color = if (isSystemInDarkTheme()) Gray4 else Gray2
    Canvas(
        modifier = modifier,
        onDraw = {
            drawCircle(color)
        }
    )
}