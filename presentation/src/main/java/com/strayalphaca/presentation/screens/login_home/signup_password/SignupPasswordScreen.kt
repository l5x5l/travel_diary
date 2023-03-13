package com.strayalphaca.presentation.screens.login_home.signup_password

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_button.BaseButton
import com.strayalphaca.presentation.components.atom.base_icon_button.BaseIconButton
import com.strayalphaca.presentation.components.block.EditTextType
import com.strayalphaca.presentation.components.block.EditTextWithTitle
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue

@Composable
fun SignupPasswordScreen(
    navigateToSignup : () -> Unit = {},
    navigateToLogin : () -> Unit = {},
    viewModel : SignupPasswordViewModel = viewModel()
) {
    val password by viewModel.password.collectAsState()

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
                .padding(horizontal = 32.dp)) {
                Text(text = stringResource(id = R.string.signup), style = MaterialTheme.typography.h2)
                Spacer(modifier = Modifier.height(60.dp))
                EditTextWithTitle(
                    title = stringResource(id = R.string.password),
                    placeHolder = "",
                    type = EditTextType.PASSWORD,
                    value = password,
                    onValueChange = viewModel::inputPassword
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            BaseButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(48.dp),
                text = stringResource(id = R.string.signup),
                onClick = { navigateToLogin() }
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
fun SignupPasswordScreenPreview(){
    TravelDiaryTheme {
        SignupPasswordScreen()
    }
}