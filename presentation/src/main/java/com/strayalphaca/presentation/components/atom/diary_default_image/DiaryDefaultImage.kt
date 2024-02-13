package com.strayalphaca.presentation.components.atom.diary_default_image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.strayalphaca.presentation.R

@Composable
fun DiaryDefaultImage(
    modifier : Modifier = Modifier
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.ic_logo),
        contentDescription = "default_image",
        contentScale = ContentScale.Fit,
        colorFilter = ColorFilter.tint(Color.Black)
    )
}