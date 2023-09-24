package com.strayalphaca.presentation.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    surface = BlackSurface,
    onSurface = Color.White,
    background = BlackBackground,
    onBackground = Color.White,
    primary = Color.Black,
    onPrimary = Color.White,
    secondary = Tape,
    onError = errorRed
)


// primary 색상이 background 와 동일한 경우, text field 의 위치를 표시하는 ui 색상도 동일해져 안보기에 된다.
private val LightColorPalette = lightColors(
    surface = Color.White,
    onSurface = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    primary = Color.White,
    onPrimary = Color.Black,
    primaryVariant = Gray2,
    secondary = Tape,
    secondaryVariant = TapeVariant,
    onError = errorRed

)

@Composable
fun TravelDiaryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}