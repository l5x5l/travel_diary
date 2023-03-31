package com.strayalphaca.presentation.screens.diary.detail

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource

@Composable
fun DiaryDetailScreen(
    viewModel : DiaryDetailViewModel = viewModel(),
    id : String
) {
    val scrollState = rememberScrollState()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(id) {
        viewModel.tryLoadDetail(id)
    }
    
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

            if (state.diaryDetail != null) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(scrollState)
                ) {
                    Text(text = state.diaryDetail!!.createdAt)

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                            Text(text = stringResource(id = R.string.today_feeling), style = MaterialTheme.typography.body2)
                            Spacer(modifier = Modifier.width(6.dp))
                            Image(
                                painter = painterResource(id = R.drawable.ic_feeling_angry),
                                contentDescription = null,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                            Text(text = stringResource(id = R.string.weather), style = MaterialTheme.typography.body2)
                            Spacer(modifier = Modifier.width(6.dp))
                            Image(
                                painter = painterResource(id = R.drawable.ic_weather_sunny),
                                contentDescription = null,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    if (state.diaryDetail!!.files.isNotEmpty()) {
                        PolaroidView()
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = state.diaryDetail!!.content,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.body2
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    // voice 파일 유무에 따라 변경 필요
                    // SoundView()
                }
            } else {
                // todo empty View 및 에러 view 생성
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
        DiaryDetailScreen(id = "1")
    }
}