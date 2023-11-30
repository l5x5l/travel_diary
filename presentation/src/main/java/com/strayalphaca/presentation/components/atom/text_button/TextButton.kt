package com.strayalphaca.presentation.components.atom.text_button

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
    textStyle : TextStyle = MaterialTheme.typography.button,
    interactionSource : MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    Box(
        modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = state != TextButtonState.INACTIVE,
            ) {
                onClick()
            },
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(vertical = 12.dp),
            style = textStyle,
            color = if (state == TextButtonState.ACTIVE && !isPressed) {
                MaterialTheme.colors.onBackground
            } else {
                Gray4
            }
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