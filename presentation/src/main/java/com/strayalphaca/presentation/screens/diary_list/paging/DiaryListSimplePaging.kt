package com.strayalphaca.presentation.screens.diary_list.paging

import com.strayalphaca.presentation.models.paging.SimplePaging
import com.strayalphaca.presentation.models.paging.SimplePagingState
import com.strayalphaca.travel_diary.diary.model.DiaryItem
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction3

class DiaryListSimplePaging(
    private val getDiaryList: KSuspendFunction3<Int, Int, Int, List<DiaryItem>>,
    private val perPage: Int,
    private val targetId: Int,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val initKey : Int = 0,
) : SimplePaging<DiaryItem> {

    private val data : MutableStateFlow<List<DiaryItem>> = MutableStateFlow(emptyList())
    private val state = MutableStateFlow(SimplePagingState.IDLE)
    private var currentKey = initKey
    private var requestJob : Job? = null
    private val firstLoadingPageRatio = 2

    override fun pagingData(): Flow<List<DiaryItem>> = data

    override suspend fun refresh() {
        requestJob?.cancel()
        state.value = SimplePagingState.LOADING_INIT

        requestJob = coroutineScope.launch {
            try {
                val response = getDiaryList(targetId, perPage * firstLoadingPageRatio, currentKey)
                if (response.size < perPage * firstLoadingPageRatio) {
                    state.value = SimplePagingState.LAST
                } else {
                    state.value = SimplePagingState.IDLE
                }
                data.value = response
                currentKey = initKey + firstLoadingPageRatio - 1
            } catch (e : Exception) {
                if (e !is CancellationException)
                    state.value = SimplePagingState.FAILURE_INIT
            }
        }
    }

    override suspend fun load() {
        if (state.value != SimplePagingState.IDLE) return

        requestJob?.cancel()
        state.value = SimplePagingState.LOADING_NEXT

        requestJob = coroutineScope.launch {
            try {
                val response = getDiaryList(targetId, perPage, currentKey)
                if (response.size < perPage) {
                    state.value = SimplePagingState.LAST
                } else {
                    state.value = SimplePagingState.IDLE
                }
                data.value = data.value + response
                currentKey += 1
            } catch (e : Exception) {
                if (e !is CancellationException)
                    state.value = SimplePagingState.FAILURE_NEXT
            }
        }

    }

    override fun pagingState(): Flow<SimplePagingState> = state
}