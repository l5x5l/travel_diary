package com.strayalphaca.presentation.screens.login_home

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.strayalphaca.presentation.screens.login_home.login.LoginScreen
import com.strayalphaca.presentation.screens.login_home.signup.SignupScreen
import com.strayalphaca.presentation.screens.login_home.signup_password.SignupPasswordScreen
import com.strayalphaca.presentation.screens.login_home.singup_email.SignupEmailScreen

private fun NavHostController.navigateSingleTopTo(route : String) =
    this.navigate(route) {
        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id)
        launchSingleTop = true
    }

fun NavGraphBuilder.loginNavGraph(
    navController: NavHostController,
    onExitLogin : () -> Unit = {}
) {
    navigation(
        route = "login_graph",
        startDestination = LoginScreenDestination.route
    ){
        composable(route = LoginScreenDestination.route) {
            LoginScreen(
                navigateToBack = onExitLogin,
                navigateToSignup = {navController.navigate(SignupScreenDestination.route)}
            )
        }

        composable(route = SignupScreenDestination.route) {
            SignupScreen(
                navigateToLogin = {navController.navigateSingleTopTo(LoginScreenDestination.route)},
                navigateToSignupEmail = {navController.navigate(SignupEmailScreenDestination.route)}
            )
        }

        composable(route = SignupEmailScreenDestination.route) {
            SignupEmailScreen(
                navigateToSignup = {navController.navigateSingleTopTo(SignupScreenDestination.route)},
                navigateToPassword = {navController.navigate(SignupPasswordScreenDestination.route)}
            )
        }

        composable(route = SignupPasswordScreenDestination.route) {
            SignupPasswordScreen(
                navigateToLogin = {navController.navigateSingleTopTo(LoginScreenDestination.route)},
                navigateToSignup = {navController.navigateSingleTopTo(SignupScreenDestination.route)},
            )
        }
    }
}