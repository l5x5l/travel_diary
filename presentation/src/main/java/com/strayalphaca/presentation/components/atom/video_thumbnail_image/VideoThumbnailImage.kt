package com.strayalphaca.presentation.components.atom.video_thumbnail_image

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.decode.VideoFrameDecoder
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.ui.theme.BlackSurfaceTransparent50

@Composable
fun VideoThumbnailImage(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    videoUri: Uri
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components { add(VideoFrameDecoder.Factory()) }
        .build()

    val painter = rememberAsyncImagePainter(model = videoUri, imageLoader = imageLoader)
    val imageState = painter.state

    Box(modifier = modifier) {
        if (imageState is AsyncImagePainter.State.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.size(40.dp),
                    strokeWidth = 4.dp
                )
            }
        }

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        if (imageState is AsyncImagePainter.State.Success) {
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


}