package com.strayalphaca.presentation.screens.home.calendar

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.strayalpaca.travel_diary.core.domain.model.DiaryDate
import com.strayalphaca.presentation.components.template.dialog.MonthPickerDialog
import com.strayalphaca.presentation.screens.home.calendar.component.CalendarUi
import com.strayalphaca.presentation.screens.home.calendar.component.ToolButtons
import com.strayalphaca.presentation.screens.home.calendar.component.YearMonthText
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import com.strayalphaca.presentation.utils.UseFinishByBackPressTwice
import com.strayalphaca.presentation.utils.collectLatestInScope
import com.strayalphaca.travel_diary.domain.calendar.utils.fillEmptyCellToCalendarData

@Composable
fun CalendarScreen(
    modifier: Modifier = Modifier,
    goToDiaryDetail: (String) -> Unit = {},
    goToDiaryWrite: (String?, String?) -> Unit = { _, _ -> },
    viewModel: CalendarViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var datePickerDialogShow by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val composeScope = rememberCoroutineScope()

    UseFinishByBackPressTwice()

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
        BoxWithConstraints {
            if (maxWidth < 600.dp) {
                Column(modifier = modifier.padding(vertical = 48.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        YearMonthText(
                            modifier = Modifier.weight(1f),
                            year = state.year,
                            month = state.month
                        )

                        ToolButtons(
                            onClickCalendarButton = { datePickerDialogShow = true },
                            onClickWrite = viewModel::checkTodayWrite,
                            enable = state.clickEnable
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    CalendarUi(
                        goToDiaryDetail = goToDiaryDetail,
                        goToDiaryWrite = goToDiaryWrite,
                        year = state.year,
                        month = state.month,
                        diaryData = state.diaryData,
                        enable = state.clickEnable
                    )
                }
            } else {
                Row(
                    modifier = Modifier.padding(24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column(
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Spacer(modifier = Modifier.height(128.dp))

                        ToolButtons(
                            onClickCalendarButton = { datePickerDialogShow = true },
                            onClickWrite = viewModel::checkTodayWrite,
                            enable = state.clickEnable
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        YearMonthText(
                            year = state.year,
                            month = state.month
                        )
                    }

                    Spacer(modifier = Modifier.width(60.dp))

                    CalendarUi(
                        goToDiaryDetail = goToDiaryDetail,
                        goToDiaryWrite = goToDiaryWrite,
                        year = state.year,
                        month = state.month,
                        diaryData = state.diaryData,
                        enable = state.clickEnable
                    )
                }
            }
        }


    }
}


@Composable
@Preview(
    showBackground = true,
    widthDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "dark"
)
@Preview(showBackground = true, widthDp = 360)
fun CalendarScreenPreview() {
    TravelDiaryTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.padding(vertical = 48.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    YearMonthText(
                        modifier = Modifier.weight(1f),
                        year = 2023,
                        month = 12
                    )

                    ToolButtons(enable = true)
                }

                Spacer(modifier = Modifier.height(32.dp))

                CalendarUi(
                    diaryData = fillEmptyCellToCalendarData(2023, 12, emptyList())
                )
            }
        }
    }
}


@Composable
@Preview(
    showBackground = true,
    widthDp = 690,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "dark"
)
@Preview(showBackground = true, widthDp = 690)
fun CalendarScreenTabletPreview() {
    TravelDiaryTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.padding(24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Column(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Spacer(modifier = Modifier.height(128.dp))

                    ToolButtons(enable = true)

                    Spacer(modifier = Modifier.height(12.dp))

                    YearMonthText(
                        year = 2023,
                        month = 12
                    )
                }

                Spacer(modifier = Modifier.width(60.dp))

                CalendarUi(
                    modifier = Modifier.weight(1f),
                    diaryData = fillEmptyCellToCalendarData(2023, 12, emptyList())
                )
            }


        }
    }
}