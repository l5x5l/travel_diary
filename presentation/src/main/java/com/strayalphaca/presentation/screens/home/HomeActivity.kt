package com.strayalphaca.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.block.HomeBottomNavigation
import com.strayalphaca.presentation.models.BottomNavigationItem
import com.strayalphaca.presentation.screens.home.calendar.CalendarScreen
import com.strayalphaca.presentation.screens.home.map.MapScreen
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

@Composable
fun HomeScreen(
    goToDiary : (Int) -> Unit = {},
    goToSettings : () -> Unit = {}
) {
    TravelDiaryTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen = homeScreens.find { it.route == currentDestination?.route } ?: CalendarScreenDestination

        Column(modifier = Modifier.fillMaxSize()) {
            HomeNavHost(
                navHostController = navController,
                goToDiary = goToDiary,
                modifier = Modifier.weight(1f)
            )

            HomeBottomNavigation(
                menuList = listOf(
                    BottomNavigationItem(CalendarScreenDestination.route, R.drawable.ic_calendar),
                    BottomNavigationItem(MapScreenDestination.route, R.drawable.ic_map),
                    BottomNavigationItem("settings", R.drawable.ic_setting),
                ),
                onClick = {bottomMenuItem ->
                    if (bottomMenuItem.route == "settings") {
                        goToSettings()
                    } else {
                        navController.navigate(bottomMenuItem.route)
                    }
                },
                currentRoute = currentScreen.route
            )
        }

    }
}

@Composable
fun HomeNavHost(
    navHostController: NavHostController,
    modifier : Modifier = Modifier,
    goToDiary : (Int) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = CalendarScreenDestination.route,
        modifier = modifier
    ) {
        composable(
            route = CalendarScreenDestination.route
        ) {
            CalendarScreen(onDiaryClick = goToDiary)
        }

        composable(
            route = MapScreenDestination.route
        ) {
            MapScreen(onDiaryClick = goToDiary)
        }
    }
}