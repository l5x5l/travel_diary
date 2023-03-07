package com.strayalphaca.presentation.screens.settings.push_alarm

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_button.BaseButton
import com.strayalphaca.presentation.components.atom.base_button.BaseButtonState
import com.strayalphaca.presentation.components.block.TextWithSwitch
import com.strayalphaca.presentation.ui.theme.Gray4
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun PushAlarmScreen() {


    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp, vertical = 12.dp)
    ) {
        TextWithSwitch(
            text = stringResource(id = R.string.use_push_alarm),
            subText = stringResource(id = R.string.sub_message_push_alarm)
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(modifier = Modifier.weight(1f), text = stringResource(id = R.string.time))
            Text(text = "오후 8:30")
        }

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
            items(5) {index ->

                BaseButton(
                    text = "$index item",
                    onClick = {  },
                    textStyle = MaterialTheme.typography.caption,
                    state = if (index == 1) BaseButtonState.SELECTED else BaseButtonState.ACTIVE
                )

            }
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    widthDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "dark"
)
@Preview(showBackground = true, widthDp = 360)
fun PushAlarmScreenPreview() {
    TravelDiaryTheme {
        Surface {
            PushAlarmScreen()
        }
    }
}