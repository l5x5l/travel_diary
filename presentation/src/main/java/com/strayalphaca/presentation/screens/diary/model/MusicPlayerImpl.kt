package com.strayalphaca.presentation.screens.diary.model

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

class MusicPlayerImpl @Inject constructor(private val context : Context) : MusicPlayer {

    private val mediaPlayer = MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
    }

    override fun setMusic(uri: Uri, isLocal : Boolean) {
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
        if (isLocal) {
            mediaPlayer.setDataSource(context, uri)
            mediaPlayer.prepare()
        } else {
            mediaPlayer.setDataSource(uri.toString())
            mediaPlayer.setOnPreparedListener { _ ->

            }
            mediaPlayer.prepareAsync()
        }
    }

    override fun getProgress(): Float {
        val progress = mediaPlayer.currentPosition / max(mediaPlayer.duration, 1).toFloat()
        return min(progress, 1f)
    }


    override fun playMusic() {
        mediaPlayer.start()
    }

    override fun stopMusic() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    override fun setPosition(progress: Float) {
        mediaPlayer.seekTo((progress * mediaPlayer.duration).toInt())
    }

    override fun release() {
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    override fun remove() {
        mediaPlayer.reset()
    }
}