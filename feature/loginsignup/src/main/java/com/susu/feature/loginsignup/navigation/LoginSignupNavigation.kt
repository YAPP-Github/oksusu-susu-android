package com.susu.feature.loginsignup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.susu.feature.loginsignup.login.LoginScreen
import com.susu.feature.loginsignup.signup.SignUpScreen
import com.susu.feature.loginsignup.test.TestScreen

@Suppress("unused")
fun NavController.navigateLoginSignup(navOptions: NavOptions) {
    navigate(LoginSignupRoute.Parent.route, navOptions)
}

fun NavGraphBuilder.loginSignupNavGraph(navController: NavController) {
    navigation(startDestination = LoginSignupRoute.Parent.Login.route, route = LoginSignupRoute.Parent.route) {
        composable(route = LoginSignupRoute.Parent.Login.route) {
            LoginScreen(
                navigateToReceived = { navController.navigate(LoginSignupRoute.Parent.Test.route) },
                navigateToSignUp = { navController.navigate(LoginSignupRoute.Parent.SignUp.route) },
            )
        }
        composable(route = LoginSignupRoute.Parent.SignUp.route) {
            SignUpScreen(
                navigateToReceived = { navController.navigate(LoginSignupRoute.Parent.Test.route) },
            )
        }
        composable(route = LoginSignupRoute.Parent.Test.route) {
            TestScreen(
                navigateToLogin = { navController.popBackStack() },
            )
        }
    }
}

sealed class LoginSignupRoute(val route: String) {
    data object Parent : LoginSignupRoute("login-signup") {
        data object Login : LoginSignupRoute("login")
        data object SignUp : LoginSignupRoute("signup")
        data object Test : LoginSignupRoute("test")
    }
}
