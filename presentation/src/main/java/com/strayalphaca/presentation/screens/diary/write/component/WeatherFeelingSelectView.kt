package com.strayalphaca.presentation.screens.diary.write.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.screens.diary.component.ContentIconImage
import com.strayalphaca.presentation.screens.diary.component.ContentSelectView
import com.strayalphaca.presentation.screens.diary.model.CurrentShowSelectView
import com.strayalphaca.presentation.screens.diary.util.getFeelingIconId
import com.strayalphaca.presentation.screens.diary.util.getWeatherIconId
import com.strayalphaca.travel_diary.diary.model.Feeling
import com.strayalphaca.travel_diary.diary.model.Weather

@Composable
fun WeatherFeelingSelectView(
    feeling: Feeling,
    weather: Weather,
    currentShowSelectView: CurrentShowSelectView?,
    setFeeling : (Feeling) -> Unit,
    setWeather : (Weather) -> Unit,
    setCurrentShowSelectView: (CurrentShowSelectView) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = stringResource(id = R.string.today_feeling),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp)
                )
                ContentIconImage(
                    iconId = getFeelingIconId(feeling),
                    descriptionText = feeling.name,
                    onClick = {
                        setCurrentShowSelectView(CurrentShowSelectView.FEELING)
                    }
                )
            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = stringResource(id = R.string.weather),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp)
                )
                ContentIconImage(
                    iconId = getWeatherIconId(weather),
                    descriptionText = weather.toString(),
                    onClick = {
                        setCurrentShowSelectView(CurrentShowSelectView.WEATHER)
                    }
                )
            }
        }

        AnimatedVisibility(currentShowSelectView == CurrentShowSelectView.WEATHER) {
            ContentSelectView(contentList = Weather.values().toList()) {
                ContentIconImage(
                    iconId = getWeatherIconId(it),
                    descriptionText = it.name,
                    onClick = {
                        setWeather(it)
                    }
                )
            }
        }

        AnimatedVisibility(currentShowSelectView == CurrentShowSelectView.FEELING) {
            ContentSelectView(contentList = Feeling.values().toList()) {
                ContentIconImage(
                    iconId = getFeelingIconId(it),
                    descriptionText = it.name,
                    onClick = {
                        setFeeling(it)
                    }
                )
            }
        }
    }

}