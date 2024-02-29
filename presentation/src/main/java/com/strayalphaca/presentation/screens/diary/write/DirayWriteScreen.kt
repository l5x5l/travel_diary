package com.strayalphaca.presentation.screens.diary.write

import android.content.res.Configuration
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
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
import com.strayalphaca.presentation.components.block.SoundView
import com.strayalphaca.presentation.ui.theme.Gray2
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.strayalphaca.presentation.components.atom.text_button.TextButtonState
import com.strayalphaca.presentation.components.block.EmptySoundView
import com.strayalphaca.presentation.components.template.dialog.DiaryLocationPickerDialog
import com.strayalphaca.presentation.components.template.dialog.PermissionRequestDialog
import com.strayalphaca.presentation.components.template.error_view.ErrorView
import com.strayalphaca.presentation.screens.diary.component.template.DiaryViewTemplate
import com.strayalphaca.travel_diary.diary.model.Feeling
import com.strayalphaca.travel_diary.diary.model.Weather
import com.strayalphaca.presentation.screens.diary.model.CurrentShowSelectView
import com.strayalphaca.presentation.screens.diary.component.block.LocationView
import com.strayalphaca.presentation.screens.diary.component.block.PolaroidHorizontalPager
import com.strayalphaca.presentation.screens.diary.component.block.WeatherFeelingSelectView
import com.strayalphaca.presentation.utils.GetMediaActivityResultContract
import com.strayalphaca.presentation.utils.WRITE_EXTERNAL_STORAGE_28
import com.strayalphaca.presentation.utils.collectAsEffect
import com.strayalphaca.presentation.utils.findActivity
import com.strayalphaca.presentation.utils.isPhotoPickerAvailable
import com.strayalphaca.presentation.utils.openAppSettings
import com.strayalphaca.presentation.utils.rememberSinglePermissionRequestLauncher

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
    val context = LocalContext.current
    val requestPermissionSettingAction by viewModel.requestPermissionSettingAction.collectAsState()

    viewModel.goBackNavigationEvent.collectAsEffect { goBackNavigationEvent ->
        if (goBackNavigationEvent)
            goBackWithModifySuccessResult()
    }

    viewModel.toastMessage.collectAsEffect { message ->
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    requestPermissionSettingAction?.let { permission ->
        PermissionRequestDialog(
            title = stringResource(R.string.deny_permission),
            message = stringResource(id = R.string.permission_description_write_external_storage_under_28),
            isPermanentlyDeclined = context.findActivity()?.let {
                !it.shouldShowRequestPermissionRationale(permission)
            } ?: true,
            onDismissRequest = viewModel::dismissPermissionRequestDialog,
            goToSettingClick = { context.findActivity()?.openAppSettings(permission) }
        )
    }

    BackHandler(true) {
        if (state.buttonActive)
            goBack()
        else
            Toast.makeText(context, context.getString(R.string.waiting_update_diary), Toast.LENGTH_SHORT).show()
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
        selectCityById = viewModel::selectCityById,
        showPermissionDialog = viewModel::showPermissionRequestDialog,
        disableLockScreen = viewModel::disableLockScreen
    )
}

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
    selectCityById : (Int?) -> Unit = {},
    showPermissionDialog : (String) -> Unit = {},
    disableLockScreen : () -> Unit = {}
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val focusManager = LocalFocusManager.current

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
            changeVoiceFile(it, true)
        }
    }

    val requestWriteExternalStoragePermissionLauncherForImage = rememberSinglePermissionRequestLauncher(
        onPermissionGranted = {
            disableLockScreen()
            prevPhotoPickerLauncher.launch("*/*")
        },
        onPermissionDenied = { showPermissionDialog(Settings.ACTION_APPLICATION_DETAILS_SETTINGS) }
    )

    val requestWriteExternalStoragePermissionLauncherForVoice = rememberSinglePermissionRequestLauncher(
        onPermissionGranted = {
            disableLockScreen()
            mp3PickerLauncher.launch("audio/*")
        },
        onPermissionDenied = { showPermissionDialog(Settings.ACTION_APPLICATION_DETAILS_SETTINGS) }
    )



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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BaseIconButton(
                    iconResourceId = R.drawable.ic_back,
                    onClick = {
                        if (!state.buttonActive) return@BaseIconButton
                        goBack()
                    }
                )

                TextButton(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(id = R.string.register),
                    state = if (content.isEmpty() || !state.buttonActive) TextButtonState.INACTIVE else TextButtonState.ACTIVE,
                    onClick = uploadDiary
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Gray2)
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (!state.showInitLoading && !state.showLoadingError) {
                DiaryViewTemplate(
                    modifier = Modifier.weight(1f),
                    dateTextView = {
                        Text(text = state.diaryDate.toString())
                    },
                    locationView = {
                        LocationView(
                            cityName = state.cityName,
                            onClickGpsIcon = showLocationPickerDialog
                        )
                    },
                    weatherFeelingView = {
                        WeatherFeelingSelectView(
                            feeling = state.feeling,
                            weather = state.weather,
                            currentShowSelectView = state.currentShowSelectView,
                            setFeeling = setFeeling,
                            setWeather = setWeather,
                            setCurrentShowSelectView = showSelectView
                        )
                    },
                    polaroidHorizontalPager = { isTabletMode ->
                        PolaroidHorizontalPager(
                            imageFiles = state.imageFiles,
                            diaryDate = state.diaryDate,
                            onClickVideo = goToVideo,
                            onClickDeleteButton = deleteImageFile,
                            onClickAddMedia = {
                                if (isPhotoPickerAvailable()) {
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                } else {
                                    if (WRITE_EXTERNAL_STORAGE_28 == null) {
                                        disableLockScreen()
                                        prevPhotoPickerLauncher.launch("*/*")
                                    } else {
                                        requestWriteExternalStoragePermissionLauncherForImage.launch(WRITE_EXTERNAL_STORAGE_28)
                                    }
                                }
                            },
                            enabled = state.buttonActive,
                            isTabletMode = isTabletMode
                        )
                    },
                    textField = {
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
                    },
                    soundView = {
                        if (state.voiceFile != null) {
                            SoundView(
                                file = state.voiceFile.uri,
                                playing = state.musicPlaying,
                                play = playMusic,
                                pause = pauseMusic,
                                remove = removeVoiceFile,
                                soundProgressChange = changeMusicProgress,
                                soundProgress = musicProgress,
                                isError = state.musicError
                            )
                        } else {
                            EmptySoundView(
                                onClick = {
                                    if (!state.buttonActive) return@EmptySoundView

                                    if (WRITE_EXTERNAL_STORAGE_28 == null) {
                                        disableLockScreen()
                                        mp3PickerLauncher.launch("audio/*")
                                    } else {
                                        requestWriteExternalStoragePermissionLauncherForVoice.launch(WRITE_EXTERNAL_STORAGE_28)
                                    }
                                }
                            )
                        }
                    }
                )

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