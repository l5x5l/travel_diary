package com.strayalphaca.presentation.screens.diary_list.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.strayalphaca.travel_diary.diary.model.DiaryItem
import kotlin.reflect.KSuspendFunction3

class DiaryListPagingSource(
    private val getDiaryList: KSuspendFunction3<Int, Int, Int, List<DiaryItem>>,
    private val perPage: Int,
    private val targetId: Int
) : PagingSource<Int, DiaryItem>() {
    override fun getRefreshKey(state: PagingState<Int, DiaryItem>): Int {
        return ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DiaryItem> {
        return try {
            val nextKey = params.key ?: 0
            val response = getDiaryList(targetId, perPage, nextKey)
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = nextKey.plus(response.size)
            )
        } catch (e : Exception) {
            LoadResult.Error(e)
        }
    }
}