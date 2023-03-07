package com.strayalphaca.presentation.screens.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.strayalphaca.presentation.screens.settings.home.SettingsHomeScreen
import com.strayalphaca.presentation.screens.settings.language_setting.LanguageSettingScreen
import com.strayalphaca.presentation.screens.settings.push_alarm.PushAlarmScreen
import com.strayalphaca.presentation.screens.settings.screen_lock.ScreenLockScreen
import com.strayalphaca.presentation.screens.settings.withdrawal.WithdrawalScreen

@Composable
fun SettingsNavHost(
    navController: NavHostController,
    modifier : Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SettingsHomeScreenDestination.route,
        modifier = modifier,
    ) {

        composable(SettingsHomeScreenDestination.route) {
            SettingsHomeScreen(
                navigateToLanguageSetting = { navController.navigate(LanguageSettingScreenDestination.route) },
                navigateToPushAlarm = { navController.navigate(PushAlarmScreenDestination.route) },
                navigateToScreenLock = { navController.navigate(ScreenLockScreenDestination.route) },
                navigateToWithdrawal = { navController.navigate(WithdrawalScreenDestination.route) }
            )
        }

        composable(PushAlarmScreenDestination.route) {
            PushAlarmScreen()
        }

        composable(ScreenLockScreenDestination.route) {
            ScreenLockScreen()
        }

        composable(LanguageSettingScreenDestination.route) {
            LanguageSettingScreen()
        }

        composable(WithdrawalScreenDestination.route) {
            WithdrawalScreen()
        }

    }
}