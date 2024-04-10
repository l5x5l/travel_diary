package com.strayalphaca.presentation.screens.camera

import android.Manifest
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.ui.theme.Tape
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun CameraScreenContainer(
    onBackPress : () -> Unit
) {
    var permissionGranted by remember { mutableStateOf(false) }
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                onBackPress()
            }
            permissionGranted = isGranted
        }
    )

    LaunchedEffect(Unit) {
        // 권한 확인 후 요청 혹은 화면 호출
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    if (permissionGranted) {
        CameraScreen()
    }
}

@Composable
fun CameraScreen(

) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // lensFacing의 경우 추후 viewModel 내 state로 변경할 예정
    val lensFacing = remember { CameraSelector.LENS_FACING_BACK }
    val previewView = remember { PreviewView(context) }
    val cameraController = remember { LifecycleCameraController(context) }

    LaunchedEffect(lensFacing) {
        cameraController.unbind()
        cameraController.bindToLifecycle(lifecycleOwner)
        cameraController.cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
        previewView.controller = cameraController
    }


    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .zIndex(1f)
                .align(Alignment.TopCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Black,
                            Color.Transparent
                        )
                    )
                )
                .padding(16.dp)
        ) {
            IconButton(
                onClick = {  }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "back_button",
                    modifier = Modifier
                        .size(48.dp)
                        .background(color = Color.White)
                        .padding(2.dp)
                        .border(width = 1.dp, shape = RectangleShape, color = Color.Black)
                        .padding(10.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Tape)
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { previewView }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        )
                    )
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                modifier = Modifier
                    .size(64.dp)
                    .background(Color.White, CircleShape),
                onClick = {
                    // 카메라 전환 기능
                }
            ) {
                Icon(
                    modifier = Modifier
                        .padding(18.dp),
                    painter = painterResource(id = R.drawable.ic_change),
                    contentDescription = "change_camera"
                )
            }

            Spacer(modifier = Modifier.width(36.dp))

            IconButton(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.White, CircleShape),
                onClick = {
                    // 사진 촬영
                }
            ) {
                Icon(
                    modifier = Modifier
                        .size(72.dp)
                        .border(width = 2.dp, color = Color.Black, shape = CircleShape)
                        .padding(18.dp),
                    painter = painterResource(id = R.drawable.ic_camera),
                    contentDescription = "take_picture"
                )
            }

            Spacer(modifier = Modifier.width(90.dp))

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
fun CameraScreenPreview(){
    TravelDiaryTheme {
        CameraScreen()
    }
}