package com.strayalphaca.presentation.screens.settings

import com.strayalphaca.presentation.R

interface SettingsDestinations {
    val route : String
    val titleStringId : Int
}

object SettingsHomeScreenDestination : SettingsDestinations {
    override val route: String = "settings_home"
    override val titleStringId : Int = R.string.settings
}

object PushAlarmScreenDestination : SettingsDestinations {
    override val route: String = "push_alarm"
    override val titleStringId : Int = R.string.push_alarm
}

object LanguageSettingScreenDestination : SettingsDestinations {
    override val route: String = "language"
    override val titleStringId : Int = R.string.language_setting
}

object ScreenLockScreenDestination : SettingsDestinations {
    override val route: String = "screen_lock"
    override val titleStringId : Int = R.string.screen_lock
}

object WithdrawalScreenDestination : SettingsDestinations {
    override val route: String = "withdrawal"
    override val titleStringId : Int = R.string.withdrawal
}

object ChangePasswordScreenDestination : SettingsDestinations {
    override val route: String = "change_password"
    override val titleStringId: Int = R.string.change_password
}


val settingsScreens = listOf(SettingsHomeScreenDestination, PushAlarmScreenDestination, LanguageSettingScreenDestination, ScreenLockScreenDestination, WithdrawalScreenDestination, ChangePasswordScreenDestination)