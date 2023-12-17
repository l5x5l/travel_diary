package com.strayalphaca.presentation.screens.login_home.login

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_button.BaseButton
import com.strayalphaca.presentation.components.atom.base_icon_button.BaseIconButton
import com.strayalphaca.presentation.components.atom.text_button.TextButton
import com.strayalphaca.presentation.components.block.EditTextWithTitle
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import androidx.compose.runtime.getValue
import com.strayalphaca.presentation.components.block.EditTextType
import com.strayalphaca.presentation.utils.collectAsEffect

@Composable
fun LoginScreen(
    navigateToSignup : () -> Unit = {},
    navigateToBack : () -> Unit = {},
    navigateToHome : () -> Unit = {},
    navigateToResetPassword : () -> Unit = {},
    viewModel : LoginViewModel = viewModel()
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val loginLoading by viewModel.networkLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    viewModel.loginSuccess.collectAsEffect { success ->
        if (success) {
            navigateToHome()
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            BaseIconButton(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                iconResourceId = R.drawable.ic_close,
                onClick = navigateToBack
            )

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
            ) {
                Text(text = stringResource(id = R.string.login), style = MaterialTheme.typography.h2)

                Spacer(modifier = Modifier.height(60.dp))

                EditTextWithTitle(
                    title = stringResource(id = R.string.email),
                    placeHolder = "",
                    value = email,
                    onValueChange = viewModel::inputEmail
                )

                Spacer(modifier = Modifier.height(16.dp))

                EditTextWithTitle(
                    title = stringResource(id = R.string.password),
                    placeHolder = "",
                    value = password,
                    onValueChange = viewModel::inputPassword,
                    type = EditTextType.PASSWORD,
                    showErrorText = true,
                    errorText = errorMessage
                )

                Spacer(modifier = Modifier.height(24.dp))

                BaseButton(
                    text = stringResource(id = R.string.login),
                    onClick = { viewModel.tryLogin() },
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    isLoading = loginLoading
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Spacer(modifier = Modifier.weight(1f))

                    TextButton(
                        text = stringResource(id = R.string.resetting_password),
                        onClick = navigateToResetPassword
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Divider(modifier = Modifier.width(1.dp).height(16.dp))

                    Spacer(modifier = Modifier.width(12.dp))

                    TextButton(
                        text = stringResource(id = R.string.signup),
                        onClick = navigateToSignup
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            SocialLoginArea(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

@Composable
fun SocialLoginArea(modifier : Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(id = R.string.login_with_social_account), style = MaterialTheme.typography.body2)

        Spacer(modifier = Modifier.height(24.dp))

        /* TODO 소셜 로그인 버튼 추가 */
        Row(modifier = Modifier) {
            // 카카오 로그인 버튼
            // 구글 로그인 버튼
            // 네이버 로그인 버튼
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    widthDp = 360,
    uiMode = UI_MODE_NIGHT_YES,
    name = "dark"
)
@Preview(showBackground = true, widthDp = 360)
fun LoginScreenPreview() {
    TravelDiaryTheme {
        LoginScreen()
    }
}