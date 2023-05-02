package com.strayalphaca.presentation.screens.lock

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.block.LockScreenTextField
import com.strayalphaca.presentation.screens.lock.model.LockScreenSideEffect
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import com.strayalphaca.presentation.utils.observeWithLifecycle

@Composable
fun LockScreen(
    backToContent : () -> Unit = {},
    viewModel : LockViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    viewModel.sideEffect.observeWithLifecycle{ lockScreenSideEffect ->
        when (lockScreenSideEffect) {
            LockScreenSideEffect.ShowFailMessage -> {
                // show error msg
            }
            LockScreenSideEffect.CheckComplete -> {
                backToContent()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.lock_input_password),
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        LockScreenTextField(
            text = state.inputPassword,
            onTextChanged = viewModel::inputPassword,
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    widthDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "dark"
)
@Preview(showBackground = true, widthDp = 360)
fun DiaryWriteScreenPreview() {
    TravelDiaryTheme {
        //LockScreen()
        Column(
            modifier = Modifier.padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.lock_input_password),
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(top = 16.dp)
            )

            LockScreenTextField(
                text = "13",
                onTextChanged = {}
            )
        }
    }
}