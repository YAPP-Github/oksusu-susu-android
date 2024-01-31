package com.susu.feature.community.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.susu.core.model.Vote
import com.susu.core.ui.DialogToken
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.encodeToUri
import com.susu.feature.community.community.CommunityRoute
import com.susu.feature.community.voteadd.VoteAddRoute
import com.susu.feature.community.votedetail.VoteDetailRoute
import com.susu.feature.community.voteedit.VoteEditRoute
import com.susu.feature.community.votesearch.VoteSearchRoute
import kotlinx.serialization.json.Json

fun NavController.navigateVoteAdd() {
    navigate(CommunityRoute.voteAddRoute)
}

fun NavController.navigateVoteSearch() {
    navigate(CommunityRoute.voteSearchRoute)
}

fun NavController.navigateCommunity(navOptions: NavOptions) {
    navigate(CommunityRoute.route, navOptions)
}

fun NavController.navigateVoteDetail(voteId: Long) {
    navigate(CommunityRoute.voteDetailRoute(voteId.toString()))
}

fun NavController.navigateVoteEdit(vote: Vote) {
    navigate(CommunityRoute.voteEditRoute(Json.encodeToUri(vote)))
}

fun NavGraphBuilder.communityNavGraph(
    padding: PaddingValues,
    navigateVoteAdd: () -> Unit,
    navigateVoteSearch: () -> Unit,
    navigateVoteDetail: (Long) -> Unit,
    navigateVoteEdit: (Vote) -> Unit,
    popBackStack: () -> Unit,
    popBackStackWithVote: (String) -> Unit,
    popBackStackWithToUpdateVote: (String) -> Unit,
    popBackStackWithDeleteVoteId: (Long) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
    onShowDialog: (DialogToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    composable(route = CommunityRoute.route) { navBackStackEntry ->
        val vote = navBackStackEntry.savedStateHandle.get<String>(CommunityRoute.VOTE_ARGUMENT_NAME)
        val toUpdateVote = navBackStackEntry.savedStateHandle.get<String>(CommunityRoute.TO_UPDATE_VOTE_ARGUMENT_NAME)
        val toDeleteVoteId = navBackStackEntry.savedStateHandle.get<Long>(CommunityRoute.VOTE_ID_ARGUMENT_NAME)

        CommunityRoute(
            padding = padding,
            vote = vote,
            toDeleteVoteId = toDeleteVoteId,
            toUpdateVote = toUpdateVote,
            navigateVoteAdd = navigateVoteAdd,
            navigateVoteSearch = navigateVoteSearch,
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
            popBackStackWithToUpdateVote = popBackStackWithToUpdateVote,
            popBackStackWithDeleteVoteId = popBackStackWithDeleteVoteId,
            navigateVoteEdit = navigateVoteEdit,
            onShowDialog = onShowDialog,
            onShowSnackbar = onShowSnackbar,
            handleException = handleException,
        )
    }

    composable(
        route = CommunityRoute.voteSearchRoute,
    ) {
        VoteSearchRoute(
            popBackStack = popBackStack,
            navigateVoteDetail = navigateVoteDetail,
        )
    }

    composable(
        route = CommunityRoute.voteEditRoute("{${CommunityRoute.VOTE_ARGUMENT_NAME}}")
    ) {
        VoteEditRoute(
            popBackStack = popBackStack,
            handleException = handleException,
        )
    }
}

object CommunityRoute {
    const val route = "community"
    const val voteAddRoute = "vote-add"
    const val voteSearchRoute = "vote-search"

    const val VOTE_ARGUMENT_NAME = "vote"
    const val VOTE_ID_ARGUMENT_NAME = "vote-id"
    const val TO_UPDATE_VOTE_ARGUMENT_NAME = "to-update-vote"

    fun voteDetailRoute(voteId: String) = "vote-detail/$voteId"
    fun voteEditRoute(vote: String) = "vote-edit/$vote"
}
