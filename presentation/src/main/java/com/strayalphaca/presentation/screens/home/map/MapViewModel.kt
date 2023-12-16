package com.strayalphaca.presentation.screens.home.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalpaca.travel_diary.core.domain.model.BaseResponse
import com.strayalphaca.presentation.screens.home.map.model.MapScreenEvent
import com.strayalphaca.presentation.screens.home.map.model.MapScreenState
import com.strayalphaca.presentation.utils.collectLatestInScope
import com.strayalphaca.travel_diary.map.usecase.UseCaseGetMapDiaryList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val useCaseGetMapDiaryList: UseCaseGetMapDiaryList
) : ViewModel() {
    private val event = Channel<MapScreenEvent>()
    val state : StateFlow<MapScreenState> = event.receiveAsFlow()
        .runningFold(MapScreenState(), ::reduce)
        .stateIn(viewModelScope, SharingStarted.Eagerly, MapScreenState())

    init {
        loadLocationDiaryList(null)

        useCaseGetMapDiaryList.locationWithDataFlow().collectLatestInScope(viewModelScope) {
            event.send(MapScreenEvent.DataLoadingSuccess(it.data, it.location?.id?.id))
        }
    }
    private fun reduce(state : MapScreenState, event : MapScreenEvent) : MapScreenState {
        return when (event) {
            MapScreenEvent.DataLoading -> {
                state.copy(showLoading = true, showError = false)
            }
            MapScreenEvent.DataLoadingFailure -> {
                state.copy(showLoading = false, showError = true)
            }
            is MapScreenEvent.DataLoadingSuccess -> {
                state.copy(showLoading = false, showError = false, dataList = event.dataList, currentLocationId = event.currentLocationId)
            }
        }
    }

    fun loadLocationDiaryList(provinceId : Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            event.send(MapScreenEvent.DataLoading)
            try {
                if (provinceId == null) {
                    val response = useCaseGetMapDiaryList.getNationWideDataList()
                    if (response is BaseResponse.Failure){
                        event.send(MapScreenEvent.DataLoadingFailure)
                    }
                } else {
                    val response = useCaseGetMapDiaryList.getProvinceDataList(provinceId)
                    if (response is BaseResponse.Failure){
                        event.send(MapScreenEvent.DataLoadingFailure)
                    }
                }
            } catch (e: Exception) {
                event.send(MapScreenEvent.DataLoadingFailure)
            }
        }
    }

    /**
     * 현재 표시하고 있는 위치가 마지막 위치여서, 아이템 클릭시 해당 위치의 리스트를 표시하는 화면으로 이동하는지 여부를 확인합니다.
     */
    fun isLeafLocation() : Boolean {
        return (state.value.currentLocationId != null)
    }
}