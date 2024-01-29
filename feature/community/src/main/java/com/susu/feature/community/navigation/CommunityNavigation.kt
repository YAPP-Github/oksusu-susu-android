package com.susu.feature.community.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.susu.core.ui.DialogToken
import com.susu.core.ui.SnackbarToken
import com.susu.feature.community.community.CommunityRoute
import com.susu.feature.community.voteadd.VoteAddRoute
import com.susu.feature.community.votedetail.VoteDetailRoute

fun NavController.navigateVoteAdd() {
    navigate(CommunityRoute.voteAddRoute)
}

fun NavController.navigateCommunity(navOptions: NavOptions) {
    navigate(CommunityRoute.route, navOptions)
}

fun NavController.navigateVoteDetail(voteId: Long) {
    navigate(CommunityRoute.voteDetailRoute(voteId.toString()))
}

fun NavGraphBuilder.communityNavGraph(
    padding: PaddingValues,
    navigateVoteAdd: () -> Unit,
    navigateVoteDetail: (Long) -> Unit,
    popBackStack: () -> Unit,
    popBackStackWithVote: (String) -> Unit,
    @Suppress("detekt:UnusedParameter")
    onShowSnackbar: (SnackbarToken) -> Unit,
    @Suppress("detekt:UnusedParameter")
    onShowDialog: (DialogToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    composable(route = CommunityRoute.route) { navBackStackEntry ->
        val vote = navBackStackEntry.savedStateHandle.get<String>(CommunityRoute.VOTE_ARGUMENT_NAME)

        CommunityRoute(
            padding = padding,
            vote = vote,
            navigateVoteAdd = navigateVoteAdd,
            navigateVoteDetail = navigateVoteDetail,
            handleException = handleException,
        )
    }

    composable(route = CommunityRoute.voteAddRoute) {
        VoteAddRoute(
            popBackStack = popBackStack,
            handleException = handleException,
            popBackStackWithVote = popBackStackWithVote,
        )
    }

    composable(
        route = CommunityRoute.voteDetailRoute(
            voteId = "{${CommunityRoute.VOTE_ID_ARGUMENT_NAME}}",
        ),
        arguments = listOf(
            navArgument(CommunityRoute.VOTE_ID_ARGUMENT_NAME) {
                type = NavType.StringType
            },
        ),
    ) {
        VoteDetailRoute(
            popBackStack = popBackStack,
            handleException = handleException,
        )
    }
}

object CommunityRoute {
    const val route = "community"
    const val voteAddRoute = "vote-add"

    const val VOTE_ARGUMENT_NAME = "vote"
    const val VOTE_ID_ARGUMENT_NAME = "vote-id"

    fun voteDetailRoute(voteId: String) = "vote-detail/$voteId"
}
