package com.strayalphaca.presentation.models.paging

import kotlinx.coroutines.flow.Flow

interface SimplePaging<T> {
    fun pagingData() : Flow<List<T>>
    suspend fun refresh()
    suspend fun load()
    fun pagingState() : Flow<SimplePagingState>
    suspend fun modifyItem(item : T)
    suspend fun deleteItem(item : T)
    fun clear()
}