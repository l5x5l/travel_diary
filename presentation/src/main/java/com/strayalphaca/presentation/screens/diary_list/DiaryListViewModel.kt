package com.strayalphaca.presentation.screens.diary_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.strayalphaca.presentation.screens.diary_list.model.SearchTargetType
import com.strayalphaca.presentation.screens.diary_list.paging.DiaryListSimplePaging
import com.strayalphaca.travel_diary.diary.model.DiaryItemUpdate
import com.strayalphaca.travel_diary.diary.use_case.UseCaseGetDiaryList
import com.strayalphaca.travel_diary.map.model.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiaryListViewModel @Inject constructor(
    private val useCaseGetDiaryList: UseCaseGetDiaryList
) : ViewModel() {

    private var cityGroupId : Int = 0

    var diaryListSimplePaging = DiaryListSimplePaging(
        getDiaryList = useCaseGetDiaryList::getByCityGroupId,
        perPage = 10,
        targetId = cityGroupId
    )
        private set

    var selectedCityId : Int ?= null
        private set


    private val _locationTitle = MutableStateFlow("-")
    val locationTitle = _locationTitle.asStateFlow()

    init {
        viewModelScope.launch {
            useCaseGetDiaryList.getUpdatedDiary().collect {
                when (it) {
                    is DiaryItemUpdate.Delete -> {
                        diaryListSimplePaging.deleteItem(it.item)
                    }
                    is DiaryItemUpdate.Modify -> {
                        diaryListSimplePaging.modifyItem(it.item)
                    }
                }
            }
        }
    }

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

            diaryListSimplePaging = DiaryListSimplePaging(
                getDiaryList = useCaseGetDiaryList::getByCityGroupId,
                perPage = 10,
                targetId = cityGroupId,
                targetType = SearchTargetType.GROUP
            )
            viewModelScope.launch {
                diaryListSimplePaging.refresh()
            }

        } else {
            _locationTitle.value = City.findCity(cityId).name

            diaryListSimplePaging = DiaryListSimplePaging(
                getDiaryList = useCaseGetDiaryList::invoke,
                perPage = 10,
                targetId = cityId,
                targetType = SearchTargetType.CITY
            )
            viewModelScope.launch {
                diaryListSimplePaging.refresh()
            }
        }
    }

    fun loadNext() {
        viewModelScope.launch {
            diaryListSimplePaging.load()
        }
    }

    override fun onCleared() {
        diaryListSimplePaging.clear()
        super.onCleared()
    }
}