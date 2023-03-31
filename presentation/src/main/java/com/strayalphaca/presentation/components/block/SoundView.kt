package com.strayalphaca.presentation.components.block

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import com.strayalphaca.presentation.R

@Composable
fun SoundView(
    file : ByteArray
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Surface(modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .shadow(elevation = 6.dp)
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = stringResource(id = R.string.recording_file), style = MaterialTheme.typography.body2)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Slider(
                    value = 0.5f, onValueChange = {},
                    colors = SliderDefaults.colors(
                        thumbColor = MaterialTheme.colors.onBackground,
                        activeTrackColor = MaterialTheme.colors.onBackground
                    )
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
fun SoundViewPreview() {
    TravelDiaryTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            SoundView(byteArrayOf())
        }
    }
}
