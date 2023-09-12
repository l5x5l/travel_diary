package com.strayalphaca.presentation.components.atom.video_thumbnail_image

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.ui.theme.BlackSurfaceTransparent50
import com.strayalphaca.presentation.ui.theme.Gray4

@Composable
fun VideoThumbnailImage(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    thumbnailUri : Uri
) {

    Box(modifier = modifier) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            contentDescription = "video thumbnail image",
            model = thumbnailUri,
            contentScale = ContentScale.Crop,
            placeholder = ColorPainter(Gray4)
        )

        IconButton(
            modifier = Modifier
                .background(
                    color = BlackSurfaceTransparent50,
                    shape = CircleShape
                )
                .align(Alignment.Center),
            onClick = { onClick() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_play_arrow),
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
                    .clickable { onClick() },
                contentDescription = null,
                tint = Color.White
            )
        }

    }

}