package com.strayalphaca.presentation.screens.settings.screen_lock

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.components.block.TextWithSwitch
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.text_button.TextButton
import com.strayalphaca.presentation.components.atom.text_button.TextButtonState
import com.strayalphaca.presentation.screens.settings.screen_lock.components.ScreenPasswordDialog
import com.strayalphaca.presentation.screens.settings.screen_lock.components.ScreenPasswordDialogViewModel

@Composable
fun ScreenLockScreenContainer(
    screenViewModel: ScreenLockViewModel,
    dialogViewModel: ScreenPasswordDialogViewModel
) {
    val usePassword by screenViewModel.usePassword.collectAsState()
    val dialogState by screenViewModel.dialogState.collectAsState()

    LaunchedEffect(dialogState) {
        dialogState?.let { type ->
            dialogViewModel.setBase(type)
        }
    }

    dialogState?.let {
        ScreenPasswordDialog(viewModel = dialogViewModel, dismiss = screenViewModel::dismissDialog)
    }

    ScreenLockScreen(usePassword, screenViewModel::setUsePassword, screenViewModel::tryChangePassword)
}

@Composable
fun ScreenLockScreen(
    usePassword : Boolean = true,
    onToggleUsePassword : (Boolean) -> Unit = {},
    onPressChangePassword : () -> Unit = {}
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp, vertical = 12.dp)
    ) {
        TextWithSwitch(text = stringResource(id = R.string.use_screen_lock), onCheckedChange = onToggleUsePassword, checked = usePassword)
//        TextWithSwitch(text = stringResource(id = R.string.use_biometric))
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.change_password),
            state = if (usePassword) TextButtonState.ACTIVE else TextButtonState.INACTIVE,
            textStyle = MaterialTheme.typography.body1,
            onClick = {
                onPressChangePassword()
            }
        )
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