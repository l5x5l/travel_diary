package com.strayalphaca.presentation.models

import kotlinx.coroutines.*

class Timer(
    private val coroutineScope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val totalTime : Int = 180,
    private val action : (Int) -> Unit = {}
) {
    private var currentTime = totalTime
    private var timerJob : Job ?= null

    val isAvailable : Boolean get() { return currentTime >= 0 }

    fun start() {
        timerJob = coroutineScope.launch(dispatcher) {
            while(currentTime >= 0) {
                action(currentTime--)
                delay(1000L)
            }
        }
    }

    fun pause() {
        timerJob?.cancel()
    }

    fun refresh() {
        timerJob?.cancel()
        currentTime = totalTime
        start()
    }

    fun clear() {
        timerJob?.cancel()
        currentTime = 0
    }
}