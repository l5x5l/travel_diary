package com.strayalphaca.presentation.screens.settings.push_alarm

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_button.BaseButton
import com.strayalphaca.presentation.components.atom.base_button.BaseButtonState
import com.strayalphaca.presentation.components.block.TextWithSwitch
import com.strayalphaca.presentation.models.Route
import com.strayalphaca.presentation.ui.theme.Gray4
import com.strayalphaca.presentation.utils.minuteIn24HourToHour12

@Composable
fun PushAlarmScreen(
    viewModel : PushAlarmViewModel
) {
    val context = LocalContext.current

    val usePushAlarm by viewModel.usePushAlarm.collectAsState()
    val pushAlarmMinute by viewModel.pushAlarmMinute.collectAsState()
    val targetUrl by viewModel.clickTarget.collectAsState()

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            viewModel.setPushAlarmTime(hour, minute)
        },
        pushAlarmMinute / 60,
        pushAlarmMinute % 60,
        false
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp, vertical = 12.dp)
    ) {
        TextWithSwitch(
            text = stringResource(id = R.string.use_push_alarm),
            subText = stringResource(id = R.string.sub_message_push_alarm),
            checked = usePushAlarm,
            onCheckedChange = viewModel::setUsePushAlarm
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(modifier = Modifier.weight(1f), text = stringResource(id = R.string.time))
            Text(
                text = stringResource(
                    id = if (pushAlarmMinute >= 12 * 60) R.string.time_pm else R.string.time_am,
                    minuteIn24HourToHour12(pushAlarmMinute),
                    pushAlarmMinute % 60
                ),
                modifier = Modifier.clickable {
                    if (usePushAlarm) timePickerDialog.show()
                },
            )
        }
        Spacer(modifier = Modifier.height(18.dp))

        Text(text = stringResource(id = R.string.push_alarm_click_event))
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = stringResource(id = R.string.sub_message_push_alarm_click_event),
            style = MaterialTheme.typography.caption,
            color = Gray4
        )

        Spacer(modifier = Modifier.height(18.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(Route.pushAlarmTargetList) { item ->
                BaseButton(
                    text = stringResource(id = item.screenNameId),
                    onClick = { viewModel.setPushAlarmClickTarget(item) },
                    textStyle = MaterialTheme.typography.caption,
                    state = when {
                        (!usePushAlarm) -> {
                            BaseButtonState.INACTIVE
                        }
                        (item == targetUrl) -> {
                            BaseButtonState.SELECTED
                        }
                        else -> {
                            BaseButtonState.ACTIVE
                        }
                    },
                    modifier = Modifier.height(40.dp)
                )

            }
        }
    }
}