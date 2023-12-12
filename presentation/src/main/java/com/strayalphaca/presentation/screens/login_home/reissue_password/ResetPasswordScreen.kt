package com.strayalphaca.presentation.screens.login_home.reissue_password

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_button.BaseButton
import com.strayalphaca.presentation.components.atom.base_button.BaseButtonState
import com.strayalphaca.presentation.components.atom.base_icon_button.BaseIconButton
import com.strayalphaca.presentation.components.block.EditTextState
import com.strayalphaca.presentation.components.block.EditTextWithTitle
import com.strayalphaca.presentation.components.template.dialog.OneButtonDialog
import com.strayalphaca.presentation.utils.observeWithLifecycle

@Composable
fun ResetPasswordScreen(
    navigateToLogin: () -> Unit = {},
    viewModel: ResetPasswordViewModel = viewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val email by viewModel.email.collectAsState()
    val authCode by viewModel.authCode.collectAsState()
    val timerString by viewModel.timerValue.collectAsState()
    val showResetPasswordSuccessDialog by viewModel.showResetPasswordSuccessDialog.collectAsState()
    val context = LocalContext.current

    viewModel.showToastEvent.observeWithLifecycle { message ->
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    if (showResetPasswordSuccessDialog) {
        OneButtonDialog(
            title = stringResource(id = R.string.complete_reset_password),
            mainText = stringResource(id = R.string.sub_message_complete_reset_password),
            buttonText = stringResource(id = R.string.check),
            buttonClick = {
                viewModel.hideResetPasswordSuccessDialog()
                navigateToLogin()
            },
            onDismissRequest = viewModel::hideResetPasswordSuccessDialog
        )
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            BaseIconButton(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                iconResourceId = R.drawable.ic_close,
                onClick = navigateToLogin
            )

            Spacer(modifier = Modifier.height(100.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.resetting_password),
                    style = MaterialTheme.typography.h2
                )
                Spacer(modifier = Modifier.height(60.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    EditTextWithTitle(
                        modifier = Modifier.weight(1f),
                        title = stringResource(id = R.string.email),
                        placeHolder = stringResource(id = R.string.placeholder_input_email),
                        value = email,
                        onValueChange = viewModel::inputEmail,
                        state = if (viewState.isActiveEmailTextField) EditTextState.ACTIVE else EditTextState.INACTIVE
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    if (viewState.isShowChangeEmailButton) {
                        BaseButton(
                            modifier = Modifier
                                .width(88.dp)
                                .height(40.dp),
                            text = stringResource(id = R.string.change_email),
                            onClick = viewModel::backToInputEmailStep
                        )
                    }
                }
                Spacer(modifier = Modifier.height(40.dp))

                if (viewState.isShowAuthCodeArea) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        EditTextWithTitle(
                            modifier = Modifier.weight(1f),
                            title = stringResource(id = R.string.input_auth_code),
                            placeHolder = stringResource(id = R.string.placeholder_input_auth_code),
                            value = authCode,
                            onValueChange = viewModel::inputAuthCode,
                            state = if (viewState.isActiveAuthCodeTextField) EditTextState.ACTIVE else EditTextState.INACTIVE
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        BaseButton(
                            modifier = Modifier
                                .width(88.dp)
                                .height(40.dp),
                            text = stringResource(id = R.string.request_again),
                            onClick = { viewModel.tryReIssueAuthCode() },
                            state = if (viewState.isActiveRequestAuthCodeButton) BaseButtonState.ACTIVE else BaseButtonState.INACTIVE
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.remain_time) + " $timerString",
                        style = MaterialTheme.typography.caption
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            BaseButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(48.dp),
                text = stringResource(id = viewState.bottomButtonTextResource),
                state = if (viewState.bottomButtonActive) BaseButtonState.ACTIVE else BaseButtonState.INACTIVE,
                onClick = {
                    if (viewState is ResetPasswordViewState.InputEmailStep || viewState is ResetPasswordViewState.IssuingAuthCode) {
                        viewModel.tryCheckAndIssueAuthCode()
                    } else {
                        viewModel.tryCheckAuthCode()
                    }
                }
            )

        }
    }
}
