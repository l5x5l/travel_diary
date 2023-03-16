package com.strayalphaca.presentation.screens.home.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.domain.calendar.model.DiaryInCalendar
import com.strayalphaca.domain.calendar.use_case.UseCaseGetCalendarDiary
import com.strayalphaca.domain.model.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val useCaseGetCalendarDiary: UseCaseGetCalendarDiary
) : ViewModel() {
    private val events = Channel<CalendarViewEvent>()
    val state : StateFlow<CalendarScreenState> = events.receiveAsFlow()
        .runningFold(CalendarScreenState(), ::reduce)
        .stateIn(viewModelScope, SharingStarted.Eagerly, CalendarScreenState())

    init {
        tryGetDiaryData(
            year = Calendar.getInstance().get(Calendar.YEAR),
            month = Calendar.getInstance().get(Calendar.MONTH) + 1
        )
    }

    fun tryGetDiaryData(year : Int, month : Int) {
        viewModelScope.launch {
            events.send(CalendarViewEvent.LoadDiaryData)
            val response = useCaseGetCalendarDiary(year = year, month = month)
            if (response is BaseResponse.Success) {
                events.send(CalendarViewEvent.SuccessLoadDiaryData(year = year, month = month, diaryData = response.data))
            } else {
                events.send(CalendarViewEvent.FailureLoadDiaryData)
            }
        }
    }

    private fun reduce(state : CalendarScreenState, event: CalendarViewEvent) : CalendarScreenState {
        return when (event){
            CalendarViewEvent.LoadDiaryData -> {
                state
            }
            is CalendarViewEvent.SuccessLoadDiaryData -> {
                state.copy(year = event.year, month = event.month, diaryData = event.diaryData)
            }
            CalendarViewEvent.FailureLoadDiaryData -> {
                state
            }
        }
    }
}

data class CalendarScreenState(
    val year : Int = 2023,
    val month : Int = 1,
    val diaryData : List<DiaryInCalendar?> = listOf()
)

sealed class CalendarViewEvent{
    object LoadDiaryData : CalendarViewEvent()
    class SuccessLoadDiaryData(val year : Int, val month : Int, val diaryData : List<DiaryInCalendar?>) : CalendarViewEvent()
    object FailureLoadDiaryData : CalendarViewEvent()
}