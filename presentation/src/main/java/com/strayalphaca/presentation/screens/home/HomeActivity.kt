package com.strayalphaca.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.strayalphaca.presentation.R
import com.strayalphaca.presentation.components.block.HomeBottomNavigation
import com.strayalphaca.presentation.models.BottomNavigationItem
import com.strayalphaca.presentation.screens.home.calendar.CalendarScreen
import com.strayalphaca.presentation.screens.home.calendar.CalendarViewModel
import com.strayalphaca.presentation.screens.home.map.MapScreen
import com.strayalphaca.presentation.screens.home.map.MapViewModel
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

private fun NavHostController.navigateToHome(route : String) =
    this.navigate(route) {
        popUpTo(this@navigateToHome.graph.findStartDestination().id)
        launchSingleTop = true
    }
@Composable
fun HomeScreen(
    goToDiary : (String) -> Unit = {},
    goToDiaryWrite : (String?) -> Unit = {},
    goToSettings : () -> Unit = {},
    goToDiaryList: (Int) -> Unit
) {
    TravelDiaryTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen = homeScreens.find { it.route == currentDestination?.route } ?: CalendarScreenDestination

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            HomeNavHost(
                navHostController = navController,
                goToDiary = goToDiary,
                goToDiaryWrite = goToDiaryWrite,
                goToDiaryList = goToDiaryList,
                modifier = Modifier.weight(1f)
            )

            HomeBottomNavigation(
                menuList = listOf(
                    BottomNavigationItem(CalendarScreenDestination.route, R.drawable.ic_calendar),
                    BottomNavigationItem(MapScreenDestination.route, R.drawable.ic_map),
                    BottomNavigationItem("settings", R.drawable.ic_setting),
                ),
                onClick = {bottomMenuItem ->
                    when (bottomMenuItem.route) {
                        "settings" -> {
                            goToSettings()
                        }
                        CalendarScreenDestination.route -> {
                            navController.navigateToHome(bottomMenuItem.route)
                        }
                        else -> {
                            navController.navigate(bottomMenuItem.route)
                        }
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
    goToDiary : (String) -> Unit,
    goToDiaryWrite : (String?) -> Unit,
    goToDiaryList : (Int) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = CalendarScreenDestination.route,
        modifier = modifier
    ) {
        composable(
            route = CalendarScreenDestination.route
        ) {
            val calendarViewModel = hiltViewModel<CalendarViewModel>()
            CalendarScreen(
                onDiaryClick = goToDiary,
                onEmptyDiaryClick = goToDiaryWrite,
                viewModel = calendarViewModel
            )
        }

        composable(
            route = MapScreenDestination.route
        ) {
            val mapViewModel = hiltViewModel<MapViewModel>()
            MapScreen(
                goToDiaryList = goToDiaryList,
                viewModel = mapViewModel
            )
        }
    }
}