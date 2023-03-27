package com.strayalphaca.travel_diary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.strayalphaca.presentation.screens.diary.detail.DiaryDetailScreen
import com.strayalphaca.presentation.screens.diary.detail.DiaryDetailViewModel
import com.strayalphaca.presentation.screens.diary.write.DiaryWriteScreen
import com.strayalphaca.presentation.screens.diary.write.DiaryWriteViewModel
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
                goToDiary = { navController.navigateToDiaryDetail(it) },
                goToDiaryWrite = { navController.navigateToDiaryWrite(it) },
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

        composable(
            route = DiaryDetail.routeWithArgs,
            arguments = DiaryDetail.arguments,
            deepLinks = DiaryDetail.deepLinks
        ) { navBackStackEntry ->
            val viewModel = hiltViewModel<DiaryDetailViewModel>()
            val diaryId = navBackStackEntry.arguments?.getString(DiaryDetail.diaryId)
            DiaryDetailScreen(id = diaryId ?: "", viewModel = viewModel)
        }

        composable(
            route = DiaryWrite.routeWithArgs,
            arguments = DiaryWrite.arguments,
            deepLinks = DiaryWrite.deepLinks
        ) { navBackStackEntry ->
            val viewModel = hiltViewModel<DiaryWriteViewModel>()
            val diaryId = navBackStackEntry.arguments?.getString(DiaryWrite.diaryId)
            DiaryWriteScreen(id = diaryId, viewModel = viewModel)
        }
    }
}

fun NavHostController.navigateSingleTopTo(route : String) =
    this.navigate(route){
        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id){
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

private fun NavHostController.navigateToDiaryDetail(diaryId : String) =
    this.navigateSingleTopTo("${DiaryDetail.route}/${diaryId}")

private fun NavHostController.navigateToDiaryWrite(diaryId : String?) {
    this.navigateSingleTopTo("${DiaryWrite.route}/${diaryId}")
}
