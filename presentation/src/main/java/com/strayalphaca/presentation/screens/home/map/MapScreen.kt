package com.strayalphaca.presentation.screens.home.map

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MapScreen(
    modifier : Modifier = Modifier,
    onDiaryClick : (Int) -> Unit = {}
) {
    Row(modifier = modifier) {
        Text(modifier = Modifier.clickable { onDiaryClick(2) } , text = "Map")
    }
}