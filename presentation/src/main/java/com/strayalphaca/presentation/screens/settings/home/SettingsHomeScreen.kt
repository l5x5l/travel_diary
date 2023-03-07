package com.strayalphaca.presentation.screens.settings.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.components.atom.text_button.TextButton
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun SettingsHomeScreen(
    navigateToPushAlarm : () -> Unit = {},
    navigateToLanguageSetting : () -> Unit = {},
    navigateToScreenLock : () -> Unit = {},
    navigateToWithdrawal : () -> Unit = {},
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp, vertical = 12.dp)
    ) {
        TextButton(modifier = Modifier.fillMaxWidth(), text = stringResource(id = R.string.push_alarm), onClick = navigateToPushAlarm)
        TextButton(modifier = Modifier.fillMaxWidth(), text = stringResource(id = R.string.screen_lock), onClick = navigateToScreenLock)
        TextButton(modifier = Modifier.fillMaxWidth(), text = stringResource(id = R.string.language_setting), onClick = navigateToLanguageSetting)
        TextButton(modifier = Modifier.fillMaxWidth(), text = stringResource(id = R.string.withdrawal), onClick = navigateToWithdrawal)
    }
}

@Composable
@Preview(
    showBackground = true,
    widthDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "dark"
)
fun SettingsHomeScreenPreview() {
    TravelDiaryTheme() {
        Surface {
            SettingsHomeScreen()
        }
    }
}