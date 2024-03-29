package com.strayalphaca.presentation

import com.strayalphaca.presentation.models.paging.SimplePagingState
import com.strayalphaca.presentation.screens.diary_list.model.SearchTargetType
import com.strayalphaca.presentation.screens.diary_list.paging.DiaryListSimplePaging
import com.strayalphaca.travel_diary.diary.model.DiaryItem
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
    private lateinit var diaryListSimplePaging : DiaryListSimplePaging
    private lateinit var diaryListSimplePagingByGroupId : DiaryListSimplePaging
    private val periodReceiveNextPageRequest = 210L

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        diaryListSimplePaging = DiaryListSimplePaging(
            samplePagingDataContainer::load, perPage = 10, targetId = 1, targetType = SearchTargetType.CITY,
        )
        diaryListSimplePagingByGroupId = DiaryListSimplePaging(
            samplePagingDataContainer::load, perPage = 10, targetId = 1, targetType = SearchTargetType.GROUP,
        )
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

    /**
     * 일지의 위치가 수정된 경우, 기존 위치에 해당하는 일지 리스트에는 표시되면 안되기 때문에 제거되어야 함.
     */
    @Test
    fun delete_position_changed_item() = runTest {
        diaryListSimplePaging.load()
        withContext(Dispatchers.Default) {
            delay(510L)
        }

        diaryListSimplePaging.modifyItem(
            DiaryItem(id = "5", imageUrl = null, cityName = "")
        )

        val data = diaryListSimplePaging.pagingData().first()
        assertEquals(9, data.size)
    }

    @Test
    fun delete_position_group_changed_item() = runTest {
        diaryListSimplePagingByGroupId.load()
        withContext(Dispatchers.Default) {
            delay(510L)
        }

        diaryListSimplePagingByGroupId.modifyItem(
            DiaryItem(id = "5", imageUrl = null, cityName = "울릉군")
        )

        val itemRemovedData = diaryListSimplePagingByGroupId.pagingData().first()
        assertEquals(9, itemRemovedData.size)
    }

    @Test
    fun modify_item() = runTest {
        diaryListSimplePaging.load()
        withContext(Dispatchers.Default) {
            delay(510L)
        }

        val changedImageUrl = "changed_image_url"
        diaryListSimplePaging.modifyItem(
            DiaryItem(id = "5", imageUrl = changedImageUrl, cityName = "종로구")
        )

        val data = diaryListSimplePaging.pagingData().first()
        val target = data.find { it.id == "5" }
        assertEquals(changedImageUrl, target?.imageUrl)
    }

    @Test
    fun delete_item() = runTest {
        diaryListSimplePaging.load()
        withContext(Dispatchers.Default) {
            delay(510L)
        }

        diaryListSimplePaging.deleteItem(
            DiaryItem(id = "5", imageUrl = "", cityName = "Seoul")
        )

        val data = diaryListSimplePaging.pagingData().first()
        val target = data.find { it.id == "5" }
        assertEquals(null, target?.imageUrl)
    }
}

class SamplePagingDataContainer() {

    private val maxCount = 35
    private val dataList = MutableList(maxCount) { index ->
        DiaryItem(id = index.toString(), imageUrl = null, cityName = "종로구")
    }
    private val delayMap = mutableMapOf(0 to 500L, 1 to 200L, 2 to 200L, 3 to 200L, 4 to 200L)

    suspend fun load(cityId : Int, perPage : Int, offset : Int) : List<DiaryItem> {
        val indexOffset = offset - 1
        delay(delayMap.getOrDefault(indexOffset, 200L))
        if (perPage * indexOffset >= dataList.size) return emptyList()

        return dataList.subList(indexOffset * perPage, min((indexOffset + 1)*perPage, dataList.size))
    }

    fun setDelayOfOffset(offset : Int, milli : Long) {
        delayMap[offset] = milli
    }

    fun getMaxCount() = maxCount
}