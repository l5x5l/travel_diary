package com.strayalphaca.presentation.components.atom.text_button

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.ui.theme.Gray4
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

enum class TextButtonState {
    ACTIVE, INACTIVE
}

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    text : String,
    state : TextButtonState = TextButtonState.ACTIVE,
    onClick : () -> Unit = {},
    textStyle : TextStyle = MaterialTheme.typography.button
) {
    Box(
        modifier
            .clickable(enabled = state != TextButtonState.INACTIVE) {
                onClick()
            },
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(vertical = 12.dp),
            style = textStyle,
            color = if (state == TextButtonState.ACTIVE) MaterialTheme.colors.onBackground else Gray4
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
fun BaseButtonPreview() {
    TravelDiaryTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                TextButton(modifier = Modifier.fillMaxWidth(), text = "TextButton")
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(modifier = Modifier.fillMaxWidth(), text = "TextButton", state = TextButtonState.INACTIVE)
            }
        }
    }
}