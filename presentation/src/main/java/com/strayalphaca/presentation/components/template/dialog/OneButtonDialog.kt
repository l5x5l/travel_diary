package com.strayalphaca.presentation.components.template.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.components.atom.base_button.BaseButton
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun OneButtonDialog(
    title: String,
    mainText: String,
    buttonText: String,
    buttonClick: () -> Unit,
    onDismissRequest: () -> Unit
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
                text = mainText,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            BaseButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp),
                text = buttonText,
                onClick = buttonClick
            )
        }
    }
}

@Preview
@Composable
fun OneButtonDialogPreview() {
    TravelDiaryTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan)
        ) {
            OneButtonDialog(
                title = "기록 삭제",
                mainText = "정말 이 기록을 삭제하실건가요?",
                buttonText = "네",
                buttonClick = {},
                onDismissRequest = {}
            )
        }
    }
}