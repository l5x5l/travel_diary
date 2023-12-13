package com.strayalphaca.presentation.screens.settings

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.strayalphaca.presentation.screens.settings.change_password.ChangePasswordScreen
import com.strayalphaca.presentation.screens.settings.change_password.ChangePasswordViewModel
import com.strayalphaca.presentation.screens.settings.home.SettingsHomeScreenContainer
import com.strayalphaca.presentation.screens.settings.language_setting.LanguageSettingScreen
import com.strayalphaca.presentation.screens.settings.push_alarm.PushAlarmScreen
import com.strayalphaca.presentation.screens.settings.push_alarm.PushAlarmViewModel
import com.strayalphaca.presentation.screens.settings.screen_lock.ScreenLockScreen
import com.strayalphaca.presentation.screens.settings.withdrawal.WithdrawalScreen
import com.strayalphaca.presentation.screens.settings.withdrawal.WithdrawalViewModel

@Composable
fun SettingsNavHost(
    navController: NavHostController,
    modifier : Modifier = Modifier,
    navigateToLogin : () -> Unit,
    navigateToIntro : () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = SettingsHomeScreenDestination.route,
        modifier = modifier,
    ) {

        composable(SettingsHomeScreenDestination.route) {
            SettingsHomeScreenContainer(
                navigateToLanguageSetting = { navController.navigate(LanguageSettingScreenDestination.route) },
                navigateToPushAlarm = { navController.navigate(PushAlarmScreenDestination.route) },
                navigateToScreenLock = { navController.navigate(ScreenLockScreenDestination.route) },
                navigateToWithdrawal = { navController.navigate(WithdrawalScreenDestination.route) },
                navigateToLogin = { navigateToLogin() },
                navigateToIntro = { navigateToIntro() },
                navigateToChangePassword = { navController.navigate(ChangePasswordScreenDestination.route) }
            )
        }

        composable(PushAlarmScreenDestination.route) {
            val viewModel = hiltViewModel<PushAlarmViewModel>()
            PushAlarmScreen(viewModel)
        }

        composable(ScreenLockScreenDestination.route) {
            ScreenLockScreen()
        }

        composable(LanguageSettingScreenDestination.route) {
            LanguageSettingScreen()
        }

        composable(WithdrawalScreenDestination.route) {
            val viewModel = hiltViewModel<WithdrawalViewModel>()
            WithdrawalScreen(modifier = Modifier.fillMaxHeight(), viewModel = viewModel)
        }

        composable(ChangePasswordScreenDestination.route) {
            val viewModel = hiltViewModel<ChangePasswordViewModel>()
            ChangePasswordScreen(viewModel = viewModel)
        }

    }
}