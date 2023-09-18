package com.strayalphaca.presentation.components.template.error_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.text_button.TextButton
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun ErrorView(
    modifier : Modifier = Modifier,
    message : String = stringResource(id = R.string.error_message_unknown),
    callback : (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(80.dp),
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = "error image",
            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onSurface)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )

        if (callback != null) {
            Spacer(modifier = Modifier.height(32.dp))

            TextButton(
                modifier = Modifier.padding(16.dp),
                text = stringResource(id = R.string.retry),
                onClick = callback
            )
        }

    }
}

@Preview
@Composable
fun ErrorViewPreview() {
    TravelDiaryTheme() {
        ErrorView(
            modifier = Modifier.fillMaxSize(),
            message = stringResource(id = R.string.error_message_removed)
        ) {}
    }
}