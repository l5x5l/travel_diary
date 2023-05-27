package com.strayalphaca.presentation.screens.diary.detail

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
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
import androidx.core.net.toUri
import com.strayalphaca.domain.diary.model.DiaryDetail
import com.strayalphaca.domain.diary.model.DiaryStatus
import com.strayalphaca.domain.diary.model.Feeling
import com.strayalphaca.domain.diary.model.File
import com.strayalphaca.domain.diary.model.FileType

@Composable
fun DiaryDetailContainer(
    viewModel: DiaryDetailViewModel = viewModel(),
    id : String,
    goBack: () -> Unit = {},
    goToVideo  : (Uri) -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val musicProgress by viewModel.musicProgress.collectAsState()

    DiaryDetailScreen(
        id = id,
        state = state,
        goBack = goBack,
        goToVideo = goToVideo,
        loadDiary = viewModel::tryLoadDetail,
        playMusic = viewModel::playMusic,
        pauseMusic = viewModel::pauseMusic,
        changeMusicProgress = viewModel::dragMusicProgressByUser,
        musicProgress = musicProgress,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiaryDetailScreen(
    id : String = "",
    state : DiaryDetailState = DiaryDetailState(),
    goBack: () -> Unit = {},
    goToVideo  : (Uri) -> Unit = {},
    loadDiary : (String) -> Unit = {},
    playMusic : () -> Unit = {},
    pauseMusic : () -> Unit = {},
    changeMusicProgress : (Float) -> Unit = {},
    musicProgress : Float = 0f
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(id) {
        loadDiary(id)
    }
    
    Surface {
        Column(modifier = Modifier.fillMaxSize()) {
            BaseIconButton(
                iconResourceId = R.drawable.ic_back,
                onClick = { goBack() }
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
                    Text(text = state.diaryDetail.date)

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                            Text(text = stringResource(id = R.string.today_feeling), style = MaterialTheme.typography.body2)
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.ic_feeling_angry),
                                contentDescription = null,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                            Text(text = stringResource(id = R.string.weather), style = MaterialTheme.typography.body2)
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.ic_weather_sunny),
                                contentDescription = null,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    if (state.diaryDetail.files.isNotEmpty()) {
                        HorizontalPager(pageCount = state.diaryDetail.files.size) {
                            PolaroidView(
                                fileUri = Uri.parse(state.diaryDetail.files[it].shortLink),
                                isVideo = state.diaryDetail.files[it].type == FileType.VIDEO,
                                onClick = goToVideo
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = state.diaryDetail.content,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.body2
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    // voice 파일 유무에 따라 변경 필요
                    state.diaryDetail.voiceFile?.let { file ->
                        SoundView(
                            file = file.shortLink.toUri(),
                            playing = state.musicPlaying,
                            play = playMusic,
                            pause = pauseMusic,
                            soundProgressChange = changeMusicProgress,
                            soundProgress = musicProgress
                        )
                    }
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
        DiaryDetailScreen(
            id = "1",
            state = DiaryDetailState(
                diaryDetail = DiaryDetail(
                    id = "1",
                    date = "2023/03/03",
                    weather = null,
                    feeling = Feeling.HAPPY,
                    content = "리펙토링 중, 뭐가 바뀌기만 하면 preview가 안된다. 미치겄네,",
                    files = listOf(File(id = "", type = FileType.VIDEO, shortLink = "", originalLink = "")),
                    createdAt = "2023/03/03",
                    updatedAt = "2023/03/03",
                    status = DiaryStatus.NORMAL,
                    voiceFile = File(id = "", type = FileType.VOICE, shortLink = "", originalLink = "")
                )
            )
        )
    }
}