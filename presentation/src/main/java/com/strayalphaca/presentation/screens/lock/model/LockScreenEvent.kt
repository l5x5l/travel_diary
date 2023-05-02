package com.strayalphaca.presentation.screens.lock.model

sealed class LockScreenEvent {
    class ChangeInputPassword(val inputPassword : String) : LockScreenEvent()
    object ClearInputPassword : LockScreenEvent()
    object PasswordChecking : LockScreenEvent()
}