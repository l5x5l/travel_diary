package com.strayalphaca.presentation.screens.home.calendar.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_icon_button.BaseIconButton

@Composable
internal fun ToolButtons(
    modifier : Modifier = Modifier,
    onClickWrite : () -> Unit = {},
    onClickCalendarButton : () -> Unit = {},
    enable: Boolean
) {
    Row(modifier) {
        BaseIconButton(
            iconResourceId = R.drawable.ic_write,
            onClick = {
                if (!enable) return@BaseIconButton
                onClickWrite()
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        BaseIconButton(
            iconResourceId = R.drawable.ic_calendar,
            onClick = {
                if (!enable) return@BaseIconButton
                onClickCalendarButton()
            }
        )
    }
}