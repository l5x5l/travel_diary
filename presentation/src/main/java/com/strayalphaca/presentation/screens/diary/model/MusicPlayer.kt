package com.strayalphaca.presentation.screens.diary.model

import android.net.Uri

interface MusicPlayer {
    fun setMusic(uri : Uri, isLocal : Boolean = true)
    fun getProgress() : Float
    fun playMusic()
    fun stopMusic()
    fun setPosition(progress : Float)
    fun release()
    fun remove()
}