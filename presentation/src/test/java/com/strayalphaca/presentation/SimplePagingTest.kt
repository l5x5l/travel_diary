package com.strayalphaca.presentation

import com.strayalphaca.presentation.models.paging.SimplePagingState
import com.strayalphaca.presentation.screens.diary_list.paging.DiaryListSimplePaging
import com.strayalphaca.travel_diary.diary.model.DiaryItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import kotlin.math.min

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SimplePagingTest {
    private val testDispatcher = StandardTestDispatcher()
    private val samplePagingDataContainer = SamplePagingDataContainer()
    private var diaryListSimplePaging : DiaryListSimplePaging = DiaryListSimplePaging(
        samplePagingDataContainer::load, perPage = 10, targetId = 1,
        coroutineScope = CoroutineScope(testDispatcher)
    )
    private val periodReceiveNextPageRequest = 210L

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        diaryListSimplePaging = DiaryListSimplePaging(samplePagingDataContainer::load, perPage = 10, targetId = 1)
        samplePagingDataContainer.setDelayOfOffset(0, 500L)
    }


    /**
     * 다음 페이지 로딩 중 새로고침 요청시, 기존 요청을 취소하고 새로고침을 진행해야 함
     */
    @Test
    fun refresh_while_page_loading() = runTest {
        diaryListSimplePaging.load()
        diaryListSimplePaging.refresh()

        withContext(Dispatchers.Default) {
            delay(periodReceiveNextPageRequest)
        }

        val state = diaryListSimplePaging.pagingState().first()

        assertEquals(SimplePagingState.LOADING_INIT, state)
    }

    /**
     * 새로고침 중 다음 페이지 요청시, 다음 페이지 요청을 무시하고 새로고침을 계속 진행해야 함
     */
    @Test
    fun page_loading_while_refresh() = runTest {
        diaryListSimplePaging.refresh()
        diaryListSimplePaging.load()

        withContext(Dispatchers.Default) {
            delay(periodReceiveNextPageRequest)
        }

        val state = diaryListSimplePaging.pagingState().first()

        assertEquals(SimplePagingState.LOADING_INIT, state)
    }

    /**
     * 동일한 페이지 요청을 여러번 연속으로 호출할 경우, 첫 요청을 제외한 나머지 요청은 무시해야 함
     */
    @Test
    fun request_same_page_while_loading() = runTest {
        samplePagingDataContainer.setDelayOfOffset(0, 200L)

        diaryListSimplePaging.load()
        diaryListSimplePaging.load()
        diaryListSimplePaging.load()
        diaryListSimplePaging.load()

        withContext(Dispatchers.Default) {
            delay(periodReceiveNextPageRequest)
        }

        val state = diaryListSimplePaging.pagingState().first()
        val data = diaryListSimplePaging.pagingData().first()
        assertEquals(SimplePagingState.IDLE, state)
        assertEquals(10, data.size)
    }

    /**
     * 새로고침 응답을 받은 후, 이전에 요청했던 페이지 요청 응답을 받게 되더라도 무시해야 함
     */
    @Test
    fun receive_prev_request_response_after_refresh_response() = runTest {
        samplePagingDataContainer.setDelayOfOffset(0, 100L)

        diaryListSimplePaging.load()
        diaryListSimplePaging.refresh()

        withContext(Dispatchers.Default) {
            delay(periodReceiveNextPageRequest)
        }

        val state = diaryListSimplePaging.pagingState().first()
        val data = diaryListSimplePaging.pagingData().first()

        assertEquals(SimplePagingState.IDLE, state)
        assertEquals(20, data.size)
    }

    /**
     * 마지막 데이터까지 응답을 받은 경우, 상태값은 LAST여야 함.
     */
    @Test
    fun request_until_last() = runTest {
        samplePagingDataContainer.setDelayOfOffset(0, 200L)

        withContext(Dispatchers.Default) {
            diaryListSimplePaging.load()
            delay(periodReceiveNextPageRequest)
            diaryListSimplePaging.load()
            delay(periodReceiveNextPageRequest)
            diaryListSimplePaging.load()
            delay(periodReceiveNextPageRequest)
            diaryListSimplePaging.load()
            delay(periodReceiveNextPageRequest)
        }

        val state = diaryListSimplePaging.pagingState().first()
        val data = diaryListSimplePaging.pagingData().first()

        assertEquals(SimplePagingState.LAST, state)
        assertEquals(samplePagingDataContainer.getMaxCount(), data.size)

    }
}

class SamplePagingDataContainer() {

    private val maxCount = 35
    private val dataList = MutableList(maxCount) { index ->
        DiaryItem(id = index.toString(), imageUrl = null, cityName = "")
    }
    private val delayMap = mutableMapOf(0 to 500L, 1 to 200L, 2 to 200L, 3 to 200L, 4 to 200L)

    suspend fun load(cityId : Int, perPage : Int, offset : Int) : List<DiaryItem> {
        delay(delayMap.getOrDefault(offset, 200L))
        if (perPage * offset >= dataList.size) return emptyList()

        return dataList.subList(offset * perPage, min((offset + 1)*perPage, dataList.size))
    }

    fun setDelayOfOffset(offset : Int, milli : Long) {
        delayMap[offset] = milli
    }

    fun getMaxCount() = maxCount
}