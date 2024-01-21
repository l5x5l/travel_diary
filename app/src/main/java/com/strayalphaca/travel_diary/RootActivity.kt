package com.strayalphaca.travel_diary

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
import com.strayalphaca.presentation.screens.start.StartScreenContainer
import com.strayalphaca.presentation.screens.start.StartViewModel
import com.strayalphaca.presentation.screens.video.VideoContainer
import com.strayalphaca.presentation.screens.video.VideoViewModel
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme
import com.strayalphaca.presentation.utils.collectAsEffect
import com.strayalphaca.presentation.utils.collectLatestInScope
import com.strayalphaca.travel_diary.core.presentation.logger.ScreenLogEvent
import com.strayalphaca.travel_diary.core.presentation.logger.ScreenLogger
import com.strayalphaca.travel_diary.core.presentation.model.MessageTrigger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RootActivity : ComponentActivity() {

    private val viewModel : RootViewModel by viewModels()
    @Inject lateinit var screenLogger : ScreenLogger
    @Inject lateinit var messageTrigger: MessageTrigger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.data?.let { uri ->
            viewModel.handleLink(uri.toString())
        }
        setContent {
            TravelDiaryTheme {
                val navHostController = rememberNavController()
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val currentDestination by remember(navBackStackEntry) { derivedStateOf { navBackStackEntry?.destination } }

                LaunchedEffect(currentDestination) {
                    currentDestination?.route?.let { route ->
                        screenLogger.log(ScreenLogEvent(route))
                    }
                }

                viewModel.invalidRefreshToken.collectAsEffect { tokenErrorOccur ->
                    Toast.makeText(this, getString(com.strayalphaca.presentation.R.string.auth_error_401), Toast.LENGTH_SHORT).show()
                    if (tokenErrorOccur) navHostController.navigateToIntroTop()
                }

                RootNavHost(navController = navHostController)
            }
        }

        initObserver()
    }

    private fun initObserver() {
        messageTrigger.message.collectLatestInScope(lifecycleScope) {message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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
        startDestination = Start.route,
    ) {

        composable(Start.route) {
            val viewModel = hiltViewModel<StartViewModel>()
            StartScreenContainer(
                viewModel = viewModel,
                goToHome = {
                    navController.navigate(HomeGraph.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                goToIntro = {
                    navController.navigate(Intro.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(HomeGraph.route) {
            HomeScreen(
                goToDiary = { navController.navigateToDiaryDetail(it) },
                goToDiaryWrite = { id, dateString ->
                    navController.navigateToDiaryWrite(id, dateString)
                },
                goToSettings = { navController.navigate(SettingsGraph.route) },
                goToDiaryList = { navController.navigateToDiaryList(it) }
            )
        }

        composable(SettingsGraph.route) {
            SettingsBaseScreen(
                exitSettingNav = { navController.popBackStack() },
                goToLogin = { navController.navigate(LoginGraph.route) },
                goToIntro = {
                    navController.navigateToIntroTop()
                }
            )
        }

        loginNavGraph(
            navController = navController,
            onExitLogin = { navController.popBackStack() },
            navigateToHome = {
                navController.navigate(HomeGraph.route) {
                    popUpTo(0) { inclusive = true }
                }
            }
        )

        composable(
            route = DiaryDetail.routeWithArgs,
            arguments = DiaryDetail.arguments,
            deepLinks = DiaryDetail.deepLinks
        ) { navBackStackEntry ->
            val viewModel = hiltViewModel<DiaryDetailViewModel>()
            val diaryId = navBackStackEntry.arguments?.getString(DiaryDetail.diaryId)
            val refresh = navBackStackEntry.savedStateHandle.get<Boolean>("modify_success") ?: false
            DiaryDetailContainer(
                id = diaryId ?: "",
                viewModel = viewModel,
                goBack = {navController.popBackStack()},
                goBackWithDeleteSuccess = {
                    navController.previousBackStackEntry?.savedStateHandle?.set("delete_diary_id", diaryId)
                    navController.popBackStack()
                },
                goToVideo = { uri ->
                    navController.navigate("${Video.route}?${uri}")
                },
                goToDiaryModify = { id ->
                    navController.navigateToDiaryWrite(id, null)
                },
                needRefresh = refresh
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
                },
                goBackWithModifySuccessResult = {
                    navController.previousBackStackEntry?.savedStateHandle?.set("modify_success", true)
                    navController.popBackStack()
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
                goToLogin = { navController.navigate(LoginGraph.route) }
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

private fun NavHostController.navigateToIntroTop() =
    this.navigate(Intro.route) {
        popUpTo(0) { inclusive = true }
    }

private fun NavHostController.navigateToDiaryDetail(diaryId : String) =
    this.navigate("${DiaryDetail.route}/${diaryId}")

private fun NavHostController.navigateToDiaryWrite(diaryId : String?, dateString : String?) {
    this.navigate("${DiaryWrite.route}/${diaryId}&${dateString}")
}

private fun NavHostController.navigateToDiaryList(cityGroupId : Int) {
    this.navigate("${DiaryList.route}/${cityGroupId}")
}