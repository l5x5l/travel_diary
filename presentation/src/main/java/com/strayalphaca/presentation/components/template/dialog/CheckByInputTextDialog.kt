package com.strayalphaca.presentation.components.template.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.ui.theme.Gray4
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun CheckByInputTextDialog(
    title : String,
    targetText : String,
    onDismissRequest : () -> Unit = {},
    onSelectConfirm : () -> Unit = {},
    hintText : String = ""
) {
    var content by rememberSaveable{ mutableStateOf("") }

    TapeDialog(onDismissRequest = onDismissRequest) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            BasicTextField(
                modifier = Modifier.border(1.dp, MaterialTheme.colors.onBackground) ,
                value = content,
                onValueChange = {content = it},
                textStyle = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onSurface),
                singleLine = true,
                minLines = 1,
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)) {
                        if (content.isEmpty() && hintText.isNotEmpty()) {
                            Text(text = hintText, style = MaterialTheme.typography.body2, color = Gray4)
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier
                .align(Alignment.End)) {
                TextButton(
                    onClick = { onDismissRequest() }
                ) {
                    Text(text = stringResource(id = R.string.cancel), color = MaterialTheme.colors.onSurface)
                }

                TextButton(
                    onClick = {
                        onDismissRequest()
                        onSelectConfirm()
                    },
                    enabled = (content == targetText)
                ) {
                    Text(text = stringResource(id = R.string.check), color = MaterialTheme.colors.onSurface)
                }
            }
        }
    }
}

@Preview
@Composable
fun CheckByInputTextDialogPreview() {
    TravelDiaryTheme() {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)) {

            CheckByInputTextDialog(
                title = stringResource(id = R.string.withdrawal_check_dialog_title, "sample"),
                targetText = "sample",
                hintText = "sample text 를 입력해주세요."
            )
        }
    }
}