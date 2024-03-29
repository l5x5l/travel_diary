package com.strayalphaca.presentation.components.template.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

sealed class TapeSize(val height : Dp, val width : Dp) {
    object Normal : TapeSize(32.dp, 48.dp)
    object Large : TapeSize(48.dp, 72.dp)
}

@Composable
fun TapeDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false),
    tapeSize : TapeSize = TapeSize.Normal,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Surface(
            shape = RectangleShape,
            color = Color.Transparent,
            modifier = Modifier.fillMaxWidth(0.9f)) {

            Box(modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)) {
                Image(
                    modifier = Modifier
                        .height(tapeSize.height)
                        .width(tapeSize.width)
                        .align(Alignment.TopCenter)
                        .zIndex(1f),
                    painter = painterResource(id = R.drawable.img_tape),
                    contentDescription = null
                )

                Surface(
                    color = Color.Transparent
                ) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .background(MaterialTheme.colors.surface)) {
                        content()
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CustomDialogPreview() {
    TravelDiaryTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Magenta
        ) {

            TapeDialog(
                tapeSize = TapeSize.Large,
                onDismissRequest = {  }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primaryVariant)
                        .padding(16.dp)

                ) {
                    Text(text = "hello")

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(text = "hello2", style = MaterialTheme.typography.h2)
                }
            }

        }
    }
}