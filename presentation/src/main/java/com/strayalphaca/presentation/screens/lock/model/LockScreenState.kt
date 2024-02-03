package com.strayalphaca.presentation.screens.lock.model

data class LockScreenState (
    val inputPassword : String = "",
    val checkingPassword : Boolean = false,
    val showError : Boolean = false
)