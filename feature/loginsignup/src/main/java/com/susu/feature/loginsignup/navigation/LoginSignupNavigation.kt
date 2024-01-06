package com.susu.feature.loginsignup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.susu.feature.loginsignup.VoteRoute
import com.susu.feature.loginsignup.login.LoginRoute
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
        VoteRoute(
            navigateToLogin = {
                navController.navigate(LoginSignupRoute.Parent.Login.route) {
                    popUpTo(
                        route = LoginSignupRoute.Parent.Vote.route
                    ) {
                        inclusive = true
                    }
                }
            },
        )
    }
    composable(route = LoginSignupRoute.Parent.Login.route) {
        LoginRoute(
            navigateToReceived = navigateToReceived,
            navigateToSignUp = {
                navController.navigate(LoginSignupRoute.Parent.SignUp.route) {
                    popUpTo(
                        route = LoginSignupRoute.Parent.SignUp.route
                    ) {
                        inclusive = true
                    }
                }
            },
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
