package com.strayalphaca.presentation.screens.settings.language_setting

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.components.atom.text_button.TextButton
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun LanguageSettingScreen() {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp, vertical = 12.dp)) {
        TextButton(modifier = Modifier.fillMaxWidth(), text = "한국어")
        TextButton(modifier = Modifier.fillMaxWidth(), text = "English")
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
fun LanguageSettingScreenPreview() {
    TravelDiaryTheme {
        Surface {
            LanguageSettingScreen()
        }
    }
}