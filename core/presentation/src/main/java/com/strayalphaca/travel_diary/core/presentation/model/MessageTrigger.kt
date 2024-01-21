package com.strayalphaca.travel_diary.core.presentation.model

import kotlinx.coroutines.flow.Flow

interface MessageTrigger {
    val message : Flow<String>
    fun triggerMessage(message : String)
}