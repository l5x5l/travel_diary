package com.strayalphaca.presentation.screens.diary.detail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_icon_button.BaseIconButton
import com.strayalphaca.presentation.components.block.PolaroidView
import com.strayalphaca.presentation.components.block.SoundView
import com.strayalphaca.presentation.ui.theme.Gray2
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun DiaryDetailScreen() {
    val scrollState = rememberScrollState()

    Surface() {
        Column(modifier = Modifier.fillMaxSize()) {
            BaseIconButton(
                iconResourceId = R.drawable.ic_back,
                onClick = {}
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Gray2))

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(scrollState)
            ) {
                Text(text = "2023년 2월 10일")

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

                Text(
                    text = "오늘은 새로운 이빨친구가 입주했다.\u2028새 친구라 기분 좋지만, 지갑은 슬퍼져 이젠 그만 들어와도 괜찮을 것 같다.",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.body2
                )

                Spacer(modifier = Modifier.height(40.dp))

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
fun DiaryDetailScreenPreview() {
    TravelDiaryTheme() {
        DiaryDetailScreen()
    }
}