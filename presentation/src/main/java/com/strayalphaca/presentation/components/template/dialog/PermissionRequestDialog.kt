package com.strayalphaca.presentation.components.template.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_button.BaseButton
import com.strayalphaca.presentation.components.atom.text_button.TextButton

@Composable
fun PermissionRequestDialog(
    title : String,
    message : String,
    isPermanentlyDeclined : Boolean,
    onDismissRequest : () -> Unit = {},
    goToSettingClick : () -> Unit = {}
) {
    TapeDialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 34.dp, vertical = 24.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isPermanentlyDeclined) {
                TextButton(
                    text = stringResource(id = R.string.go_to_app_setting),
                    onClick = {
                        goToSettingClick()
                        onDismissRequest()
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            BaseButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.check),
                onClick = onDismissRequest
            )
        }
    }
}