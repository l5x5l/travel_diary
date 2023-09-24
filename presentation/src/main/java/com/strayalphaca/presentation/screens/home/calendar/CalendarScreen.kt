package com.strayalphaca.presentation.screens.home.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.strayalphaca.presentation.components.block.CalendarItemEmptyView
import com.strayalphaca.presentation.components.block.CalendarItemView
import com.strayalphaca.presentation.components.template.calendar_view.CalendarView
import com.strayalphaca.presentation.components.template.dialog.MonthPickerDialog
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_icon_button.BaseIconButton
import com.strayalphaca.presentation.screens.diary.model.DiaryDate

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    onDiaryClick: (String) -> Unit = {},
    onEmptyDiaryClick: (String?, String?) -> Unit = { _, _ -> },
    viewModel: CalendarViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    var datePickerDialogShow by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(Int.MAX_VALUE / 2)

    if (datePickerDialogShow) {
        MonthPickerDialog(
            year = state.year,
            month = state.month,
            onDismissRequest = { datePickerDialogShow = false },
            onMonthSelect = viewModel::tryGetDiaryData
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = modifier.padding(vertical = 48.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(text = state.month.toString(), style = MaterialTheme.typography.h1)

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(text = state.year.toString(), style = MaterialTheme.typography.h2)
                }

                BaseIconButton(
                    iconResourceId = R.drawable.ic_calendar,
                    onClick = { datePickerDialogShow = true })
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
                        CalendarItemView(item = data, day = day, isToday = isToday,
                            modifier = Modifier.clickable { onDiaryClick(data.id) })
                    },
                    emptyView = { day, isToday ->
                        CalendarItemEmptyView(day = day, isToday = isToday,
                            modifier = Modifier.clickable {
                                onEmptyDiaryClick(
                                    null,
                                    DiaryDate(year = state.year, month = state.month, day = day).toString()
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
    }
}