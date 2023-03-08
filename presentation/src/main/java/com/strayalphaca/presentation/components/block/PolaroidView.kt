package com.strayalphaca.presentation.components.block

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.ui.theme.Gray4
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun PolaroidView(
    imageUrl : String = "",
    isVideo : Boolean = false,
    dateString : String = "2023.02.10_18:34",
    positionString : String = "1/1"
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .shadow(elevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(Color.Cyan)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = dateString, style = MaterialTheme.typography.caption, color = Gray4)
                    Text(text = positionString, style = MaterialTheme.typography.caption, color = Gray4)
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
fun PolaroidViewPreview() {
    TravelDiaryTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            
            PolaroidView()
        }
    }
}
