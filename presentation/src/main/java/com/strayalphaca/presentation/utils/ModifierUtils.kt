package com.strayalphaca.presentation.utils

import androidx.compose.ui.Modifier

inline fun Modifier.thenIf(
    condition : Boolean,
    crossinline other : Modifier.() -> Modifier
) : Modifier = if (condition) other() else this