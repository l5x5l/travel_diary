package com.strayalphaca.presentation.screens.home

interface HomeDestinations {
    val route : String
}

object CalendarScreenDestination : HomeDestinations {
    override val route: String = "calendar"
}

object MapScreenDestination : HomeDestinations {
    override val route: String = "map"
}

val homeScreens = listOf(CalendarScreenDestination, MapScreenDestination)