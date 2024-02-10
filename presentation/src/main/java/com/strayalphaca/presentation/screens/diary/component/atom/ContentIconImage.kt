package com.strayalphaca.presentation.screens.diary.component.atom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.ui.theme.Gray4

@Composable
fun ContentIconImage(
    iconId : Int,
    descriptionText : String ?= null,
    onClick : (() -> Unit)? = null,
    interactionSource : MutableInteractionSource = remember {MutableInteractionSource()}
) {
    val isPressed by interactionSource.collectIsPressedAsState()

    Icon(
        painter = painterResource(id = iconId),
        contentDescription = descriptionText,
        modifier = Modifier
            .size(36.dp)
            .thenIf(
                condition = onClick != null,
                other = Modifier.clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) { onClick?.invoke() }
            ),
        tint = if (isPressed) Gray4 else MaterialTheme.colors.onSurface
    )
}

private fun Modifier.thenIf(condition : Boolean, other : Modifier) : Modifier =
    if (condition) this.then(other) else this