package com.strayalphaca.presentation.screens.diary_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.strayalphaca.domain.diary.model.DiaryItem
import com.strayalphaca.domain.diary.use_case.UseCaseGetDiaryList
import com.strayalphaca.presentation.screens.diary_list.paging.DiaryListPagingSource
import com.strayalphaca.travel_diary.map.model.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class DiaryListViewModel @Inject constructor(
    private val useCaseGetDiaryList: UseCaseGetDiaryList
) : ViewModel() {

    private var cityGroupId : Int = 0

    var diaryPager : Flow<PagingData<DiaryItem>> = emptyFlow()

    var selectedCityId : Int ?= null
        private set


    private val _locationTitle = MutableStateFlow<String>("-")
    val locationTitle = _locationTitle.asStateFlow()

    fun setCityGroup(cityGroupId : Int) {
        if (this.cityGroupId == cityGroupId) return

        this.cityGroupId = cityGroupId
        setCity(null)
    }

    fun setCity(cityId : Int?) {
        selectedCityId = cityId
        if (cityId == null) {
            _locationTitle.value =
                City
                    .getSameGroupCityList(cityGroupId)
                    .joinToString(separator = ",") { city ->
                        city.name
                    }
            diaryPager = Pager(PagingConfig(pageSize = 10)) {
                DiaryListPagingSource(useCaseGetDiaryList::getByCityGroupId, 10, cityGroupId)
            }.flow.cachedIn(viewModelScope)
        } else {
            _locationTitle.value = City.findCity(cityId).name
            diaryPager = Pager(PagingConfig(pageSize = 10)) {
                DiaryListPagingSource(useCaseGetDiaryList::invoke, 10, cityId)
            }.flow.cachedIn(viewModelScope)
        }
    }
}