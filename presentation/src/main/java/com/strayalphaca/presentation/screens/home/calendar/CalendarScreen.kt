package com.strayalphaca.presentation.screens.home.calendar

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.strayalphaca.presentation.components.block.CalendarItemEmptyView
import com.strayalphaca.presentation.components.block.CalendarItemView
import com.strayalphaca.presentation.components.template.calendar_view.CalendarView
import com.strayalphaca.presentation.components.template.dialog.MonthPickerDialog
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_icon_button.BaseIconButton
import com.strayalphaca.travel_diary.diary.model.DiaryDate
import com.strayalphaca.presentation.utils.collectLatestInScope

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    goToDiaryDetail: (String) -> Unit = {},
    goToDiaryWrite: (String?, String?) -> Unit = { _, _ -> },
    viewModel: CalendarViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var datePickerDialogShow by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(Int.MAX_VALUE / 2)
    val context = LocalContext.current
    val composeScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.goDiaryWriteNavigationEvent.collectLatestInScope(composeScope) { goDiaryWriteNavigationEvent ->
            if (goDiaryWriteNavigationEvent)
                goToDiaryWrite(null, DiaryDate.getInstanceFromCalendar().toString())
        }

        viewModel.toastMessage.collectLatestInScope(composeScope) { toastMessage ->
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }


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
                    iconResourceId = R.drawable.ic_write,
                    onClick = {
                        if (!state.clickEnable) return@BaseIconButton
                        viewModel.checkTodayWrite()
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                BaseIconButton(
                    iconResourceId = R.drawable.ic_calendar,
                    onClick = {
                        if (!state.clickEnable) return@BaseIconButton
                        datePickerDialogShow = true
                    }
                )
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
                            modifier = Modifier.clickable {
                                if (!state.clickEnable) return@clickable
                                goToDiaryDetail(data.id)
                            })
                    },
                    emptyView = { day, isToday ->
                        CalendarItemEmptyView(day = day, isToday = isToday,
                            modifier = Modifier.clickable {
                                if (!state.clickEnable) return@clickable
                                goToDiaryWrite(
                                    null,
                                    DiaryDate(year = state.year, month = state.month, day = day).toString()
                                )
                            }
                        )
                    },
                    outRangeView = { day ->
                        if (!state.clickEnable) return@CalendarView
                        CalendarItemEmptyView(day = day, isToday = false, isCurrentMonth = false)
                    }
                )
            }


        }
    }
}