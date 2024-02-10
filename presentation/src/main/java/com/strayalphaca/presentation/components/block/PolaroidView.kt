package com.strayalphaca.presentation.components.block

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.video_thumbnail_image.VideoThumbnailImage
import com.strayalphaca.presentation.ui.theme.Gray4
import com.strayalphaca.presentation.ui.theme.Tape
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun PolaroidView(
    modifier : Modifier = Modifier,
    fileUri: Uri = Uri.EMPTY,
    thumbnailUri : Uri = Uri.EMPTY,
    isVideo: Boolean = false,
    dateString: String = "2023.02.10_18:34",
    positionString: String = "1/1",
    onClick: (Uri) -> Unit = {},
    onDeleteClick : ((Uri) -> Unit)? = null
) {
    val imageViewSize = remember { mutableStateOf(IntSize(0, 0)) }

    Box(modifier = modifier.fillMaxWidth()) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .shadow(elevation = 6.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {

                onDeleteClick?.let { onDeleteClick ->
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 20.dp)
                            .background(Tape)
                            .zIndex(1f)
                    ) {
                        Spacer(modifier = Modifier.height(12.dp))
                        IconButton(onClick = { onDeleteClick(fileUri) }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = "delete media file",
                                modifier = Modifier
                                    .height(48.dp)
                                    .width(40.dp)
                                    .padding(vertical = 12.dp, horizontal = 8.dp)
                                ,
                                tint = Color.Black
                            )
                        }
                    }
                }

                Column(modifier = Modifier.padding(12.dp)) {
                    if (isVideo) {
                        VideoThumbnailImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp)
                                .onGloballyPositioned { coordinates ->
                                    imageViewSize.value = coordinates.size
                                },
                            onClick = { onClick(fileUri) },
                            thumbnailUri = thumbnailUri
                        )
                    } else {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp)
                                .onGloballyPositioned { coordinates ->
                                    imageViewSize.value = coordinates.size
                                },
                            model = fileUri,
                            contentDescription = "diary image",
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = dateString, style = MaterialTheme.typography.caption, color = Gray4)
                        Text(
                            text = positionString,
                            style = MaterialTheme.typography.caption,
                            color = Gray4
                        )
                    }
                }
            }

        }
    }
}


@Composable
@Preview(
    showBackground = true,
    widthDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "dark"
)
@Preview(showBackground = true, widthDp = 360)
fun PolaroidViewPreview() {
    TravelDiaryTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            PolaroidView(onDeleteClick = {})
        }
    }
}
