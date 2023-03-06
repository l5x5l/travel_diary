package com.strayalphaca.presentation.screens.login_home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.strayalphaca.presentation.screens.login_home.login.LoginScreen
import com.strayalphaca.presentation.screens.login_home.signup.SignupScreen
import com.strayalphaca.presentation.screens.login_home.signup_password.SignupPasswordScreen
import com.strayalphaca.presentation.screens.login_home.singup_email.SignupEmailScreen
import com.strayalphaca.presentation.ui.theme.TravelDiaryTheme

class LoginHomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TravelDiaryTheme {
                val navController = rememberNavController()

                LoginNavHost(navController)
            }
        }
    }
}

@Composable
fun LoginNavHost(
    navController : NavHostController,
    modifier : Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = LoginScreenDestination.route,
        modifier = modifier
    ) {
        composable(route = LoginScreenDestination.route) {
            LoginScreen(
                navigateToBack = {navController.popBackStack()},
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

private fun NavHostController.navigateSingleTopTo(route : String) =
    this.navigate(route) {
        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id)
        launchSingleTop = true
    }