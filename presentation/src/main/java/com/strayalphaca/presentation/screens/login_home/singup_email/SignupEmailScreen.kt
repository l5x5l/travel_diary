package com.strayalphaca.presentation.screens.login_home.singup_email

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_button.BaseButton
import com.strayalphaca.presentation.components.atom.base_icon_button.BaseIconButton
import com.strayalphaca.presentation.components.block.EditTextWithTitle
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import com.strayalphaca.presentation.components.atom.base_button.BaseButtonState
import com.strayalphaca.presentation.components.block.EditTextState

@Composable
fun SignupEmailScreen(
    navigateToSignup : () -> Unit = {},
    navigateToPassword : () -> Unit = {},
    viewModel : SignupEmailViewModel = viewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    val email by viewModel.email.collectAsState()
    val authCode by viewModel.authCode.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            BaseIconButton(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                iconResourceId = R.drawable.ic_close,
                onClick = navigateToSignup
            )

            Spacer(modifier = Modifier.height(100.dp))

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
            ) {
                Text(text = stringResource(id = R.string.signup), style = MaterialTheme.typography.h2)
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
                            modifier = Modifier.width(88.dp).height(40.dp),
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
                            modifier = Modifier.width(88.dp).height(40.dp),
                            text = stringResource(id = R.string.request_again),
                            onClick = { /*TODO*/ },
                            state = if (viewState.isActiveRequestAuthCodeButton) BaseButtonState.ACTIVE else BaseButtonState.INACTIVE
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            BaseButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(48.dp),
                text = stringResource(id = viewState.bottomButtonTextResource),
                onClick = {
                    when (viewState){
                        SignupEmailViewState.InputEmailStep -> {
                            viewModel.moveToAuthCodeStep()
                        }
                        SignupEmailViewState.AuthCodeStep -> {
                            navigateToPassword()
                        }
                    }
                }
            )

        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "dark"
)
@Preview(showBackground = true, widthDp = 360)
@Composable
fun SignupEmailScreenPreview(){
    TravelDiaryTheme {
        SignupEmailScreen()
    }
}