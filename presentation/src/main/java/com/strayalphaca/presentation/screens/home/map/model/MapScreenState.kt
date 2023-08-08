package com.strayalphaca.presentation.screens.home.map.model

import com.strayalphaca.travel_diary.map.model.LocationDiary

data class MapScreenState (
    val showError : Boolean = false,
    val showLoading : Boolean = false,
    val dataList : List<LocationDiary> = listOf(),
    val currentLocationId : Int? = null
) {
    val showEmpty : Boolean get() {
        return (!showLoading && !showError && dataList.isEmpty())
    }
}