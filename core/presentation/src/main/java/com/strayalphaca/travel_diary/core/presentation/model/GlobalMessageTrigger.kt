package com.strayalphaca.travel_diary.core.presentation.model

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GlobalMessageTrigger @Inject constructor() : MessageTrigger {
    private val _message = MutableSharedFlow<String>()
    override val message: Flow<String> = _message.asSharedFlow()

    @OptIn(DelicateCoroutinesApi::class)
    override fun triggerMessage(message: String) {
        GlobalScope.launch(Dispatchers.Main) {
            _message.emit(message)
        }
    }

}