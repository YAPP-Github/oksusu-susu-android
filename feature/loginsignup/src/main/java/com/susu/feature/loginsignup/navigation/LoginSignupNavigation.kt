package com.susu.feature.loginsignup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.susu.feature.loginsignup.VoteScreen
import com.susu.feature.loginsignup.login.LoginScreen
import com.susu.feature.loginsignup.signup.SignUpScreen

@Suppress("unused")
fun NavController.navigateLoginSignup(navOptions: NavOptions) {
    navigate(LoginSignupRoute.Parent.route, navOptions)
}

fun NavGraphBuilder.loginSignupNavGraph(
    navController: NavController,
    navigateToReceived: () -> Unit,
) {
    composable(route = LoginSignupRoute.Parent.Vote.route) {
        VoteScreen(
            navigateToLogin = { navController.navigate(LoginSignupRoute.Parent.Login.route) },
        )
    }
    composable(route = LoginSignupRoute.Parent.Login.route) {
        LoginScreen(
            navigateToReceived = navigateToReceived,
            navigateToSignUp = { navController.navigate(LoginSignupRoute.Parent.SignUp.route) },
        )
    }
    composable(route = LoginSignupRoute.Parent.SignUp.route) {
        SignUpScreen(
            navigateToReceived = navigateToReceived,
        )
    }
}

sealed class LoginSignupRoute(val route: String) {
    data object Parent : LoginSignupRoute("login-signup") {
        data object Vote : LoginSignupRoute("vote")
        data object Login : LoginSignupRoute("login")
        data object SignUp : LoginSignupRoute("signup")
    }
}
