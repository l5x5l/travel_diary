package com.strayalphaca.presentation.screens.home.map.model

import com.strayalphaca.travel_diary.map.model.LocationDiary

sealed class MapScreenEvent {
    object DataLoading : MapScreenEvent()
    object DataLoadingFailure : MapScreenEvent()
    class DataLoadingSuccess(val dataList : List<LocationDiary>, val currentLocationId : Int?) : MapScreenEvent()
}