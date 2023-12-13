package com.strayalphaca.presentation.screens.login_home

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.strayalphaca.presentation.screens.login_home.login.LoginScreen
import com.strayalphaca.presentation.screens.login_home.login.LoginViewModel
import com.strayalphaca.presentation.screens.login_home.reissue_password.ResetPasswordScreen
import com.strayalphaca.presentation.screens.login_home.reissue_password.ResetPasswordViewModel
import com.strayalphaca.presentation.screens.login_home.signup.SignupScreen
import com.strayalphaca.presentation.screens.login_home.signup_password.SignupPasswordScreen
import com.strayalphaca.presentation.screens.login_home.signup_password.SignupPasswordViewModel
import com.strayalphaca.presentation.screens.login_home.singup_email.SignupEmailScreen
import com.strayalphaca.presentation.screens.login_home.singup_email.SignupEmailViewModel

private fun NavHostController.navigateSingleTopTo(route : String) {
    this.navigate(route) {
        popUpTo(LoginScreenDestination.route)
        launchSingleTop = true
    }
}

fun NavGraphBuilder.loginNavGraph(
    navController: NavHostController,
    onExitLogin : () -> Unit = {},
    navigateToHome : () -> Unit = {}
) {
    navigation(
        route = "login_graph",
        startDestination = LoginScreenDestination.route
    ){
        composable(route = LoginScreenDestination.route) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                navigateToBack = onExitLogin,
                navigateToSignup = {navController.navigate(SignupScreenDestination.route)},
                navigateToHome = navigateToHome,
                navigateToResetPassword = {navController.navigate(ResetPasswordScreenDestination.route)},
                viewModel = loginViewModel
            )
        }

        composable(route = SignupScreenDestination.route) {
            SignupScreen(
                navigateToLogin = {navController.navigateSingleTopTo(LoginScreenDestination.route)},
                navigateToSignupEmail = {navController.navigate(SignupEmailScreenDestination.route)}
            )
        }

        composable(route = SignupEmailScreenDestination.route) {
            val signupEmailViewModel = hiltViewModel<SignupEmailViewModel>()
            SignupEmailScreen(
                navigateToSignup = {navController.navigateSingleTopTo(SignupScreenDestination.route)},
                navigateToPassword = {navController.navigate(SignupPasswordScreenDestination.route)},
                viewModel = signupEmailViewModel
            )
        }

        composable(route = SignupPasswordScreenDestination.route) {
            val signupPasswordViewModel = hiltViewModel<SignupPasswordViewModel>()
            SignupPasswordScreen(
                navigateToLogin = {navController.navigateSingleTopTo(LoginScreenDestination.route)},
                navigateToSignup = {navController.navigateSingleTopTo(SignupScreenDestination.route)},
                viewModel = signupPasswordViewModel
            )
        }

        composable(route = ResetPasswordScreenDestination.route) {
            val resetPasswordViewModel = hiltViewModel<ResetPasswordViewModel>()
            ResetPasswordScreen(
                navigateToLogin = {navController.navigateSingleTopTo(LoginScreenDestination.route)},
                viewModel = resetPasswordViewModel
            )
        }
    }
}