package com.strayalphaca.presentation.screens.lock

import android.content.Intent
import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.block.LockScreenTextField
import com.strayalphaca.presentation.screens.lock.model.LockScreenSideEffect
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import com.strayalphaca.presentation.ui.theme.errorRed
import com.strayalphaca.presentation.utils.observeWithLifecycle

@Composable
fun LockScreen(
    backToContent : () -> Unit = {},
    viewModel : LockViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(null) {
        focusRequester.requestFocus()
    }

    BackHandler(true) {
        Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }.run {
            context.startActivity(this)
        }
    }

    viewModel.sideEffect.observeWithLifecycle{ lockScreenSideEffect ->
        when (lockScreenSideEffect) {
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
            modifier = Modifier.padding(top = 16.dp),
            color = MaterialTheme.colors.onBackground
        )

        Spacer(modifier = Modifier.height(48.dp))

        LockScreenTextField(
            modifier = Modifier.focusRequester(focusRequester),
            text = state.inputPassword,
            onTextChanged = viewModel::inputPassword,
        )

        if (state.showError) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(id = R.string.lock_wrong_password),
                color = errorRed,
                style = MaterialTheme.typography.body2
            )
        }
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
        Surface() {
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
}