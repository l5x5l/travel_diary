package com.strayalphaca.presentation.screens.home.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.domain.calendar.model.DiaryInCalendar
import com.strayalphaca.domain.calendar.use_case.UseCaseCheckWrittenOnToday
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
    private val useCaseGetCalendarDiary: UseCaseGetCalendarDiary,
    private val useCaseCheckWrittenOnToday: UseCaseCheckWrittenOnToday
) : ViewModel() {
    private val events = Channel<CalendarViewEvent>()
    val state : StateFlow<CalendarScreenState> = events.receiveAsFlow()
        .runningFold(CalendarScreenState(), ::reduce)
        .stateIn(viewModelScope, SharingStarted.Eagerly, CalendarScreenState())

    private val _goDiaryWriteNavigationEvent = MutableSharedFlow<Boolean>()
    val goDiaryWriteNavigationEvent = _goDiaryWriteNavigationEvent.asSharedFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

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

    fun checkTodayWrite() {
        viewModelScope.launch {
            events.send(CalendarViewEvent.CheckingTodayWritten)
            val response = useCaseCheckWrittenOnToday()
            if (response is BaseResponse.Success<Boolean>) {
                events.send(CalendarViewEvent.SuccessCheckingTodayWritten(response.data))
            } else {
                events.send(CalendarViewEvent.FailureCheckingTodayWritten)
            }
        }
    }

    private fun callGoDiaryWriteNavigationEvent() {
        viewModelScope.launch {
            _goDiaryWriteNavigationEvent.emit(true)
        }
    }

    private fun makeToast() {
        viewModelScope.launch {
            _toastMessage.emit("하루에 한번만 작성 가능합니다.")
        }
    }

    private fun reduce(state : CalendarScreenState, event: CalendarViewEvent) : CalendarScreenState {
        return when (event){
            CalendarViewEvent.LoadDiaryData -> {
                state.copy(clickEnable = false)
            }
            is CalendarViewEvent.SuccessLoadDiaryData -> {
                state.copy(year = event.year, month = event.month, diaryData = event.diaryData, clickEnable = true)
            }
            CalendarViewEvent.FailureLoadDiaryData -> {
                state.copy(clickEnable = true)
            }
            CalendarViewEvent.CheckingTodayWritten -> {
                state.copy(clickEnable = false)
            }
            is CalendarViewEvent.SuccessCheckingTodayWritten -> {
                if (!event.written) {
                    callGoDiaryWriteNavigationEvent()
                } else {
                    makeToast()
                }
                state.copy(clickEnable = true)
            }
            CalendarViewEvent.FailureCheckingTodayWritten -> {
                state.copy(clickEnable = true)
            }
        }
    }
}

data class CalendarScreenState(
    val year : Int = 2023,
    val month : Int = 1,
    val diaryData : List<DiaryInCalendar?> = listOf(),
    val clickEnable : Boolean = false
)

sealed class CalendarViewEvent{
    object LoadDiaryData : CalendarViewEvent()
    class SuccessLoadDiaryData(val year : Int, val month : Int, val diaryData : List<DiaryInCalendar?>) : CalendarViewEvent()
    object FailureLoadDiaryData : CalendarViewEvent()
    object CheckingTodayWritten : CalendarViewEvent()
    class SuccessCheckingTodayWritten(val written : Boolean) : CalendarViewEvent()
    object FailureCheckingTodayWritten : CalendarViewEvent()
}