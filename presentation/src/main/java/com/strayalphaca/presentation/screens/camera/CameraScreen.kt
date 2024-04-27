package com.strayalphaca.presentation.screens.camera

import android.Manifest
import android.content.res.Configuration
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.screens.camera.util.imageProxyToBitmap
import com.strayalphaca.presentation.screens.camera.util.reverseHorizontal
import com.strayalphaca.presentation.ui.theme.Tape
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import com.strayalphaca.presentation.utils.collectAsEffect

@Composable
fun CameraScreenContainer(
    viewModel : CameraViewModel,
    onBackPress : () -> Unit,
    onBackPressWithResult : (String) -> Unit
) {
    var permissionGranted by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                Toast.makeText(context, context.getString(R.string.permission_description_camera), Toast.LENGTH_SHORT).show()
                onBackPress()
            }
            permissionGranted = isGranted
        }
    )

    val cameraScreenState by viewModel.screenState.collectAsState()

    viewModel.moveToBackStack.collectAsEffect { photoUri ->
        if (photoUri == null) {
            onBackPress()
        } else {
            onBackPressWithResult(photoUri.toString())
        }
    }

    LaunchedEffect(Unit) {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    BackHandler {
        viewModel.onBackPressed()
    }

    if (permissionGranted) {
        CameraScreen(
            cameraScreenState = cameraScreenState,
            showTakenImage = viewModel::takePhoto,
            onBackPress = onBackPress,
            backToCamera = viewModel::moveToCameraState,
            confirmImage = viewModel::confirmPhoto
        )
    }
}

@Composable
fun CameraScreen(
    cameraScreenState: CameraScreenState,
    showTakenImage : (Bitmap) -> Unit,
    onBackPress: () -> Unit,
    backToCamera : () -> Unit,
    confirmImage : () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var lensFacing by remember { mutableStateOf(CameraSelector.LENS_FACING_BACK) }
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
                onClick = { onBackPress() }
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
                    if (lensFacing == CameraSelector.LENS_FACING_BACK && cameraController.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA))
                        lensFacing = CameraSelector.LENS_FACING_FRONT
                    else if (lensFacing == CameraSelector.LENS_FACING_FRONT && cameraController.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA))
                        lensFacing = CameraSelector.LENS_FACING_BACK
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
                    cameraController.takePicture(
                        ContextCompat.getMainExecutor(context),
                        object : ImageCapture.OnImageCapturedCallback() {
                            override fun onCaptureSuccess(image: ImageProxy) {
                                val bitmap = imageProxyToBitmap(image).run {
                                    if (lensFacing == CameraSelector.LENS_FACING_FRONT) {
                                        this.reverseHorizontal()
                                    } else {
                                        this
                                    }
                                }

                                showTakenImage(bitmap)
                            }
                        }
                    )
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

        if (cameraScreenState is CameraScreenState.PhotoConfirmation) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .zIndex(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        onClick = { backToCamera() }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = "back_to_camera_button",
                            modifier = Modifier
                                .size(48.dp)
                                .padding(12.dp),
                            tint = Color.White
                        )
                    }
                }

                Image(
                    modifier = Modifier
                        .weight(1f),
                    bitmap = cameraScreenState.bitmap.asImageBitmap(),
                    contentDescription = "captured photo"
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = { backToCamera() },
                        modifier = Modifier.weight(1f).padding(vertical = 8.dp)
                    ) {
                        Text(text = stringResource(id = R.string.retake_photo), color = Color.White)
                    }

                    Divider(
                        modifier = Modifier.width(1.dp).height(24.dp),
                        color = Color.White
                    )

                    TextButton(
                        onClick = { confirmImage() },
                        modifier = Modifier.weight(1f).padding(vertical = 8.dp)
                    ) {
                        Text(text = stringResource(id = R.string.confirmation_this_photo), color = Color.White)
                    }
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
fun CameraScreenPreview(){
    TravelDiaryTheme {
        CameraScreen(
            cameraScreenState = CameraScreenState.Camera,
            showTakenImage = {},
            onBackPress = {},
            backToCamera = {},
            confirmImage = {}
        )
    }
}