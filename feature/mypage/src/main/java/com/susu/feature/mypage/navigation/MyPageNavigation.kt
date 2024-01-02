package com.susu.feature.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.susu.feature.mypage.MyPageScreen

fun NavController.navigateMyPage(navOptions: NavOptions) {
    navigate(MyPageRoute.route, navOptions)
}

fun NavGraphBuilder.myPageNavGraph(
    padding: PaddingValues,
    navigateToLogin: () -> Unit
) {
    composable(route = MyPageRoute.route) {
        MyPageScreen(padding, navigateToLogin = navigateToLogin)
    }
}

object MyPageRoute {
    const val route = "my-page"
}
