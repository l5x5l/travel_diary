package com.strayalphaca.presentation.components.block

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.ui.theme.Gray4
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun EmptySoundView(
    modifier : Modifier = Modifier,
    onClick : () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Surface(modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                onClick()
            }
            .dashedBorder(
                color = Gray4,
                strokeWidth = 1.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_music),
                    contentDescription = "add audio",
                    colorFilter = ColorFilter.tint(Gray4)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = stringResource(id = R.string.add_audio),
                    style = MaterialTheme.typography.caption,
                    color = Gray4,
                    textAlign = TextAlign.Center
                )
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
fun EmptySoundViewPreview() {
    TravelDiaryTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            EmptySoundView()
        }
    }
}