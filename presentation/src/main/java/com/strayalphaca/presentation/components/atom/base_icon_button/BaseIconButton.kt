package com.strayalphaca.presentation.components.atom.base_icon_button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.ui.theme.Gray4

@Composable
fun BaseIconButton(
    modifier : Modifier = Modifier,
    iconResourceId : Int,
    onClick : () -> Unit = {},
    contentDescription : String ?= null,
    enabled : Boolean = true
) {
    val iconTint = if (enabled) MaterialTheme.colors.onSurface else Gray4

    Box(
        modifier = modifier,
    ) {
        IconButton(
            onClick = onClick,
            enabled = enabled
        ) {
            Icon(
                painter = painterResource(id = iconResourceId),
                contentDescription = contentDescription,
                modifier = Modifier
                    .height(48.dp)
                    .width(48.dp)
                    .padding(12.dp)
                ,
                tint = iconTint
            )
        }
    }
}