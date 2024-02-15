package com.strayalphaca.presentation.components.block

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.atom.base_icon_button.BaseIconButton
import com.strayalphaca.presentation.ui.theme.Gray4

@Composable
fun SoundView(
    file : Uri,
    playing : Boolean = false,
    play : () -> Unit = {},
    pause : () -> Unit = {},
    remove : (() -> Unit)? = null,
    soundProgressChange : (Float) -> Unit = {},
    soundProgress : Float = 0f,
    isError : Boolean = false
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(modifier = Modifier.padding(start = 12.dp), text = stringResource(id = R.string.recording_file), style = MaterialTheme.typography.body2)

                    Row {
                        if (!isError) {
                            if (playing) {
                                BaseIconButton(
                                    iconResourceId = R.drawable.ic_pause,
                                    onClick = pause
                                )
                            } else {
                                BaseIconButton(
                                    iconResourceId = R.drawable.ic_play_arrow,
                                    onClick = play
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        remove?.let {
                            BaseIconButton(
                                iconResourceId = R.drawable.ic_close,
                                onClick = remove
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (isError) {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        textAlign = TextAlign.Center,
                        color = Gray4,
                        style = MaterialTheme.typography.caption,
                        text = "음성파일 로드 중 문제가 발생했습니다."
                    )
                } else {
                    Slider(
                        value = soundProgress,
                        onValueChange = {
                            soundProgressChange(it)
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colors.onBackground,
                            activeTrackColor = MaterialTheme.colors.onBackground
                        )
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
fun SoundViewPreview() {
    TravelDiaryTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            SoundView(Uri.EMPTY, isError = true)
        }
    }
}
