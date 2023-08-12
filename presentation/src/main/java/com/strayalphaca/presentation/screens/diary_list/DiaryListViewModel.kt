package com.strayalphaca.presentation.screens.diary_list

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.strayalphaca.domain.diary.model.DiaryItem
import com.strayalphaca.domain.diary.use_case.UseCaseGetDiaryList
import com.strayalphaca.presentation.screens.diary_list.paging.DiaryListPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class DiaryListViewModel @Inject constructor(
    private val useCaseGetDiaryList: UseCaseGetDiaryList
) : ViewModel() {

    private var cityId : Int = 0

    var diaryPager : Flow<PagingData<DiaryItem>> = emptyFlow()

    fun setCityIdAndLoadData(cityId : Int) {
        this.cityId = cityId
        diaryPager = Pager(PagingConfig(pageSize = 10)) {
            DiaryListPagingSource(useCaseGetDiaryList, 10, this.cityId)
        }.flow
    }
}