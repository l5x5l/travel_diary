package com.strayalphaca.presentation.screens.home.calendar.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun YearMonthText(
    modifier : Modifier = Modifier,
    year : Int,
    month : Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(text = month.toString(), style = MaterialTheme.typography.h1)

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = year.toString(), style = MaterialTheme.typography.h2)
    }
}