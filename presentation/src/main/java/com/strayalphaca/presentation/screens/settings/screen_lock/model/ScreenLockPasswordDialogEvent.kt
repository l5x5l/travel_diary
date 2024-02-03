package com.strayalphaca.presentation.screens.settings.screen_lock.model

sealed class ScreenLockPasswordDialogEvent {
    object FillNewPassword : ScreenLockPasswordDialogEvent()
    object BackToInputNewPassword : ScreenLockPasswordDialogEvent()
    object FillConfirmNewPassword : ScreenLockPasswordDialogEvent()
    object NewPasswordNotMatched : ScreenLockPasswordDialogEvent()
    object NewPasswordMatched : ScreenLockPasswordDialogEvent()
    class FillExistPassword(val goal : CheckPasswordGoal) : ScreenLockPasswordDialogEvent()
    class ExistPasswordMatched(val goal: CheckPasswordGoal) : ScreenLockPasswordDialogEvent()
    class ExistPasswordNotMatched(val goal: CheckPasswordGoal) : ScreenLockPasswordDialogEvent()
    class SetBaseState(val type : ScreenLockPasswordDialogType) : ScreenLockPasswordDialogEvent()

    enum class CheckPasswordGoal {
        Change, Cancel
    }
}