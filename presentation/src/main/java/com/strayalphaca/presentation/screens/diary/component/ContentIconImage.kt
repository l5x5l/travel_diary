package com.strayalphaca.presentation.screens.diary.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ContentIconImage(
    iconId : Int,
    descriptionText : String ?= null,
    onClick : () -> Unit = {},

) {
    val interactionSource = remember {MutableInteractionSource()}
    val iconTint = MaterialTheme.colors.onSurface

    Icon(
        painter = painterResource(id = iconId),
        contentDescription = descriptionText,
        modifier = Modifier.size(36.dp).clickable(
            indication = null,
            interactionSource = interactionSource
        ) { onClick() },
        tint = iconTint
    )
}