package com.strayalphaca.presentation.screens.diary.detail

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.core.net.toUri
import com.strayalphaca.travel_diary.diary.model.DiaryDetail
import com.strayalphaca.travel_diary.diary.model.Feeling
import com.strayalphaca.travel_diary.diary.model.File
import com.strayalphaca.travel_diary.diary.model.FileType
import com.strayalphaca.presentation.components.template.dialog.TwoButtonDialog
import com.strayalphaca.presentation.components.template.error_view.ErrorView
import com.strayalphaca.domain.all.DiaryDate
import com.strayalphaca.presentation.screens.diary.component.ContentIconImage
import com.strayalphaca.presentation.screens.diary.util.getFeelingIconId
import com.strayalphaca.presentation.screens.diary.util.getWeatherIconId

@Composable
fun DiaryDetailContainer(
    viewModel: DiaryDetailViewModel = viewModel(),
    id : String,
    goBack: () -> Unit = {},
    goBackWithDeleteSuccess : () -> Unit = {},
    goToVideo  : (Uri) -> Unit = {},
    goToDiaryModify : (String?) -> Unit = {},
    needRefresh : Boolean = false
) {
    val state by viewModel.state.collectAsState()
    val musicProgress by viewModel.musicProgress.collectAsState()
    val deleteSuccess by viewModel.goBackNavigationEvent.collectAsState(initial = false)

    LaunchedEffect(needRefresh) {
        if (needRefresh)
            viewModel.tryRefresh()
    }

    LaunchedEffect(deleteSuccess) {
        if (deleteSuccess)
            goBackWithDeleteSuccess()
    }

    BackHandler(enabled = !state.showDeleteDialog) {
        goBack()
    }

    DiaryDetailScreen(
        id = id,
        state = state,
        goBack = goBack,
        goToVideo = goToVideo,
        goToDiaryModify = goToDiaryModify,
        loadDiary = viewModel::tryLoadDetail,
        deleteDiary = viewModel::tryDeleteDiary,
        playMusic = viewModel::playMusic,
        pauseMusic = viewModel::pauseMusic,
        changeMusicProgress = viewModel::dragMusicProgressByUser,
        musicProgress = musicProgress,
        showDeleteDialog = viewModel::showDeleteDialog,
        hideDeleteDialog = viewModel::hideDeleteDialog
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiaryDetailScreen(
    id : String = "",
    state : DiaryDetailState = DiaryDetailState(),
    goBack: () -> Unit = {},
    goToVideo  : (Uri) -> Unit = {},
    goToDiaryModify : (String?) -> Unit = {},
    loadDiary : (String) -> Unit = {},
    deleteDiary : () -> Unit = {},
    playMusic : () -> Unit = {},
    pauseMusic : () -> Unit = {},
    changeMusicProgress : (Float) -> Unit = {},
    musicProgress : Float = 0f,
    showDeleteDialog : () -> Unit = {},
    hideDeleteDialog : () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    var dropdownExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        loadDiary(id)
    }

    if (state.showDeleteDialog) {
        TwoButtonDialog(
            title = stringResource(id = R.string.remove_diary_title),
            mainText = stringResource(id = R.string.remove_diary_text),
            leftButtonText = stringResource(id = R.string.no),
            leftButtonClick = hideDeleteDialog,
            rightButtonText = stringResource(id = R.string.yes),
            rightButtonClick = deleteDiary,
            onDismissRequest = hideDeleteDialog
        )
    }

    Surface {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BaseIconButton(
                    iconResourceId = R.drawable.ic_back,
                    onClick = {
                        if (state.deleteLoading) return@BaseIconButton
                        goBack()
                    }
                )

                if (state.diaryDetail != null) {
                    Box {
                        BaseIconButton(
                            iconResourceId = R.drawable.ic_more,
                            onClick = {
                                if (state.deleteLoading) return@BaseIconButton
                                dropdownExpanded = !dropdownExpanded
                            }
                        )

                        DropdownMenu(
                            expanded = dropdownExpanded,
                            onDismissRequest = { dropdownExpanded = false}
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    dropdownExpanded = false
                                    goToDiaryModify(id)
                                }
                            ) {
                                Text(text = stringResource(id = R.string.modify), style = MaterialTheme.typography.body2)
                            }

                            DropdownMenuItem(
                                onClick = {
                                    dropdownExpanded = false
                                    showDeleteDialog()
                                }
                            ) {
                                Text(text = stringResource(id = R.string.delete), style = MaterialTheme.typography.body2)
                            }
                        }
                    }
                }

            }

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
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = state.diaryDetail.date.toString())

                    Spacer(modifier = Modifier.height(16.dp))

                    if (state.diaryDetail.cityName != null) {
                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = stringResource(id = R.string.location),
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(end = 10.dp)
                            )

                            Text(
                                text = state.diaryDetail.cityName ?: stringResource(id = R.string.placeholder_location),
                                style = MaterialTheme.typography.body2,
                                color = MaterialTheme.colors.onSurface,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .weight(1f)
                                    .padding(end = 10.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                            Text(text = stringResource(id = R.string.today_feeling), style = MaterialTheme.typography.body2)
                            Spacer(modifier = Modifier.width(10.dp))
                            ContentIconImage(
                                iconId = getFeelingIconId(state.diaryDetail.feeling),
                                descriptionText = state.diaryDetail.feeling.name
                            )
                        }
                        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                            Text(text = stringResource(id = R.string.weather), style = MaterialTheme.typography.body2)
                            Spacer(modifier = Modifier.width(10.dp))
                            ContentIconImage(
                                iconId = state.diaryDetail.weather?.let { getWeatherIconId(it) }
                                    ?: R.drawable.ic_weather_sunny,
                                descriptionText = state.diaryDetail.weather?.toString()
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    if (state.diaryDetail.files.isNotEmpty()) {
                        HorizontalPager(pageCount = state.diaryDetail.files.size) {
                            PolaroidView(
                                fileUri = Uri.parse(state.diaryDetail.files[it].fileLink),
                                thumbnailUri = Uri.parse(state.diaryDetail.files[it].getThumbnail()),
                                isVideo = state.diaryDetail.files[it].type == FileType.VIDEO,
                                onClick = { uri ->
                                    if (state.deleteLoading) return@PolaroidView
                                    goToVideo(uri)
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    Text(
                        text = state.diaryDetail.content,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.body2
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    // voice 파일 유무에 따라 변경 필요
                    state.diaryDetail.voiceFile?.let { file ->
                        SoundView(
                            file = file.fileLink.toUri(),
                            playing = state.musicPlaying,
                            play = playMusic,
                            pause = pauseMusic,
                            soundProgressChange = changeMusicProgress,
                            soundProgress = musicProgress
                        )
                    }
                }
            } else if (!state.showError) { // 데이터 로딩중
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier.size(40.dp),
                        strokeWidth = 4.dp
                    )
                }
            } else { // 데이터 로딩 실패
                ErrorView(modifier = Modifier.fillMaxSize())
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
    TravelDiaryTheme {
        DiaryDetailScreen(
            id = "1",
            state = DiaryDetailState(
                diaryDetail = DiaryDetail(
                    id = "1",
                    date = DiaryDate.getInstanceFromCalendar(),
                    weather = null,
                    feeling = Feeling.HAPPY,
                    content = "리펙토링 중, 뭐가 바뀌기만 하면 preview가 안된다. 미치겄네,",
                    files = listOf(File(id = "", type = FileType.VIDEO, fileLink = "")),
                    createdAt = "2023/03/03",
                    voiceFile = File(id = "", type = FileType.VOICE, fileLink = "")
                )
            )
        )
    }
}