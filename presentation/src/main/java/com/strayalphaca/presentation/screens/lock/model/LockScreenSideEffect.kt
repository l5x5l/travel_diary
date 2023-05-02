package com.strayalphaca.presentation.screens.lock.model

sealed class LockScreenSideEffect {
    object ShowFailMessage : LockScreenSideEffect()
    object CheckComplete : LockScreenSideEffect()
}