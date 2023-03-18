package com.strayalphaca.travel_diary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.strayalphaca.presentation.screens.diary.detail.DiaryDetailScreen
import com.strayalphaca.presentation.screens.diary.write.DiaryWriteScreen
import com.strayalphaca.presentation.screens.home.HomeScreen
import com.strayalphaca.presentation.screens.login_home.loginNavGraph
import com.strayalphaca.presentation.screens.settings.SettingsBaseScreen
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RootActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TravelDiaryTheme {
                val navHostController = rememberNavController()

                RootNavHost(navController = navHostController)
            }
        }
    }
}

@Composable
fun RootNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = HomeGraph.route,
    ) {
        composable(HomeGraph.route) {
            HomeScreen(
                goToDiary = { navController.navigate(DiaryDetail.route) },
                goToDiaryWrite = { navController.navigate(DiaryWrite.route) },
                goToSettings = { navController.navigate(SettingsGraph.route) }
            )
        }

        composable(SettingsGraph.route) {
            SettingsBaseScreen(
                exitSettingNav = { navController.popBackStack() },
                goToLogin = { navController.navigate(LoginGraph.route) }
            )
        }

        loginNavGraph(
            navController = navController,
            onExitLogin = { navController.popBackStack() }
        )

        composable(DiaryDetail.route) {
            DiaryDetailScreen()
        }

        composable(DiaryWrite.route) {
            DiaryWriteScreen()
        }
    }
}