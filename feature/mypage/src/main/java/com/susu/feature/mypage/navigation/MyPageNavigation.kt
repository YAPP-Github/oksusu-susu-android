package com.susu.feature.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.susu.core.ui.DialogToken
import com.susu.core.ui.SnackbarToken
import com.susu.feature.mypage.MyPagePrivacyPolicyScreen
import com.susu.feature.mypage.info.MyPageInfoRoute
import com.susu.feature.mypage.main.MyPageDefaultRoute
import com.susu.feature.mypage.social.MyPageSocialRoute

fun NavController.navigateMyPage(navOptions: NavOptions) {
    navigate(MyPageRoute.defaultRoute, navOptions)
}

fun NavController.navigateMyPageInfo() {
    navigate(MyPageRoute.infoRoute)
}

fun NavController.navigateMyPageSocial() {
    navigate(MyPageRoute.socialRoute)
}

fun NavController.navigateMyPagePrivacyPolicy() {
    navigate(MyPageRoute.privacyPolicyRoute)
}

fun NavGraphBuilder.myPageNavGraph(
    padding: PaddingValues,
    restartMainActivity: () -> Unit,
    navigateToInfo: () -> Unit,
    navigateToSocial: () -> Unit,
    navigateToPrivacyPolicy: () -> Unit,
    popBackStack: () -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
    onShowDialog: (DialogToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    composable(route = MyPageRoute.defaultRoute) {
        MyPageDefaultRoute(
            padding = padding,
            restartMainActivity = restartMainActivity,
            navigateToInfo = navigateToInfo,
            navigateToSocial = navigateToSocial,
            navigateToPrivacyPolicy = navigateToPrivacyPolicy,
            onShowSnackbar = onShowSnackbar,
            onShowDialog = onShowDialog,
            handleException = handleException,
        )
    }
    composable(route = MyPageRoute.infoRoute) {
        MyPageInfoRoute(
            padding = padding,
            popBackStack = popBackStack,
            onShowSnackbar = onShowSnackbar,
            handleException = handleException,
        )
    }
    composable(route = MyPageRoute.socialRoute) {
        MyPageSocialRoute(padding = padding, popBackStack = popBackStack)
    }
    composable(route = MyPageRoute.privacyPolicyRoute) {
        MyPagePrivacyPolicyScreen()
    }
}

object MyPageRoute {
    const val defaultRoute = "my-page"
    const val infoRoute = "info"
    const val socialRoute = "social"
    const val privacyPolicyRoute = "privacy-policy"
}
