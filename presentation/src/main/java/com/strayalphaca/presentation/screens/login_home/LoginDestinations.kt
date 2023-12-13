package com.strayalphaca.presentation.screens.login_home

interface LoginDestinations {
    val route : String
}

object LoginScreenDestination : LoginDestinations {
    override val route: String = "login"
}

object SignupScreenDestination : LoginDestinations {
    override val route: String = "signup"
}

object SignupEmailScreenDestination : LoginDestinations {
    override val route: String = "signup_email"
}

object SignupPasswordScreenDestination : LoginDestinations {
    override val route: String = "signup_password"
}

object ResetPasswordScreenDestination : LoginDestinations {
    override val route: String = "reset_password"
}