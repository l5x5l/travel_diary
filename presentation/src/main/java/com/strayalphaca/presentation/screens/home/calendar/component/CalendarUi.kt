package com.strayalphaca.presentation.screens.home.calendar.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.strayalphaca.domain.all.DiaryDate
import com.strayalphaca.presentation.components.block.CalendarItemEmptyView
import com.strayalphaca.presentation.components.block.CalendarItemView
import com.strayalphaca.presentation.components.template.calendar_view.CalendarView
import com.strayalphaca.travel_diary.domain.calendar.model.DiaryInCalendar


@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun CalendarUi(
    modifier : Modifier = Modifier,
    goToDiaryWrite: (String?, String?) -> Unit = { _, _ ->},
    goToDiaryDetail: (String) -> Unit = {},
    year : Int = 2023,
    month : Int = 12,
    diaryData : List<DiaryInCalendar?> = emptyList(),
    enable : Boolean = true
) {
    val pagerState = rememberPagerState(Int.MAX_VALUE / 2)

    HorizontalPager(
        pageCount = 1,
        state = pagerState,
        modifier = modifier
    ) {
        CalendarView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            year = year,
            month = month,
            calendarData = diaryData,
            contentView = { data, _, isToday ->
                CalendarItemView(item = data, isToday = isToday,
                    modifier = Modifier.clickable {
                        if (!enable) return@clickable
                        goToDiaryDetail(data.id)
                    })
            },
            emptyView = { day, isToday ->
                CalendarItemEmptyView(day = day, isToday = isToday,
                    modifier = Modifier.clickable {
                        if (!enable) return@clickable
                        goToDiaryWrite(
                            null,
                            DiaryDate(year = year, month = month, day = day).toString()
                        )
                    }
                )
            },
            outRangeView = { day ->
                CalendarItemEmptyView(day = day, isToday = false, isCurrentMonth = false)
            }
        )
    }
}