package com.strayalphaca.presentation.screens.settings.screen_lock.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.components.block.LockScreenTextField
import com.strayalphaca.presentation.components.template.dialog.TapeDialog
import com.strayalphaca.presentation.ui.theme.errorRed
import com.strayalphaca.presentation.utils.collectAsEffect

@Composable
fun ScreenPasswordDialog(
    viewModel: ScreenPasswordDialogViewModel,
    dismiss : () -> Unit = {}
) {
    val text by viewModel.inputPassword.collectAsState()
    val state by viewModel.state.collectAsState()
    val focusRequester = remember { FocusRequester() }

    viewModel.dismissDialogEvent.collectAsEffect {
        dismiss()
    }

    LaunchedEffect(null) {
        focusRequester.requestFocus()
    }

    TapeDialog(onDismissRequest = {}) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = state.titleResourceId),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            LockScreenTextField(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .focusRequester(focusRequester),
                text = text,
                onTextChanged = {
                    if (it.length <= 4) {
                        viewModel.setInputPassword(it)
                    }
                    if (it.length >= 4) {
                        state.onFillTextEvent(it)
                    }
                },
                enabled = state.interactionEnabled
            )

            state.errorMessageStringResourceId?.let { resourceId ->
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    modifier = Modifier.align(CenterHorizontally),
                    text = stringResource(id = resourceId),
                    color = errorRed,
                    style = MaterialTheme.typography.body2
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                state.leftButtonTextResourceId?.let { stringResourceId ->
                    TextButton(
                        onClick = {
                            state.leftButtonPressEvent()
                        },
                        enabled = state.interactionEnabled
                    ) {
                        Text(text = stringResource(id = stringResourceId), color = MaterialTheme.colors.onSurface)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                TextButton(
                    onClick = {
                        state.rightButtonPressEvent()
                    },
                    enabled = state.interactionEnabled
                ) {
                    Text(text = stringResource(id = state.rightButtonTextResourceId), color = MaterialTheme.colors.onSurface)
                }
            }
        }
    }
}