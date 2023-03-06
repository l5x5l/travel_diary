package com.strayalphaca.presentation.components.atom.base_button

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

@Composable
fun BaseIconButton(
    modifier : Modifier = Modifier,
    iconResourceId : Int,
    onClick : () -> Unit = {},
    contentDescription : String ?= null
) {
    val iconTint = MaterialTheme.colors.onSurface

    Box(
        modifier = modifier,
    ) {
        IconButton(onClick = onClick) {

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