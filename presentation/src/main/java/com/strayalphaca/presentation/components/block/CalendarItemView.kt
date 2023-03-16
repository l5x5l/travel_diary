package com.strayalphaca.presentation.components.block

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.domain.calendar.model.DiaryInCalendar
import com.strayalphaca.presentation.ui.theme.Gray4
import com.strayalphaca.presentation.ui.theme.Tape
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun CalendarItemView( modifier: Modifier = Modifier, item: DiaryInCalendar, day : Int, isToday : Boolean = false) {
    Surface(
        modifier = modifier
            .padding(2.dp)
            .shadow(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.8f)
                    .background(Tape)
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun CalendarItemEmptyView(modifier: Modifier = Modifier, day : Int, isToday : Boolean = false, isCurrentMonth : Boolean = true) {


    Box(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 9.dp)
    ) {
        Box( modifier = Modifier.aspectRatio(0.8f), contentAlignment = Alignment.Center){
            Text(text = day.toString(), color = if (isCurrentMonth) MaterialTheme.colors.onBackground else Gray4 )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "dark"
)
@Preview(showBackground = true, widthDp = 360)
@Composable
fun CalendarItemViewPreview() {
    TravelDiaryTheme {
        Row() {
//            CalendarItemView(modifier = Modifier.width(40.dp), item = DiaryInCalendar(1), day = 1)

            CalendarItemEmptyView(modifier = Modifier.width(40.dp), day = 5)

        }
    }
}