package com.strayalphaca.presentation.screens.intro

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_button.BaseButton
import com.strayalphaca.presentation.components.atom.text_button.TextButton
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun IntroScreen(

) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.25f))

            Image(
                modifier = Modifier.weight(0.125f),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "logo"
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(id = R.string.hello),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.intro),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(0.18f))

            BaseButton(
                text = stringResource(id = R.string.login),
                onClick = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 32.dp),
                isLoading = false
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                text = stringResource(id = R.string.continue_offline),
                modifier = Modifier.padding(horizontal = 32.dp),
                onClick = {  }
            )

            Spacer(modifier = Modifier.weight(0.25f))
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
fun IntroScreenPreview() {
    TravelDiaryTheme {
        IntroScreen()
    }
}