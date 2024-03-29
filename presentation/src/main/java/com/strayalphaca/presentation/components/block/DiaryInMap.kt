package com.strayalphaca.presentation.components.block

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.strayalphaca.presentation.components.atom.diary_default_image.DiaryDefaultImage
import com.strayalphaca.presentation.ui.theme.Tape
import com.strayalphaca.presentation.utils.pxToDp

@Composable
fun DiaryInMap(modifier: Modifier, onClick : (Int) -> Unit, thumbnailUrl : String?, id : Int, widthPx : Int) {
    Surface(
        modifier = modifier
            .padding(2.dp)
            .shadow(4.dp)
            .clickable { onClick(id) },
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    (widthPx * 0.05f)
                        .toInt()
                        .pxToDp()
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Tape)
            ) {
                if (thumbnailUrl != null) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.8f),
                        model = thumbnailUrl,
                        contentDescription = "thumbnail_image",
                        contentScale = ContentScale.Crop
                    )
                } else {
                    DiaryDefaultImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f)
            )
        }
    }
}