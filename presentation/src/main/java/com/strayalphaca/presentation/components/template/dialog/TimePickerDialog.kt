package com.strayalphaca.presentation.components.template.dialog

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.ui.theme.Tape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismissRequest : () -> Unit,
    setTime : (Int, Int) -> Unit,
    initHour : Int,
    initMinute : Int,
    is24Hour : Boolean
) {
    TapeDialog(
        onDismissRequest = onDismissRequest
    ) {
        val state = rememberTimePickerState(
            initialHour = initHour,
            initialMinute = initMinute,
            is24Hour = is24Hour
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                // 508 == 600 * 0.9 - (16 * 2)
                // 0.9는 TapeDialog의 width 비율, 16은 Column의 padding 크기
                TimePicker(
                    state = state,
                    layoutType = if (maxWidth < 508.dp) {
                        TimePickerLayoutType.Vertical
                    } else {
                        TimePickerLayoutType.Horizontal
                    },
                    colors = TimePickerDefaults.colors(
                        selectorColor = Tape,
                        clockDialColor = MaterialTheme.colors.surface,
                        clockDialUnselectedContentColor = MaterialTheme.colors.onSurface,
                        clockDialSelectedContentColor = Color.Black,
                        timeSelectorSelectedContainerColor = Tape,
                        timeSelectorSelectedContentColor = Color.Black,
                        timeSelectorUnselectedContainerColor = MaterialTheme.colors.surface,
                        timeSelectorUnselectedContentColor = MaterialTheme.colors.onSurface,
                        periodSelectorSelectedContainerColor = Tape,
                        periodSelectorSelectedContentColor = Color.Black,
                        periodSelectorUnselectedContainerColor = MaterialTheme.colors.surface,
                        periodSelectorUnselectedContentColor = MaterialTheme.colors.onSurface,
                    )
                )
            }


            Row(
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text(text = stringResource(id = R.string.cancel), color = MaterialTheme.colors.onSurface)
                }

                TextButton(
                    onClick = {
                        onDismissRequest()
                        setTime(state.hour, state.minute)
                    }
                ) {
                    Text(text = stringResource(id = R.string.confirm), color = MaterialTheme.colors.onSurface)
                }
            }
        }
    }
}