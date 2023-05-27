package com.strayalphaca.presentation.screens.video

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    val player: Player,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val videoUri = savedStateHandle.getStateFlow(VIDEO_URI, Uri.EMPTY)

    init {
        player.prepare()
    }

    fun setVideo(uri : Uri) {
        savedStateHandle[VIDEO_URI] = uri
        player.setMediaItem(MediaItem.fromUri(uri))
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

    companion object {
        private const val VIDEO_URI = "VIDEO_URI"
    }
}