package com.strayalphaca.presentation.screens.diary.write

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
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
import com.strayalphaca.presentation.components.atom.base_icon_button.BaseIconButton
import com.strayalphaca.presentation.components.atom.text_button.TextButton
import com.strayalphaca.presentation.components.block.PolaroidView
import com.strayalphaca.presentation.components.block.SoundView
import com.strayalphaca.presentation.ui.theme.Gray2
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun DiaryWriteScreen() {
    val scrollState = rememberScrollState()

    Surface {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BaseIconButton(
                    iconResourceId = R.drawable.ic_back,
                    onClick = {}
                )

                TextButton(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(id = R.string.register)
                )
            }

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gray2))

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(scrollState)
            ) {
                Text(text = "2023??? 2??? 10???")

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.weight(1f)) {
                        Text(text = stringResource(id = R.string.today_feeling), style = MaterialTheme.typography.body2)
                    }
                    Row(modifier = Modifier.weight(1f)) {
                        Text(text = stringResource(id = R.string.weather), style = MaterialTheme.typography.body2)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                PolaroidView()

                Spacer(modifier = Modifier.height(24.dp))

                BasicTextField(
                    value = "ttttttttt",
                    onValueChange = {},
                    modifier = Modifier.padding(horizontal = 16.dp),
                    textStyle = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onBackground),
                    decorationBox = { innerTextField ->
                        Column(modifier = Modifier.fillMaxWidth()) {
                            innerTextField()
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "150/300", modifier = Modifier.align(Alignment.End), style = MaterialTheme.typography.caption)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                SoundView()
            }
        }
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
fun DiaryWriteScreenPreview() {
    TravelDiaryTheme() {
        DiaryWriteScreen()
    }
}