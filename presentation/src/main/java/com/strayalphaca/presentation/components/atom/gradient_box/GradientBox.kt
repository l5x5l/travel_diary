package com.strayalphaca.presentation.components.atom.gradient_box

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.ui.theme.GradientGray
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

sealed class GradientDirection {
    object OnBottom : GradientDirection()
    object OnTop : GradientDirection()
    object OnLeft : GradientDirection()
    object OnRight : GradientDirection()
}

@Composable
fun GradientBox(
    modifier : Modifier = Modifier,
    gradientDirection: GradientDirection
) {
    val colorList = listOf(GradientGray, Color.Transparent)
    val reverseColorList = colorList.reversed()

    Box(modifier = modifier) {
        when (gradientDirection) {
            GradientDirection.OnBottom -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .background(Brush.verticalGradient(reverseColorList))
                        .align(Alignment.BottomStart)
                )
            }
            GradientDirection.OnTop -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .background(Brush.verticalGradient(colorList))
                        .align(Alignment.TopStart)
                )
            }
            GradientDirection.OnLeft -> {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(6.dp)
                        .background(Brush.horizontalGradient(colorList))
                        .align(Alignment.TopStart)
                )
            }
            GradientDirection.OnRight -> {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(6.dp)
                        .background(Brush.horizontalGradient(reverseColorList))
                        .align(Alignment.TopEnd)
                )
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
fun GradientBoxPreview() {
    TravelDiaryTheme() {
        Surface(Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize()) {
                GradientBox(
                    modifier = Modifier.fillMaxSize(0.5f).align(Alignment.TopStart),
                    gradientDirection = GradientDirection.OnBottom
                )

                GradientBox(
                    modifier = Modifier.fillMaxSize(0.5f).align(Alignment.TopEnd),
                    gradientDirection = GradientDirection.OnLeft
                )

                GradientBox(
                    modifier = Modifier.fillMaxSize(0.5f).align(Alignment.BottomEnd),
                    gradientDirection = GradientDirection.OnTop
                )

                GradientBox(
                    modifier = Modifier.fillMaxSize(0.5f).align(Alignment.BottomStart),
                    gradientDirection = GradientDirection.OnRight
                )
            }
        }

    }
}