package com.strayalphaca.presentation.screens.settings.change_password

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_button.BaseButton
import com.strayalphaca.presentation.components.atom.base_button.BaseButtonState
import com.strayalphaca.presentation.components.block.EditTextState
import com.strayalphaca.presentation.components.block.EditTextType
import com.strayalphaca.presentation.components.block.EditTextWithTitle
import com.strayalphaca.presentation.utils.collectAsEffect

@Composable
fun ChangePasswordScreen(
    viewModel : ChangePasswordViewModel,
    goToBack : () -> Unit = {}
) {
    val context = LocalContext.current

    val prevPassword by viewModel.prevPassword.collectAsState()
    val newPassword by viewModel.newPassword.collectAsState()
    val screenState by viewModel.state.collectAsState()

    viewModel.changePasswordSuccess.collectAsEffect{
        Toast.makeText(context, context.getString(R.string.success_change_password), Toast.LENGTH_SHORT).show()
        goToBack()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 12.dp)
    ) {

        Spacer(modifier = Modifier.height(48.dp))

        EditTextWithTitle(
            title = stringResource(id = R.string.prev_password),
            placeHolder = "",
            type = EditTextType.PASSWORD,
            value = prevPassword,
            onValueChange = viewModel::inputPrePassword,
            state = if(screenState.buttonEnable) EditTextState.ACTIVE else EditTextState.INACTIVE
        )

        Spacer(modifier = Modifier.height(36.dp))

        EditTextWithTitle(
            title = stringResource(id = R.string.new_password),
            placeHolder = "",
            type = EditTextType.PASSWORD,
            value = newPassword,
            onValueChange = viewModel::inputNewPassword,
            state = if(screenState.buttonEnable) EditTextState.ACTIVE else EditTextState.INACTIVE,
            isError = screenState.errorMessage.isNotEmpty(),
            errorText = screenState.errorMessage
        )

        Spacer(modifier = Modifier.weight(1f))

        BaseButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(48.dp),
            text = stringResource(id = R.string.change_password),
            onClick = {
                viewModel.tryChangePassword()
            },
            state = if(screenState.buttonEnable) BaseButtonState.ACTIVE else BaseButtonState.INACTIVE
        )
    }

}