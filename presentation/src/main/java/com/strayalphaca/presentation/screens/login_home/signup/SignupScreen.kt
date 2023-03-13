package com.strayalphaca.presentation.screens.login_home.signup

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_button.BaseButton
import com.strayalphaca.presentation.components.atom.base_icon_button.BaseIconButton
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun SignupScreen(
    navigateToLogin : () -> Unit = {},
    navigateToSignupEmail : () -> Unit = {}
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            BaseIconButton(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                iconResourceId = R.drawable.ic_back,
                onClick = navigateToLogin
            )

            Spacer(modifier = Modifier.weight(0.5f))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                ) {
                Text(text = stringResource(id = R.string.signup), style = MaterialTheme.typography.h2)
                Spacer(modifier = Modifier.height(60.dp))
                BaseButton(
                    modifier = Modifier.fillMaxWidth().height(40.dp),
                    text = stringResource(id = R.string.signup_with_email),
                    onClick = { navigateToSignupEmail() }
                )
                Spacer(modifier = Modifier.height(16.dp))
                /* TODO 소셜 회원가입 버튼들 추가하기 */
            }

            Spacer(modifier = Modifier.weight(1f))
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
fun SignupScreenPreview(){
    TravelDiaryTheme {
        SignupScreen()
    }
}