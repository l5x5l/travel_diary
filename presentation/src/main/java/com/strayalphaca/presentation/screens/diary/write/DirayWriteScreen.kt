package com.strayalphaca.presentation.screens.diary.write

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.strayalphaca.presentation.components.template.error_view.ErrorView
import com.strayalphaca.travel_diary.diary.model.Feeling
import com.strayalphaca.travel_diary.diary.model.Weather
import com.strayalphaca.presentation.screens.diary.component.ContentIconImage
import com.strayalphaca.presentation.screens.diary.component.ContentSelectView
import com.strayalphaca.presentation.screens.diary.model.CurrentShowSelectView
import com.strayalphaca.presentation.screens.diary.util.getFeelingIconId
import com.strayalphaca.presentation.screens.diary.util.getWeatherIconId
import com.strayalphaca.presentation.utils.GetMediaActivityResultContract
import com.strayalphaca.presentation.utils.checkUriIsVideo
import com.strayalphaca.presentation.utils.isPhotoPickerAvailable

@Composable
fun DiaryWriteContainer(
    id: String?,
    viewModel: DiaryWriteViewModel = viewModel(),
    goBack: () -> Unit = {},
    goToVideo  : (Uri) -> Unit = {},
    goBackWithModifySuccessResult : () -> Unit = {}
) {
    val content by viewModel.writingContent.collectAsState()
    val state by viewModel.state.collectAsState()
    val musicProgress by viewModel.musicProgress.collectAsState()
    val goBackNavigationEvent by viewModel.goBackNavigationEvent.collectAsState(initial = false)

    LaunchedEffect(goBackNavigationEvent) {
        if (goBackNavigationEvent)
            goBackWithModifySuccessResult()
    }

    DiaryWriteScreen(
        id = id,
        goBack = goBack,
        goToVideo = goToVideo,
        content= content,
        changeContent= viewModel::inputContent,
        state= state,
        musicProgress= musicProgress,
        changeImageFile= viewModel::inputImageFile,
        changeVoiceFile = viewModel::inputVoiceFile,
        removeVoiceFile= viewModel::removeVoiceFile,
        loadDiary= viewModel::tryLoadDetail,
        playMusic = viewModel::playMusic,
        pauseMusic = viewModel::pauseMusic,
        releaseMusicPlayer = viewModel::releaseMusicPlayer,
        changeMusicProgress = viewModel::dragMusicProgressByUser,
        showSelectView = viewModel::showSelectView,
        setWeather = viewModel::setWeather,
        setFeeling = viewModel::setFeeling,
        uploadDiary = viewModel::uploadDiary
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiaryWriteScreen(
    id: String?,
    goBack: () -> Unit = {},
    goToVideo : (Uri) -> Unit = {},
    content: String = "",
    changeContent: (String) -> Unit = {},
    state: DiaryWriteState = DiaryWriteState(),
    musicProgress: Float = 0f,
    changeImageFile: (List<Uri>) -> Unit = {},
    changeVoiceFile: (Uri, Boolean) -> Unit = { _, _ -> },
    removeVoiceFile: () -> Unit = {},
    loadDiary: (String) -> Unit = {},
    playMusic: () -> Unit = {},
    pauseMusic: () -> Unit = {},
    releaseMusicPlayer: () -> Unit = {},
    changeMusicProgress: (Float) -> Unit = {},
    showSelectView: (CurrentShowSelectView) -> Unit = {},
    setWeather: (Weather) -> Unit = {},
    setFeeling: (Feeling) -> Unit = {},
    uploadDiary : () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current


    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(3)
    ) { uriList ->
        changeImageFile(uriList)
    }

    val prevPhotoPickerLauncher = rememberLauncherForActivityResult(contract = GetMediaActivityResultContract()) { uriList ->
        changeImageFile(uriList)
    }

    val mp3PickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val isNewDiary = (id == null || id == "null")
            changeVoiceFile(it, isNewDiary)
        }
    }

    LaunchedEffect(id) {
        if (id != null && id != "null") loadDiary(id)
    }

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                pauseMusic()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            releaseMusicPlayer()
        }
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
                    onClick = goBack
                )

                TextButton(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(id = R.string.register),
                    onClick = uploadDiary
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Gray2)
            )

            if (!state.showInitLoading && !state.showLoadingError) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp)
                        .verticalScroll(scrollState)
                ) {
                    Text(text = "")

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.location),
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(end = 10.dp)
                        )

                        Text(
                            text = stringResource(id = R.string.placeholder_location),
                            style = MaterialTheme.typography.body2,
                            color = Gray2,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1f)
                                .padding(end = 10.dp)
                        )

                        ContentIconImage(
                            iconId = R.drawable.ic_location,
                            descriptionText = state.feeling.name,
                            onClick = {

                            }
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        ContentIconImage(
                            iconId = R.drawable.ic_gps,
                            descriptionText = state.feeling.name,
                            onClick = {

                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                        ) {
                            Text(
                                text = stringResource(id = R.string.today_feeling),
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(end = 10.dp)
                            )
                            ContentIconImage(
                                iconId = getFeelingIconId(state.feeling),
                                descriptionText = state.feeling.name,
                                onClick = {
                                    showSelectView(CurrentShowSelectView.FEELING)
                                }
                            )
                        }
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                        ) {
                            Text(
                                text = stringResource(id = R.string.weather),
                                style = MaterialTheme.typography.body2,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(end = 10.dp)
                            )
                            ContentIconImage(
                                iconId = state.weather?.let { getWeatherIconId(it) }
                                    ?: R.drawable.ic_weather_sunny,
                                descriptionText = state.weather?.toString(),
                                onClick = {
                                    showSelectView(CurrentShowSelectView.WEATHER)
                                }
                            )
                        }
                    }

                    AnimatedVisibility(state.currentShowSelectView == CurrentShowSelectView.WEATHER) {
                        ContentSelectView(contentList = Weather.values().toList()) {
                            ContentIconImage(
                                iconId = getWeatherIconId(it),
                                descriptionText = it.name,
                                onClick = {
                                    setWeather(it)
                                }
                            )
                        }
                    }

                    AnimatedVisibility(state.currentShowSelectView == CurrentShowSelectView.FEELING) {
                        ContentSelectView(contentList = Feeling.values().toList()) {
                            ContentIconImage(
                                iconId = getFeelingIconId(it),
                                descriptionText = it.name,
                                onClick = {
                                    setFeeling(it)
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    if (state.imageFiles.isNotEmpty()) {
                        HorizontalPager(pageCount = state.imageFiles.size) {
                            val isVideo = checkUriIsVideo(state.imageFiles[it], context)
                            PolaroidView(fileUri = state.imageFiles[it], isVideo = isVideo, onClick = goToVideo)
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    BasicTextField(
                        value = content,
                        onValueChange = changeContent,
                        modifier = Modifier
                            .border(1.dp, MaterialTheme.colors.onBackground)
                            .defaultMinSize(minHeight = 250.dp),
                        textStyle = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onBackground),
                        decorationBox = { innerTextField ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp), verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                innerTextField()

                                Text(
                                    text = "${content.length}/300",
                                    modifier = Modifier.align(Alignment.End),
                                    style = MaterialTheme.typography.caption
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    state.voiceFile?.let { uri ->
                        SoundView(
                            file = uri,
                            playing = state.musicPlaying,
                            play = playMusic,
                            pause = pauseMusic,
                            remove = removeVoiceFile,
                            soundProgressChange = changeMusicProgress,
                            soundProgress = musicProgress
                        )
                    }
                }
            } else if (state.showInitLoading) {
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
            } else {
                ErrorView(modifier = Modifier.fillMaxSize())
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Gray2)
                )

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BaseIconButton(
                        iconResourceId = R.drawable.ic_image,
                        onClick = {
                            if (isPhotoPickerAvailable()) {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageAndVideo
                                    )
                                )
                            } else {
                                prevPhotoPickerLauncher.launch("*/*")
                            }
                        }
                    )

                    BaseIconButton(
                        iconResourceId = R.drawable.ic_music,
                        onClick = {
                            mp3PickerLauncher.launch("audio/*")
                        }
                    )

                }
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
    TravelDiaryTheme {
        DiaryWriteScreen("null")
    }
}