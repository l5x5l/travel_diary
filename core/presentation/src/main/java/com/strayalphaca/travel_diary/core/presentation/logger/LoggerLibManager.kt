package com.strayalphaca.travel_diary.core.presentation.logger

interface LoggerLibManager<T> {
    fun attachLogger(loggerObj : T)
    fun detachLogger()
}