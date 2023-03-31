package com.strayalphaca.presentation.screens.diary.write

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.strayalphaca.presentation.utils.getFileFromLocal

@Composable
fun DiaryWriteScreen(
    id : String?,
    viewModel : DiaryWriteViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    val content by viewModel.writingContent.collectAsState()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    val pickMP3File = getFileFromLocal(context = context) { file ->
        viewModel.inputVoiceFile(file)
    }

    val pickImage = getFileFromLocal(context = context) { file ->
        viewModel.inputImageFile(file)
    }

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                pickMP3File.launch("audio/*")
            }
        }

    LaunchedEffect(id) {
        if (id != null) viewModel.tryLoadDetail(id)
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
                    onClick = {}
                )

                TextButton(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(id = R.string.register)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Gray2)
            )


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                Text(text = "")

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(id = R.string.today_feeling),
                            style = MaterialTheme.typography.body2
                        )
                    }
                    Row(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(id = R.string.weather),
                            style = MaterialTheme.typography.body2
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (state.imageFiles.isNotEmpty()) {
                    PolaroidView(imageFile = state.imageFiles[0])
                }

                Spacer(modifier = Modifier.height(24.dp))

                BasicTextField(
                    value = content,
                    onValueChange = viewModel::inputContent,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    textStyle = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onBackground),
                    decorationBox = { innerTextField ->
                        Column(modifier = Modifier.fillMaxWidth()) {
                            innerTextField()
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "${content.length}/300",
                                modifier = Modifier.align(Alignment.End),
                                style = MaterialTheme.typography.caption
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (state.voiceFile != null)
                    SoundView(state.voiceFile!!)
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
                        iconResourceId = R.drawable.ic_map,
                        onClick = {
                            if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            } else {
                                pickMP3File.launch("audio/*")
                            }
                        }
                    )

                    BaseIconButton(
                        iconResourceId = R.drawable.ic_map,
                        onClick = {
                            if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            } else {
                                pickImage.launch("image/*")
                            }
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
    TravelDiaryTheme() {
        DiaryWriteScreen(null)
    }
}