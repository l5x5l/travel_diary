package com.strayalphaca.presentation.screens.diary.util

import com.strayalphaca.domain.diary.model.Feeling
import com.strayalphaca.domain.diary.model.Weather
import com.strayalphaca.presentation.R

fun getWeatherIconId(weather: Weather) : Int {
    return when (weather) {
        Weather.SUNNY -> R.drawable.ic_weather_sunny
        Weather.PARTLY_CLOUDY -> R.drawable.ic_weather_partly_cloudy
        Weather.CLOUDY -> R.drawable.ic_weather_cloudy
        Weather.STORMY -> R.drawable.ic_weather_stormy
        Weather.RAINY -> R.drawable.ic_weather_rainy
        Weather.SNOWY -> R.drawable.ic_weather_snowy
        Weather.WINDY -> R.drawable.ic_weather_windy
    }
}

fun getFeelingIconId(feeling: Feeling) : Int {
    return when (feeling) {
        Feeling.HAPPY -> R.drawable.ic_feeling_happy
        Feeling.CALM -> R.drawable.ic_feeling_calm
        Feeling.SATISFIED -> R.drawable.ic_feeling_satisfied
        Feeling.EXCITING -> R.drawable.ic_feeling_exciting
        Feeling.ANGRY -> R.drawable.ic_feeling_angry
        Feeling.SAD -> R.drawable.ic_feeling_sad
    }
}