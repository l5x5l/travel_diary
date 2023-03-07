package com.strayalphaca.presentation.screens.settings.screen_lock

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.components.block.TextWithSwitch
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.text_button.TextButton

@Composable
fun ScreenLockScreen() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp, vertical = 12.dp)
    ) {
        TextWithSwitch(text = stringResource(id = R.string.use_screen_lock))
        TextWithSwitch(text = stringResource(id = R.string.use_biometric))
        TextButton(modifier = Modifier.fillMaxWidth(), text = stringResource(id = R.string.change_password), textStyle = MaterialTheme.typography.body1)
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
fun ScreenLockScreenPreview() {
    TravelDiaryTheme {
        Surface {
            ScreenLockScreen()
        }
    }
}