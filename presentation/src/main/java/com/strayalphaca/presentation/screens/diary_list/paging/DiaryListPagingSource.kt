package com.strayalphaca.presentation.screens.diary_list.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.strayalphaca.domain.diary.model.DiaryItem
import com.strayalphaca.domain.diary.use_case.UseCaseGetDiaryList

class DiaryListPagingSource(
    private val useCaseGetDiaryList: UseCaseGetDiaryList,
    private val perPage : Int,
    private val cityId : Int
) : PagingSource<Int, DiaryItem>() {
    override fun getRefreshKey(state: PagingState<Int, DiaryItem>): Int {
        return ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DiaryItem> {
        return try {
            val nextKey = params.key ?: 0
            val response = useCaseGetDiaryList(cityId, perPage, nextKey)
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