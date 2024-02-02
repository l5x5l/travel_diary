package com.strayalphaca.presentation.screens.settings.screen_lock.model

import com.strayalphaca.presentation.R

sealed class ScreenLockPasswordDialogState(
    val titleResourceId : Int,
    val errorMessageStringResourceId : Int? = null,
    val leftButtonTextResourceId : Int? = null,
    open val leftButtonPressEvent : () -> Unit = {},
    val rightButtonTextResourceId : Int = R.string.cancel,
    open val rightButtonPressEvent : () -> Unit = {},
    open val onFillTextEvent : (String) -> Unit = {},
    val interactionEnabled : Boolean = true
) {
    data class InputNewPassword(
        override val rightButtonPressEvent: () -> Unit,
        override val onFillTextEvent: (String) -> Unit
    ) : ScreenLockPasswordDialogState(
        titleResourceId = R.string.input_new_screen_password_title,
        rightButtonPressEvent = rightButtonPressEvent,
        onFillTextEvent = onFillTextEvent
    )

    data class InputNewPasswordAgain(
        override val rightButtonPressEvent: () -> Unit,
        override val leftButtonPressEvent: () -> Unit,
        override val onFillTextEvent: (String) -> Unit
    ) : ScreenLockPasswordDialogState(
        titleResourceId = R.string.check_screen_password_title,
        leftButtonTextResourceId = R.string.move_back,
        rightButtonPressEvent = rightButtonPressEvent,
        onFillTextEvent = onFillTextEvent
    )

    object CheckingNewPassword : ScreenLockPasswordDialogState(
        titleResourceId = R.string.check_screen_password_title,
        rightButtonPressEvent = {},
        onFillTextEvent = {},
        interactionEnabled = false
    )

    data class CheckingNewPasswordFail(
        override val rightButtonPressEvent: () -> Unit,
        override val onFillTextEvent: (String) -> Unit
    ) : ScreenLockPasswordDialogState(
        titleResourceId = R.string.check_screen_password_title,
        errorMessageStringResourceId = R.string.password_not_macthed,
        rightButtonPressEvent = rightButtonPressEvent,
        onFillTextEvent = onFillTextEvent
    )

    data class InputExistPasswordForCancel(
        override val rightButtonPressEvent: () -> Unit,
        override val onFillTextEvent: (String) -> Unit
    ) : ScreenLockPasswordDialogState(
        titleResourceId = R.string.input_exist_screen_password_title,
        rightButtonPressEvent = rightButtonPressEvent,
        onFillTextEvent = onFillTextEvent
    )

    object CheckingExistPasswordForCancel : ScreenLockPasswordDialogState(
        titleResourceId = R.string.input_exist_screen_password_title,
        interactionEnabled = false
    )

    data class CheckingExistPasswordForCancelFail(
        override val rightButtonPressEvent: () -> Unit,
        override val onFillTextEvent: (String) -> Unit
    ) : ScreenLockPasswordDialogState(
        titleResourceId = R.string.input_exist_screen_password_title,
        errorMessageStringResourceId = R.string.password_not_macthed,
        rightButtonPressEvent = rightButtonPressEvent,
        onFillTextEvent = onFillTextEvent
    )

    data class InputExistPasswordForChange(
        override val rightButtonPressEvent: () -> Unit,
        override val onFillTextEvent: (String) -> Unit
    ) : ScreenLockPasswordDialogState(
        titleResourceId = R.string.input_exist_screen_password_title,
        rightButtonPressEvent = rightButtonPressEvent,
        onFillTextEvent = onFillTextEvent
    )

    object CheckingExistPasswordForChange : ScreenLockPasswordDialogState(
        titleResourceId = R.string.input_exist_screen_password_title,
        interactionEnabled = false
    )

    data class CheckingExistPasswordForChangeFail(
        override val rightButtonPressEvent: () -> Unit,
        override val onFillTextEvent: (String) -> Unit
    ) : ScreenLockPasswordDialogState(
        titleResourceId = R.string.input_exist_screen_password_title,
        errorMessageStringResourceId = R.string.password_not_macthed,
        rightButtonTextResourceId = R.string.cancel,
        rightButtonPressEvent = rightButtonPressEvent,
        onFillTextEvent = onFillTextEvent
    )

}