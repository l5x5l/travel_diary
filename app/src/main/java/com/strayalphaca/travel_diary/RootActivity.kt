package com.strayalphaca.travel_diary

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.strayalphaca.presentation.screens.diary.detail.DiaryDetailContainer
import com.strayalphaca.presentation.screens.diary.detail.DiaryDetailViewModel
import com.strayalphaca.presentation.screens.diary.write.DiaryWriteContainer
import com.strayalphaca.presentation.screens.diary.write.DiaryWriteViewModel
import com.strayalphaca.presentation.screens.diary_list.DiaryListScreenContainer
import com.strayalphaca.presentation.screens.diary_list.DiaryListViewModel
import com.strayalphaca.presentation.screens.home.HomeScreen
import com.strayalphaca.presentation.screens.intro.IntroScreen
import com.strayalphaca.presentation.screens.lock.LockScreen
import com.strayalphaca.presentation.screens.lock.LockViewModel
import com.strayalphaca.presentation.screens.login_home.loginNavGraph
import com.strayalphaca.presentation.screens.settings.SettingsBaseScreen
import com.strayalphaca.presentation.screens.video.VideoContainer
import com.strayalphaca.presentation.screens.video.VideoViewModel
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
                goToSettings = { navController.navigate(SettingsGraph.route) },
                goToDiaryList = { navController.navigateToDiaryList(it) }
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
            DiaryDetailContainer(
                id = diaryId ?: "",
                viewModel = viewModel,
                goBack = {navController.popBackStack()},
                goToVideo = { uri ->
                    navController.navigate("${Video.route}?${uri}")
                }
            )
        }

        composable(
            route = DiaryWrite.routeWithArgs,
            arguments = DiaryWrite.arguments,
            deepLinks = DiaryWrite.deepLinks
        ) { navBackStackEntry ->
            val viewModel = hiltViewModel<DiaryWriteViewModel>()
            val diaryId = navBackStackEntry.arguments?.getString(DiaryWrite.diaryId)
            DiaryWriteContainer(
                id = diaryId,
                viewModel = viewModel,
                goBack = {navController.popBackStack()},
                goToVideo = { uri ->
                    navController.navigate("${Video.route}?${uri}")
                }
            )
        }

        composable(
            route = Video.routeWithArgs,
            arguments = Video.arguments,
            deepLinks = Video.deepLinks
        ) { navBackStackEntry ->
            val viewModel = hiltViewModel<VideoViewModel>()
            val videoUri = navBackStackEntry.arguments?.getString(Video.videoUri)?.let { Uri.parse(it) }
            VideoContainer(viewModel = viewModel, uri = videoUri, goBack = {navController.popBackStack()})
        }

        composable(route = Lock.route) {
            val viewModel = hiltViewModel<LockViewModel>()
            LockScreen(
                backToContent = navController::popBackStack,
                viewModel = viewModel
            )
        }

        composable(route = Intro.route) {
            IntroScreen(
                goToHome = { navController.navigate(HomeGraph.route){
                        popUpTo(0) { inclusive = true }
                    }
                },
                goToLogin = { navController.navigate(LoginGraph.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = DiaryList.routeWithArgs,
            arguments = DiaryList.arguments,
            deepLinks = DiaryList.deepLinks
        ) { navBackStackEntry ->
            val viewModel = hiltViewModel<DiaryListViewModel>()
            DiaryListScreenContainer(
                moveToDiary = {
                    navController.navigateToDiaryDetail(it)
                },
                onBackPress = { navController.popBackStack() },
                initCityGroupId = navBackStackEntry.arguments?.getInt(DiaryList.cityGroupId) ?: -1,
                viewModel = viewModel
            )
        }

    }
}

//fun NavHostController.navigateSingleTopTo(route : String) =
//    this.navigate(route){
//        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id){
//            saveState = true
//        }
//        launchSingleTop = true
//        restoreState = true
//    }

private fun NavHostController.navigateToDiaryDetail(diaryId : String) =
    this.navigate("${DiaryDetail.route}/${diaryId}")

private fun NavHostController.navigateToDiaryWrite(diaryId : String?) {
    this.navigate("${DiaryWrite.route}/${diaryId}")
}

private fun NavHostController.navigateToDiaryList(cityGroupId : Int) {
    this.navigate("${DiaryList.route}/${cityGroupId}")
}