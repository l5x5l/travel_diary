package com.strayalphaca.travel_diary.domain.file.model

import java.io.File

interface FileResizeHandler {
    fun resizeImageFile(file : File, maxByteSize : Int) : File
    fun cutAudioFile(file : File, startSecond : Int, endSecond : Int) : File
}