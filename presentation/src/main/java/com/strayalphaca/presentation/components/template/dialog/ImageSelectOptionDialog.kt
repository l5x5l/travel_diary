package com.strayalphaca.presentation.components.template.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun ImageSelectOptionDialog(
    onDismissRequest : () -> Unit,
    onCameraClick : () -> Unit,
    onGalleryClick : () -> Unit
) {

    TapeDialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.select_image_option),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.caption_select_image_option),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .height(126.dp)
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            shape = RectangleShape,
                            color = MaterialTheme.colors.onSurface
                        )
                        .clickable {
                            onDismissRequest()
                            onCameraClick()
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        modifier = Modifier.size(48.dp),
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = "image_select_option_camera",
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(id = R.string.take_picture_from_camera),
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSurface,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier
                        .height(126.dp)
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            shape = RectangleShape,
                            color = MaterialTheme.colors.onSurface
                        )
                        .clickable {
                            onDismissRequest()
                            onGalleryClick()
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        modifier = Modifier.size(48.dp),
                        painter = painterResource(id = R.drawable.ic_image),
                        contentDescription = "image_select_option_camera",
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(id = R.string.get_from_gallery),
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    onDismissRequest()
                },
                contentPadding = PaddingValues(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageSelectOptionDialogPreview() {
    TravelDiaryTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Magenta
        ) {
            ImageSelectOptionDialog(onDismissRequest = {}, onCameraClick = {}) {

            }
        }
    }
}