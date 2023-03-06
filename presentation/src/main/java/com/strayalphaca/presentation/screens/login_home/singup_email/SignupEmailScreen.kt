package com.strayalphaca.presentation.screens.login_home.singup_email

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_button.BaseButton
import com.strayalphaca.presentation.components.atom.base_button.BaseIconButton
import com.strayalphaca.presentation.components.block.EditTextWithTitle
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun SignupEmailScreen(
    navigateToSignup : () -> Unit = {},
    navigateToPassword : () -> Unit = {}
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            BaseIconButton(
                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                iconResourceId = R.drawable.ic_close,
                onClick = navigateToSignup
            )

            Spacer(modifier = Modifier.height(100.dp))

            SignupEmailBody()

            Spacer(modifier = Modifier.weight(1f))

            /* TODO step에 따라 클릭 이벤트 및 텍스트 변경되도록 수정하기 */
            BaseButton(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), text = "", onClick = { navigateToPassword() })
        }
    }
}

@Composable
fun SignupEmailBody() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp)
    ) {
        Text(text = stringResource(id = R.string.signup), style = MaterialTheme.typography.h2)
        Spacer(modifier = Modifier.height(60.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            EditTextWithTitle(modifier = Modifier.weight(1f), title = stringResource(id = R.string.email), placeHolder = "", value = "")
            Spacer(modifier = Modifier.width(12.dp))
            BaseButton(modifier = Modifier.width(88.dp), text = stringResource(id = R.string.change_email), onClick = { /*TODO*/ })
        }
        Spacer(modifier = Modifier.height(40.dp))
        /* TODO step에 따라서 이메일이 정해졌을 때만 보여지도록 수정 */
        Row(verticalAlignment = Alignment.Bottom) {
            EditTextWithTitle(
                modifier = Modifier.weight(1f),
                title = stringResource(id = R.string.input_auth_code),
                placeHolder = stringResource(id = R.string.placeholder_input_email),
                value = ""
            )
            Spacer(modifier = Modifier.width(12.dp))
            BaseButton(modifier = Modifier.width(88.dp), text = stringResource(id = R.string.request_again), onClick = { /*TODO*/ })
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