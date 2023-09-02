package com.strayalphaca.presentation.screens.settings.withdrawal

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
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun WithdrawalScreen(modifier : Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp)) {
        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(style = MaterialTheme.typography.body1, text = stringResource(id = R.string.current_login))
            Text(style = MaterialTheme.typography.body1, text = "")
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(style = MaterialTheme.typography.body1, text = stringResource(id = R.string.my_diaries))
            Text(style = MaterialTheme.typography.body1, text = stringResource(id = R.string.total_count_format, 10))
        }

        Spacer(modifier = Modifier.height(60.dp))

        Text(
            style = MaterialTheme.typography.body1,
            text = stringResource(id = R.string.caution)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            style = MaterialTheme.typography.body2,
            text = stringResource(id = R.string.withdrawal_caution_message)
        )

        Spacer(modifier = Modifier.weight(1f))

        BaseButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            text = stringResource(id = R.string.withdrawal),
            onClick = {

            }
        )

        Spacer(modifier = Modifier.height(32.dp))
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
fun WithdrawalScreenPreview() {
    TravelDiaryTheme {
        Surface {
            WithdrawalScreen()
        }
    }
}