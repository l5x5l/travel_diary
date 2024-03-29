package com.strayalphaca.presentation.screens.home.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar
import com.strayalphaca.travel_diary.domain.calendar.usecase.UseCaseCheckWrittenOnToday
import com.strayalphaca.travel_diary.domain.calendar.usecase.UseCaseGetCalendarDiary
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.presentation.models.deeplink_handler.DeepLinkEvent
import com.strayalphaca.presentation.models.deeplink_handler.NotificationDeepLinkHandler
import com.strayalphaca.presentation.models.event_flow.MutableEventFlow
import com.strayalphaca.presentation.models.event_flow.asEventFlow
import com.strayalphaca.presentation.utils.collectLatestInScope
import com.strayalphaca.travel_diary.domain.calendar.utils.fillEmptyCellToCalendarData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val useCaseGetCalendarDiary: UseCaseGetCalendarDiary,
    private val useCaseCheckWrittenOnToday: UseCaseCheckWrittenOnToday,
    deepLinkHandler: NotificationDeepLinkHandler
) : ViewModel() {
    private val events = Channel<CalendarViewEvent>()
    val state : StateFlow<CalendarScreenState> = events.receiveAsFlow()
        .runningFold(CalendarScreenState(), ::reduce)
        .stateIn(viewModelScope, SharingStarted.Eagerly, CalendarScreenState())

    private val _goDiaryWriteNavigationEvent = MutableEventFlow<Boolean>()
    val goDiaryWriteNavigationEvent = _goDiaryWriteNavigationEvent.asEventFlow()

    private val _toastMessage = MutableEventFlow<String>()
    val toastMessage = _toastMessage.asEventFlow()

    init {
        tryGetDiaryData(
            year = Calendar.getInstance().get(Calendar.YEAR),
            month = Calendar.getInstance().get(Calendar.MONTH) + 1
        )

        useCaseGetCalendarDiary.monthCalendarFlow().collectLatestInScope(viewModelScope) {
            val diaryList = fillEmptyCellToCalendarData(it.year, it.month,  it.diaryList)
            events.send(CalendarViewEvent.SuccessLoadDiaryData(it.year, it.month, diaryList))
        }

        deepLinkHandler.deepLinkEvent.collectLatestInScope(viewModelScope) {
            if (it == DeepLinkEvent.WRITE_DIARY) {
                checkTodayWrite()
            }
        }
    }

    fun tryGetDiaryData(year : Int, month : Int) {
        viewModelScope.launch {
            events.send(CalendarViewEvent.LoadDiaryData)
            val response = useCaseGetCalendarDiary(year = year, month = month)
            if (response is BaseResponse.Failure) {
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
                if (event.writable) {
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
    class SuccessCheckingTodayWritten(val writable : Boolean) : CalendarViewEvent()
    object FailureCheckingTodayWritten : CalendarViewEvent()
}