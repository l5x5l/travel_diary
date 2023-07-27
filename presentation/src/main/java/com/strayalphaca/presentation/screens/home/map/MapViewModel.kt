package com.strayalphaca.presentation.screens.home.map

import androidx.lifecycle.ViewModel
import com.strayalphaca.travel_diary.map.usecase.UseCaseLoadDataListInArea
import com.strayalphaca.travel_diary.map.usecase.UseCaseLoadRecentlyDataPerArea
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val useCaseLoadDataListInArea : UseCaseLoadDataListInArea,
    private val useCaseLoadRecentlyDataPerArea: UseCaseLoadRecentlyDataPerArea
) : ViewModel() {

}