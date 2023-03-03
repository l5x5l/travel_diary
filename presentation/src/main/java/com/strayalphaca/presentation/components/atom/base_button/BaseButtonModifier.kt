package com.strayalphaca.presentation.components.atom.base_button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.ui.theme.Gray4

internal fun Modifier.getButtonModifier(baseButtonState: BaseButtonState) = composed { this.then(
    when(baseButtonState) {
        BaseButtonState.ACTIVE -> Modifier.border(width = 1.dp, shape = RectangleShape, color = MaterialTheme.colors.onBackground)
        BaseButtonState.INACTIVE -> Modifier.border(width = 1.dp, shape = RectangleShape, color = Gray4)
        BaseButtonState.SELECTED -> Modifier.background(MaterialTheme.colors.onBackground)
    }
)}

@Composable
@ReadOnlyComposable
fun getButtonTextColor(baseButtonState : BaseButtonState) : Color {
    return when(baseButtonState) {
        BaseButtonState.ACTIVE -> MaterialTheme.colors.onBackground
        BaseButtonState.INACTIVE -> Gray4
        BaseButtonState.SELECTED -> MaterialTheme.colors.background
    }
}