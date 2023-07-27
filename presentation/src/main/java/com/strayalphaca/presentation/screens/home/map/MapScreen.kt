package com.strayalphaca.presentation.screens.home.map

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.gradient_box.GradientBox
import com.strayalphaca.presentation.components.atom.gradient_box.GradientDirection
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    onDiaryClick: (String) -> Unit = {},
    viewModel : MapViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .padding(horizontal = 16.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .align(Alignment.Center),
            elevation = 4.dp
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                GradientBox(
                    modifier = Modifier
                        .fillMaxSize(0.5f)
                        .align(Alignment.TopStart),
                    gradientDirection = GradientDirection.OnBottom
                )

                GradientBox(
                    modifier = Modifier
                        .fillMaxSize(0.5f)
                        .align(Alignment.TopEnd),
                    gradientDirection = GradientDirection.OnLeft
                )

                GradientBox(
                    modifier = Modifier
                        .fillMaxSize(0.5f)
                        .align(Alignment.BottomEnd),
                    gradientDirection = GradientDirection.OnTop
                )

                GradientBox(
                    modifier = Modifier
                        .fillMaxSize(0.5f)
                        .align(Alignment.BottomStart),
                    gradientDirection = GradientDirection.OnRight
                )
            }

            Box(
                Modifier
                    .padding(16.dp)
                    .border(width = 1.dp, color = MaterialTheme.colors.onSurface)
                    .padding(16.dp)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.ic_map_korea),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }


        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "dark"
)
@Preview(showBackground = true, widthDp = 360)
@Composable
fun MapScreenPreview() {
    TravelDiaryTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            // MapScreen()
        }
    }
}