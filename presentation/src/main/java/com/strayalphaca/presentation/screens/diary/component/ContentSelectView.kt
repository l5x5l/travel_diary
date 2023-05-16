package com.strayalphaca.presentation.screens.diary.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T>ContentSelectView(
    contentList : List<T>,
    contentRender : @Composable (T) -> Unit
) {
    Row(
        modifier = Modifier.padding(8.dp)
    ) {
        contentList.forEach { content ->
            contentRender(content)
        }
    }
}