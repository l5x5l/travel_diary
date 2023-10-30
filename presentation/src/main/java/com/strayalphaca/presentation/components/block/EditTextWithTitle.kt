package com.strayalphaca.presentation.components.block

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strayalphaca.presentation.ui.theme.Gray4

enum class EditTextState {
    ACTIVE, INACTIVE
}

enum class EditTextType {
    EMAIL, PASSWORD, NORMAL
}

@Composable
fun EditTextWithTitle(
    modifier: Modifier = Modifier,
    title : String,
    placeHolder : String,
    value : String,
    errorText : String = "",
    isError : Boolean = false,
    state : EditTextState = EditTextState.ACTIVE,
    type : EditTextType = EditTextType.NORMAL,
    onValueChange : (newValue : String) -> Unit = {}
) {
    val textVisible by rememberSaveable { mutableStateOf(type != EditTextType.PASSWORD) }
    val keyboardType : KeyboardType = when(type) {
        EditTextType.EMAIL -> KeyboardType.Email
        else -> KeyboardType.Text
    }

    Column(modifier = modifier) {
        Text(text = title, fontSize = 14.sp)
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = (state == EditTextState.ACTIVE),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            textStyle = TextStyle(fontSize = 14.sp, color = if (state == EditTextState.ACTIVE) MaterialTheme.colors.onBackground else Gray4),
            visualTransformation = if (textVisible) VisualTransformation.None else PasswordVisualTransformation(),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(text = placeHolder, color = Gray4, fontSize = 14.sp)
                }
                innerTextField()
            },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            cursorBrush = SolidColor(MaterialTheme.colors.onSurface)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colors.onBackground)
        )
        if (isError) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorText, style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.error))
        }
    }
}

@Preview(name="light mode", widthDp = 360)
@Preview(
    name="dark mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 360
)
@Composable
fun EditTextWithTitlePreview() {
    TravelDiaryTheme {
        Surface {
            var text by remember { mutableStateOf("email") }

            Column(Modifier.padding(8.dp)) {
                EditTextWithTitle(modifier = Modifier.fillMaxWidth(), title = "이메일", placeHolder = "이메일을 입력해주세요", state = EditTextState.ACTIVE, value = text, onValueChange = { text = it } )
                Spacer(modifier = Modifier.height(16.dp))
                EditTextWithTitle(modifier = Modifier.fillMaxWidth(), title = "이메일", placeHolder = "이메일을 입력해주세요", state = EditTextState.INACTIVE, value = text, onValueChange = { text = it } )
                Spacer(modifier = Modifier.height(16.dp))
                EditTextWithTitle(modifier = Modifier.fillMaxWidth(), title = "이메일", placeHolder = "이메일을 입력해주세요", state = EditTextState.INACTIVE, value = "", onValueChange = { text = it } )
                Spacer(modifier = Modifier.height(16.dp))
                EditTextWithTitle(modifier = Modifier.fillMaxWidth(), title = "비밀번호", placeHolder = "비밀번호를 입력해주세요", state = EditTextState.ACTIVE, value = "password", onValueChange = { text = it }, type = EditTextType.PASSWORD )
            }
        }
    }
}