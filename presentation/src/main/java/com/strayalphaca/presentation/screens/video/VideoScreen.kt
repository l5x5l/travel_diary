package com.strayalphaca.presentation.screens.video

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView

@Composable
fun VideoContainer(
    viewModel : VideoViewModel,
    uri : Uri?,
    goBack : () -> Unit,
) {
    VideoScreen(
        player = viewModel.player,
        uri = uri,
        goBack = goBack,
        setMedia = viewModel::setVideo
    )
}

@Composable
fun VideoScreen(
    player: Player,
    uri : Uri?,
    goBack : () -> Unit,
    setMedia : (Uri) -> Unit
) {
    LaunchedEffect(uri) {
        if (uri == null) goBack()

        uri?.let {
            setMedia(uri)
        }
    }

    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black),
        verticalArrangement = Arrangement.Center,
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).also {
                    it.player = player
                }
            },
            update = { playerView ->
                when (lifecycle) {
                    Lifecycle.Event.ON_RESUME -> {
                        playerView.onResume()
                    }
                    Lifecycle.Event.ON_PAUSE -> {
                        playerView.onPause()
                        playerView.player?.pause()
                    }
                    else -> Unit
                }
            }
        )
    }
}