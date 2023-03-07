package com.strayalphaca.presentation.components.atom.base_button


import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

enum class BaseButtonState {
    ACTIVE, INACTIVE, SELECTED
}

@Composable
fun BaseButton(
    modifier : Modifier = Modifier,
    text : String,
    onClick : () -> Unit,
    state : BaseButtonState = BaseButtonState.ACTIVE,
    textStyle : TextStyle = MaterialTheme.typography.button
) {
    Box(
        modifier
            .then(Modifier.getButtonModifier(state))
            .clickable(enabled = state != BaseButtonState.INACTIVE) {
                onClick()
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = textStyle,
            modifier = Modifier.padding(vertical = 12.dp),
            color = getButtonTextColor(baseButtonState = state)
        )
    }
}

@Preview(name="light mode")
@Preview(
    name="dark mode",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun BaseButtonPreview() {
    TravelDiaryTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                BaseButton(modifier = Modifier.fillMaxWidth(), text = "Button", onClick = {})
                Spacer(modifier = Modifier.height(8.dp))
                BaseButton(modifier = Modifier.fillMaxWidth(), text = "Button", onClick = {}, state = BaseButtonState.INACTIVE)
                Spacer(modifier = Modifier.height(8.dp))
                BaseButton(modifier = Modifier.fillMaxWidth(), text = "Button", onClick = {}, state = BaseButtonState.SELECTED)
            }
        }
    }
}