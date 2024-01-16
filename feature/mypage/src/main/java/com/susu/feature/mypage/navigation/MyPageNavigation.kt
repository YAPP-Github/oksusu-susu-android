package com.susu.feature.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.susu.feature.mypage.main.MyPageDefaultRoute

fun NavController.navigateMyPage(navOptions: NavOptions) {
    navigate(MyPageRoute.default, navOptions)
}

fun NavGraphBuilder.myPageNavGraph(
    padding: PaddingValues,
    navigateToLogin: () -> Unit,
) {
    composable(route = MyPageRoute.default) {
        MyPageDefaultRoute(padding, navigateToLogin = navigateToLogin)
    }
}

object MyPageRoute {
    const val default = "my-page"
    const val myInfo = "my-info"
    const val social = "social"
}
