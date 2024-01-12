package com.susu.feature.loginsignup.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.susu.feature.loginsignup.VoteRoute
import com.susu.feature.loginsignup.login.LoginRoute
import com.susu.feature.loginsignup.signup.SignUpRoute

@Suppress("unused")
fun NavController.navigateLoginSignup(navOptions: NavOptions) {
    navigate(LoginSignupRoute.Parent.route, navOptions)
}

fun NavGraphBuilder.loginSignupNavGraph(
    padding: PaddingValues,
    navigateToLogin: () -> Unit,
    navigateToSignUp: () -> Unit,
    navigateToReceived: () -> Unit,
) {
    composable(route = LoginSignupRoute.Parent.Vote.route) {
        VoteRoute(
            navigateToLogin = navigateToLogin,
        )
    }
    composable(route = LoginSignupRoute.Parent.Login.route) {
        LoginRoute(
            navigateToReceived = navigateToReceived,
            navigateToSignUp = navigateToSignUp,
        )
    }
    composable(route = LoginSignupRoute.Parent.SignUp.route) {
        SignUpRoute(
            padding = padding,
            navigateToReceived = navigateToReceived,
            navigateToLogin = navigateToLogin,
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
