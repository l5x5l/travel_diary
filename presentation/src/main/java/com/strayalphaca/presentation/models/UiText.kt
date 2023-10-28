package com.strayalphaca.presentation.models

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText{
    class StringResource(
        @StringRes val resourceId : Int
    ) : UiText()

    class StringResourceWithArgs(
        @StringRes val resourceId: Int,
        vararg val args : Any
    ) : UiText()

    @Composable
    fun asString() : String {
        return when(this) {
            is StringResource -> {
                stringResource(resourceId)
            }
            is StringResourceWithArgs -> {
                stringResource(resourceId, *args)
            }
        }
    }

    fun asString(context : Context) : String {
        return when(this) {
            is StringResource -> {
                context.getString(resourceId)
            }
            is StringResourceWithArgs -> {
                context.getString(resourceId, *args)
            }
        }
    }
}