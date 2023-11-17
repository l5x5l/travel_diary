package com.strayalphaca.presentation.screens.diary_list.component

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.strayalphaca.presentation.components.atom.base_button.BaseButtonState
import com.strayalphaca.presentation.components.atom.base_button.getButtonModifier
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun NextPageLoadFailView(
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.fail_next_page_load),
            style = MaterialTheme.typography.caption
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            modifier = Modifier
                .clickable { onClickRetry() }
                .then(Modifier.getButtonModifier(BaseButtonState.ACTIVE))
                .padding(8.dp),
            text = stringResource(id = R.string.retry_next_page_load),
            style = MaterialTheme.typography.button
        )

    }
}

@Preview(name="light mode")
@Preview(
    name="dark mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun NextPageLoadFailPreview() {
    TravelDiaryTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                NextPageLoadFailView(modifier = Modifier.fillMaxWidth().height(48.dp))
            }
        }
    }
}