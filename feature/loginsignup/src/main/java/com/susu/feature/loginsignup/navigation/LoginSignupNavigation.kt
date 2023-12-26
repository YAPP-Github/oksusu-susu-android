package com.susu.feature.loginsignup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.susu.feature.loginsignup.LoginSignupScreen

@Suppress("unused")
fun NavController.navigateLoginSignup(navOptions: NavOptions) {
    navigate(LoginSignupRoute.route, navOptions)
}

fun NavGraphBuilder.loginSignupNavGraph() {
    composable(route = LoginSignupRoute.route) {
        LoginSignupScreen()
    }
}

object LoginSignupRoute {
    const val route = "login-signup"
}
