package com.strayalphaca.travel_diary

interface RootDestinations {
    val route : String
}

object HomeGraph : RootDestinations {
    override val route: String = "home_graph"
}

object SettingsGraph : RootDestinations {
    override val route: String = "settings_graph"
}

object LoginGraph : RootDestinations {
    override val route: String = "login_graph"
}