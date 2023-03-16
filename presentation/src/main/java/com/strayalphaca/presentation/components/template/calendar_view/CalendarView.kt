package com.strayalphaca.presentation.components.template.calendar_view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import com.strayalphaca.presentation.utils.checkToday
import com.strayalphaca.presentation.utils.firstDaysOfNextMonth
import com.strayalphaca.presentation.utils.lastDaysOfPrevMonth

@Composable
fun <T> CalendarView(
    modifier: Modifier = Modifier, showWeekDays: Boolean = true,
    year : Int, month : Int, calendarData: List<T?>,
    contentView : @Composable (T, Int, Boolean)-> Unit,
    emptyView : @Composable (Int, Boolean) -> Unit,
    outRangeView : @Composable (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        content = {
            if (showWeekDays) {
                items(7) {
                    Text(text = "day", textAlign = TextAlign.Center, style = MaterialTheme.typography.caption)
                }
            }

            itemsIndexed(lastDaysOfPrevMonth(year, month)) { _, date ->
                outRangeView(date + 1)
            }

            itemsIndexed(calendarData) { index, item ->
                if (item != null)
                    contentView(item, index + 1, checkToday(year, month, index + 1))
                else
                    emptyView(index + 1, checkToday(year, month, index + 1))
            }

            // 다음 달 날짜
            itemsIndexed(firstDaysOfNextMonth(year, month)) { _, date ->
                outRangeView(date + 1)
            }
        },
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    )
}

@Preview(
    showBackground = true,
    widthDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "dark"
)
@Preview(showBackground = true, widthDp = 360)
@Composable
fun CalendarViewPreview() {
    TravelDiaryTheme {
        Surface {
//            CalendarView(
//                year = 2023,
//                month = 3,
//                calendarData = listOf(
//                    1, null, 3, 4, null, 6, 7,
//                    8, null, 10, 11, null, 13, 14,
//                    15, 16, null, null, null, null, null,
//                    22, 23, null, 25, 26, null, 28, 29, null, 31
//                ),
//                contentView = { data, day, isToday ->
//                    CalendarItemView(item = data, day = day, today = isToday)
//                },
//                emptyView = { day, isToday ->
//                    CalendarItemView(item = null, day = day, today = isToday)
//                })
        }
    }
}