package com.strayalphaca.presentation.screens.home.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.strayalphaca.presentation.components.block.CalendarItemEmptyView
import com.strayalphaca.presentation.components.block.CalendarItemView
import com.strayalphaca.presentation.components.template.calendar_view.CalendarView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    onDiaryClick: (Int) -> Unit = {},
    viewModel: CalendarViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val pagerState = rememberPagerState(Int.MAX_VALUE / 2)

    Column(modifier = modifier.padding(vertical = 48.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(text = state.month.toString(), style = MaterialTheme.typography.h1)
            Text(text = state.year.toString(), style = MaterialTheme.typography.h2)
        }

        Spacer(modifier = Modifier.height(32.dp))
        
        HorizontalPager(
            pageCount = 1,
            state = pagerState
        ) {
            CalendarView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                year = state.year,
                month = state.month,
                calendarData = state.diaryData,
                contentView = { data, day, isToday ->
                    CalendarItemView(item = data, day = day, isToday = isToday)
                },
                emptyView = { day, isToday ->
                    CalendarItemEmptyView(day = day, isToday = isToday)
                },
                outRangeView = { day ->
                    CalendarItemEmptyView(day = day, isToday = false, isCurrentMonth = false)
                }
            )
        }
    }
}