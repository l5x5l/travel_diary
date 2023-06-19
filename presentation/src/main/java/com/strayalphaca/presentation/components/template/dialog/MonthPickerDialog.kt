package com.strayalphaca.presentation.components.template.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_icon_button.BaseIconButton
import com.strayalphaca.presentation.models.YearMonth
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun MonthPickerDialog(
    year: Int, month: Int, onMonthSelect: (Int, Int) -> Unit
) {
    var currentYear by remember { mutableStateOf(year) }
    var selectedMonthYear by remember { mutableStateOf(YearMonth(year, month)) }

    CustomDialog(onDismissRequest = {}) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.padding(start = 34.dp, top = 24.dp),
                text = stringResource(id = R.string.select_date),
                style = MaterialTheme.typography.caption
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 34.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = currentYear.toString(),
                    style = MaterialTheme.typography.h2
                )

                BaseIconButton(iconResourceId = R.drawable.ic_before, onClick = { currentYear-- })

                BaseIconButton(iconResourceId = R.drawable.ic_next, onClick = { currentYear++ })
            }

            Spacer(modifier = Modifier.height(8.dp))

            Divider(thickness = 1.dp, color = MaterialTheme.colors.onSurface)

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp, vertical = 18.dp),
                columns = GridCells.Fixed(3)
            ) {
                items(12) {
                    Text(
                        modifier = Modifier
                            .background(
                                color = if (currentYear == selectedMonthYear.year && it == selectedMonthYear.month) MaterialTheme.colors.onSurface else MaterialTheme.colors.surface
                            )
                            .clickable {
                                selectedMonthYear = YearMonth(currentYear, it)
                            }
                            .padding(vertical = 8.dp)
                        ,
                        text = (it + 1).toString(),
                        style = MaterialTheme.typography.body1,
                        color = if (currentYear == selectedMonthYear.year && it == selectedMonthYear.month) MaterialTheme.colors.surface else MaterialTheme.colors.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Row(
                modifier = Modifier.align(Alignment.End).padding(end = 12.dp, bottom = 12.dp)
            ) {
                TextButton(
                    onClick = {}
                ) {
                    Text(text = stringResource(id = R.string.cancel), color = MaterialTheme.colors.onSurface)
                }

                TextButton(
                    onClick = {}
                ) {
                    Text(text = stringResource(id = R.string.confirm), color = MaterialTheme.colors.onSurface)
                }
            }
        }
    }
}

@Preview
@Composable
fun MonthPickerDialogPreview() {
    TravelDiaryTheme() {

        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)) {

            MonthPickerDialog(2023, 4) { year, month ->
                println("$year $month")
            }
        }

    }
}