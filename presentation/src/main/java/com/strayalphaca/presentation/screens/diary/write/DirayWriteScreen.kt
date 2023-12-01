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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.strayalphaca.presentation.components.atom.text_button.TextButtonState
import com.strayalphaca.presentation.components.block.EmptyPolaroidView
import com.strayalphaca.presentation.components.template.dialog.DiaryLocationPickerDialog
import com.strayalphaca.presentation.components.template.error_view.ErrorView
import com.strayalphaca.travel_diary.diary.model.Feeling
import com.strayalphaca.travel_diary.diary.model.Weather
import com.strayalphaca.presentation.screens.diary.component.ContentIconImage
import com.strayalphaca.presentation.screens.diary.component.ContentSelectView
import com.strayalphaca.presentation.screens.diary.model.CurrentShowSelectView
import com.strayalphaca.presentation.screens.diary.util.getFeelingIconId
import com.strayalphaca.presentation.screens.diary.util.getWeatherIconId
import com.strayalphaca.presentation.utils.GetMediaActivityResultContract
import com.strayalphaca.presentation.utils.collectAsEffect
import com.strayalphaca.presentation.utils.isPhotoPickerAvailable
import com.strayalphaca.travel_diary.domain.file.model.FileType
import kotlin.math.min

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

    viewModel.goBackNavigationEvent.collectAsEffect { goBackNavigationEvent ->
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
        addImageFile= viewModel::inputImageFile,
        deleteImageFile = viewModel::deleteImageFile,
        changeVoiceFile = viewModel::inputVoiceFile,
        removeVoiceFile= viewModel::removeVoiceFile,
        loadDiary= viewModel::tryLoadDetail,
        playMusic = viewModel::playMusic,
        pauseMusic = viewModel::pauseMusic,
        changeMusicProgress = viewModel::dragMusicProgressByUser,
        showSelectView = viewModel::showSelectView,
        setWeather = viewModel::setWeather,
        setFeeling = viewModel::setFeeling,
        uploadDiary = viewModel::uploadDiary,
        showLocationPickerDialog = viewModel::showLocationPickerDialog,
        hideLocationPickerDialog = viewModel::hideLocationPickerDialog,
        selectCityById = viewModel::selectCityById
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
    addImageFile: (List<Uri>) -> Unit = {},
    deleteImageFile : (Uri) -> Unit = {},
    changeVoiceFile: (Uri, Boolean) -> Unit = { _, _ -> },
    removeVoiceFile: () -> Unit = {},
    loadDiary: (String) -> Unit = {},
    playMusic: () -> Unit = {},
    pauseMusic: () -> Unit = {},
    changeMusicProgress: (Float) -> Unit = {},
    showSelectView: (CurrentShowSelectView) -> Unit = {},
    setWeather: (Weather) -> Unit = {},
    setFeeling: (Feeling) -> Unit = {},
    uploadDiary : () -> Unit = {},
    showLocationPickerDialog : () -> Unit = {},
    hideLocationPickerDialog : () -> Unit = {},
    selectCityById : (Int?) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val lifecycleOwner = LocalLifecycleOwner.current

    val photoPickerLauncher = if (3 - state.imageFiles.size > 1) {
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(3 - state.imageFiles.size),
            onResult = { uriList -> addImageFile(uriList) }
        )
    } else {
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri -> uri?.let{ addImageFile(listOf(uri)) } }
        )
    }

    val prevPhotoPickerLauncher = rememberLauncherForActivityResult(contract = GetMediaActivityResultContract()) { uriList ->
        addImageFile(uriList)
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
        }
    }

    if (state.showLocationPickerDialog) {
        DiaryLocationPickerDialog(
            title = stringResource(id = R.string.select_location),
            message = stringResource(id = R.string.select_location_message),
            onDismissRequest = hideLocationPickerDialog,
            onCitySelect = selectCityById,
            prevSelectedCityId = state.cityId
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
                    onClick = goBack
                )

                TextButton(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(id = R.string.register),
                    state = if (content.isEmpty()) TextButtonState.INACTIVE else TextButtonState.ACTIVE,
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
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = state.diaryDate.toString())

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
                            text = state.cityName ?: stringResource(id = R.string.placeholder_location),
                            style = MaterialTheme.typography.body2,
                            color = if (state.cityName != null) MaterialTheme.colors.onSurface else Gray2,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1f)
                                .padding(end = 10.dp)
                        )

                        ContentIconImage(
                            iconId = R.drawable.ic_gps,
                            descriptionText = stringResource(id = R.string.select_location),
                            onClick = showLocationPickerDialog
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
                                iconId = getWeatherIconId(state.weather),
                                descriptionText = state.weather.toString(),
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

                    HorizontalPager(pageCount = min(state.imageFiles.size + 1, 3)) { index ->
                        val target = state.imageFiles.getOrNull(index)
                        if (target != null) {
                            val isVideo = state.imageFiles[index].fileType == FileType.Video
                            PolaroidView(
                                fileUri = state.imageFiles[index].uri,
                                thumbnailUri = state.imageFiles[index].getThumbnailUriOrFileUri(),
                                isVideo = isVideo,
                                onClick = goToVideo,
                                onDeleteClick = deleteImageFile,
                                dateString = state.diaryDate.toString(),
                                positionString = "${index + 1}/${state.imageFiles.size}"
                            )
                        } else {
                            EmptyPolaroidView(
                                onClick = {
                                    if (isPhotoPickerAvailable()) {
                                        photoPickerLauncher.launch(
                                            PickVisualMediaRequest(
                                                ActivityResultContracts.PickVisualMedia.ImageOnly
                                            )
                                        )
                                    } else {
                                        prevPhotoPickerLauncher.launch("*/*")
                                    }
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))


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

                    state.voiceFile?.let { mediaFileInDiary ->
                        SoundView(
                            file = mediaFileInDiary.uri,
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