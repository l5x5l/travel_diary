package com.strayalphaca.presentation.components.block

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.template.dialog.TapeSize
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun MapEmptyView(
    modifier: Modifier,
    tapeSize: TapeSize = TapeSize.Normal
) {
    Box(modifier = modifier
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
            color = MaterialTheme.colors.surface,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(MaterialTheme.colors.surface),
                    contentDescription = "diary image",
                    contentScale = ContentScale.Fit,
                    painter = painterResource(id = R.drawable.ic_logo),
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = stringResource(id = R.string.map_empty), style = MaterialTheme.typography.caption, color = MaterialTheme.colors.onSurface)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MapEmptyViewPreview() {
    TravelDiaryTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Magenta
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                MapEmptyView(
                    modifier = Modifier.fillMaxWidth(0.5f)
                )
            }
        }
    }
}