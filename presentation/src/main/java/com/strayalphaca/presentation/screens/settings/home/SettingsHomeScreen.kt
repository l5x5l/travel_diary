package com.strayalphaca.presentation.screens.settings.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.strayalphaca.presentation.components.atom.text_button.TextButton
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.template.dialog.TwoButtonDialog
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import com.strayalphaca.presentation.utils.collectAsEffect

@Composable
fun SettingsHomeScreenContainer(
    viewModel : SettingsHomeViewModel = hiltViewModel(),
    navigateToPushAlarm : () -> Unit = {},
    navigateToLanguageSetting : () -> Unit = {},
    navigateToScreenLock : () -> Unit = {},
    navigateToWithdrawal : () -> Unit = {},
    navigateToLogin : () -> Unit = {},
    navigateToIntro : () -> Unit = {},
    navigateToChangePassword : () -> Unit = {}
) {
    val isLogin by viewModel.isLogin.collectAsState()
    val showLogoutCheckDialog by viewModel.logoutCheckDialogVisible.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.checkIsLogin()
    }

    viewModel.navigateToIntroEvent.collectAsEffect {
        if (it) {
            navigateToIntro()
        }
    }

    if (showLogoutCheckDialog) {
        TwoButtonDialog(
            title = stringResource(id = R.string.logout),
            mainText = stringResource(id = R.string.logout_check_dialog_text),
            leftButtonText = stringResource(id = R.string.no),
            leftButtonClick = viewModel::hideLogoutCheckDialog,
            rightButtonText = stringResource(id = R.string.yes),
            rightButtonClick = {
                viewModel.hideLogoutCheckDialog()
                viewModel.logout()
            },
            onDismissRequest = viewModel::hideLogoutCheckDialog
        )
    }

    SettingsHomeScreen(
        navigateToPushAlarm = navigateToPushAlarm,
        navigateToLanguageSetting = navigateToLanguageSetting,
        navigateToScreenLock = navigateToScreenLock,
        navigateToWithdrawal = navigateToWithdrawal,
        navigateToLogin = navigateToLogin,
        navigateToChangePassword = navigateToChangePassword,
        logoutClick = viewModel::showLogoutCheckDialog,
        isLogin = isLogin
    )
}

@Composable
fun SettingsHomeScreen(
    navigateToPushAlarm : () -> Unit = {},
    navigateToLanguageSetting : () -> Unit = {},
    navigateToScreenLock : () -> Unit = {},
    navigateToWithdrawal : () -> Unit = {},
    navigateToLogin : () -> Unit = {},
    navigateToChangePassword : () -> Unit = {},
    logoutClick : () -> Unit = {},
    isLogin : Boolean = false
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp, vertical = 12.dp)
    ) {
        // TextButton(modifier = Modifier.fillMaxWidth(), text = stringResource(id = R.string.screen_lock), onClick = navigateToScreenLock)
        // TextButton(modifier = Modifier.fillMaxWidth(), text = stringResource(id = R.string.language_setting), onClick = navigateToLanguageSetting)

        // 로그인 상태에 따라 로그인/로그아웃 전환 필요
        if (isLogin) {
            TextButton(modifier = Modifier.fillMaxWidth(), text = stringResource(id = R.string.push_alarm), onClick = navigateToPushAlarm)
            TextButton(modifier = Modifier.fillMaxWidth(), text = stringResource(id = R.string.change_password), onClick = navigateToChangePassword)
            TextButton(modifier = Modifier.fillMaxWidth(), text = stringResource(id = R.string.logout), onClick = logoutClick)
            TextButton(modifier = Modifier.fillMaxWidth(), text = stringResource(id = R.string.withdrawal), onClick = navigateToWithdrawal)
        } else {
            TextButton(modifier = Modifier.fillMaxWidth(), text = stringResource(id = R.string.login), onClick = navigateToLogin)
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
fun SettingsHomeScreenPreview() {
    TravelDiaryTheme() {
        Surface {
            SettingsHomeScreen(isLogin = true)
        }
    }
}