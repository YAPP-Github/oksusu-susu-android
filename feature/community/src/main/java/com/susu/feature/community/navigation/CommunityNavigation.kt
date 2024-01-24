package com.susu.feature.community.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.susu.core.ui.DialogToken
import com.susu.core.ui.SnackbarToken
import com.susu.feature.community.CommunityScreen
import com.susu.feature.community.voteadd.VoteAddRoute

fun NavController.navigateVoteAdd() {
    navigate(CommunityRoute.voteAddRoute)
}

fun NavController.navigateCommunity(navOptions: NavOptions) {
    navigate(CommunityRoute.route, navOptions)
}

fun NavGraphBuilder.communityNavGraph(
    padding: PaddingValues,
    navigateVoteAdd: () -> Unit,
    popBackStack: () -> Unit,
    @Suppress("detekt:UnusedParameter")
    onShowSnackbar: (SnackbarToken) -> Unit,
    @Suppress("detekt:UnusedParameter")
    onShowDialog: (DialogToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    composable(route = CommunityRoute.route) {
        CommunityScreen(
            padding = padding,
            navigateVoteAdd = navigateVoteAdd,
        )
    }

    composable(route = CommunityRoute.voteAddRoute) {
        VoteAddRoute(
            popBackStack = popBackStack,
            handleException = handleException,
        )
    }
}

object CommunityRoute {
    const val route = "community"
    const val voteAddRoute = "vote-add"
}
