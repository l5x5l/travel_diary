package com.strayalphaca.presentation.components.block

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.ui.theme.Gray2

@Composable
fun LockScreenTextField(
    modifier : Modifier = Modifier,
    text : String,
    onTextChanged : (String) -> Unit
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChanged,
        decorationBox = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                text.forEachIndexed { index, c ->
                    LockScreenTextFieldCell(
                        text = c,
                        isFocused = index == text.lastIndex
                    )
                }
                repeat(4 - text.length) {
                    LockScreenTextFieldCell(
                        text = ' ', isFocused = false
                    )
                }

            }
        }
    )
}

@Composable
private fun LockScreenTextFieldCell(
    modifier : Modifier = Modifier,
    text : Char,
    isFocused : Boolean
) {
    val shape = remember { RoundedCornerShape(4.dp) }

    Box(
        modifier = modifier
            .size(width = 30.dp, height = 40.dp)
            .background(
                color = MaterialTheme.colors.surface,
                shape = shape
            )
            .run {
                if (isFocused) {
                    border(
                        width = 1.dp,
                        color = Gray2,
                        shape = shape
                    )
                } else {
                    this
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text.toString())
    }
}
