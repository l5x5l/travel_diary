package com.strayalphaca.presentation.components.block

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.ui.theme.Gray4
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun TextWithSwitch(
    text : String,
    modifier : Modifier = Modifier,
    subText : String ?= null,
    checked : Boolean = false,
    onCheckedChange : (Boolean) -> Unit = {}
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier
            .padding(vertical = 12.dp)
            .weight(1f)) {
            Text(text = text, style = MaterialTheme.typography.body1)
            subText?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = it, style = MaterialTheme.typography.caption, color = Gray4)
            }
        }

        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
@Preview(name="light mode", widthDp = 360)
@Preview(
    name="dark mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 360
)
fun TextWithSwitchPreview() {
    TravelDiaryTheme() {
        Surface {
            TextWithSwitch(modifier = Modifier.fillMaxWidth(), text = "푸시 알림 적용", subText = "지정된 시간마다 알람을 보내드려요")
        }
    }
}