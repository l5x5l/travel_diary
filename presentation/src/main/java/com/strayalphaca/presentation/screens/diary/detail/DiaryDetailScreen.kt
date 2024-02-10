package com.strayalphaca.presentation.screens.diary.detail

import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import com.strayalphaca.travel_diary.diary.model.DiaryDetail
import com.strayalphaca.travel_diary.diary.model.Feeling
import com.strayalphaca.travel_diary.diary.model.File
import com.strayalphaca.travel_diary.diary.model.FileType
import com.strayalphaca.presentation.components.template.dialog.TwoButtonDialog
import com.strayalphaca.presentation.components.template.error_view.ErrorView
import com.strayalpaca.travel_diary.core.domain.model.DiaryDate
import com.strayalphaca.presentation.screens.diary.component.block.LocationView
import com.strayalphaca.presentation.screens.diary.component.block.WeatherFeelingSelectView
import com.strayalphaca.presentation.screens.diary.component.template.DiaryViewTemplate
import com.strayalphaca.presentation.utils.collectAsEffect
import com.strayalphaca.presentation.utils.thenIf
import com.strayalphaca.travel_diary.diary.model.Weather
import kotlin.math.absoluteValue

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
    val context = LocalContext.current

    LaunchedEffect(needRefresh) {
        if (needRefresh)
            viewModel.tryRefresh()
    }

    viewModel.goBackNavigationEvent.collectAsEffect { deleteSuccess ->
        if (deleteSuccess)
            goBackWithDeleteSuccess()
    }

    viewModel.toastMessage.collectAsEffect { message ->
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
                    onClick = { goBack() },
                    enabled = !state.deleteLoading
                )

                if (state.diaryDetail != null) {
                    Box {
                        BaseIconButton(
                            iconResourceId = R.drawable.ic_more,
                            onClick = { dropdownExpanded = !dropdownExpanded },
                            enabled = !state.deleteLoading
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

            Spacer(modifier = Modifier.height(8.dp))

            if (state.diaryDetail != null) {
                DiaryViewTemplate(
                    modifier = Modifier.weight(1f),
                    hideMediaArea = state.diaryDetail.files.isEmpty() && state.diaryDetail.voiceFile == null,
                    dateTextView = {
                        Text(text = state.diaryDetail.date.toString())
                    },
                    locationView = {
                        LocationView(
                            cityName = state.diaryDetail.cityName,
                            onClickGpsIcon = null
                        )
                    },
                    weatherFeelingView = {
                        WeatherFeelingSelectView(
                            feeling = state.diaryDetail.feeling,
                            weather = state.diaryDetail.weather,
                            currentShowSelectView = null
                        )
                    },
                    polaroidHorizontalPager = { isTabletMode ->
                        val pagerState = rememberPagerState()
                        HorizontalPager(
                            pageCount = state.diaryDetail.files.size,
                            state = pagerState
                        ) { index ->
                            PolaroidView(
                                modifier = Modifier.thenIf(isTabletMode) {
                                    graphicsLayer {
                                        val pageOffset = pagerState.run { currentPage - index + currentPageOffsetFraction }
                                        if (pageOffset < 0) {
                                            translationY = -pageOffset * size.height * 0.3f
                                            translationX = pageOffset * size.width
                                        }
                                        alpha = 1 - pageOffset.absoluteValue
                                    }
                                },
                                fileUri = Uri.parse(state.diaryDetail.files[index].fileLink),
                                thumbnailUri = Uri.parse(state.diaryDetail.files[index].getThumbnail()),
                                isVideo = state.diaryDetail.files[index].type == FileType.VIDEO,
                                onClick = { uri ->
                                    if (state.deleteLoading) return@PolaroidView
                                    goToVideo(uri)
                                },
                                dateString = state.diaryDetail.date.toString(),
                                positionString = "${index + 1}/${state.diaryDetail.files.size}"
                            )
                        }
                    },
                    textField = {
                        if (state.diaryDetail.files.isEmpty()) {
                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth(1f),
                                thickness = 1.dp,
                                color = MaterialTheme.colors.onSurface
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = state.diaryDetail.content,
                            modifier = Modifier.padding(horizontal = 8.dp),
                            style = MaterialTheme.typography.body2
                        )
                    },
                    soundView = {
                        state.diaryDetail.voiceFile?.let { file ->
                            SoundView(
                                file = file.fileLink.toUri(),
                                playing = state.musicPlaying,
                                play = playMusic,
                                pause = pauseMusic,
                                soundProgressChange = changeMusicProgress,
                                soundProgress = musicProgress,
                                isError = state.musicError
                            )
                        }
                    }
                )
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
                    weather = Weather.SUNNY,
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