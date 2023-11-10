package com.strayalphaca.presentation.models.paging

import kotlinx.coroutines.flow.Flow

interface SimplePaging<T> {
    fun pagingData() : Flow<List<T>>
    suspend fun refresh()
    suspend fun load()
    fun pagingState() : Flow<SimplePagingState>
}