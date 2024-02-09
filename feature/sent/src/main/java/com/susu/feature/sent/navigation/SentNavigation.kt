package com.susu.feature.sent.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.susu.core.model.EnvelopeDetail
import com.susu.core.ui.DialogToken
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.encodeToUri
import com.susu.feature.envelope.SentEnvelopeRoute
import com.susu.feature.envelopeadd.SentEnvelopeAddRoute
import com.susu.feature.envelopedetail.SentEnvelopeDetailRoute
import com.susu.feature.envelopeedit.SentEnvelopeEditRoute
import com.susu.feature.envelopefilter.EnvelopeFilterRoute
import com.susu.feature.envelopesearch.SentEnvelopeSearchRoute
import com.susu.feature.sent.SentRoute
import kotlinx.serialization.json.Json

fun NavController.navigateSent(navOptions: NavOptions) {
    navigate(SentRoute.route, navOptions)
}

fun NavController.navigateSentEnvelope(id: Long) {
    navigate(SentRoute.sentEnvelopeRoute(id = id.toString()))
}

fun NavController.navigateSentEnvelopeDetail(id: Long) {
    navigate(SentRoute.sentEnvelopeDetailRoute(id = id.toString()))
}

fun NavController.navigateSentEnvelopeEdit(envelopeDetail: EnvelopeDetail) {
    navigate(SentRoute.sentEnvelopeEditRoute(Json.encodeToUri(envelopeDetail)))
}

fun NavController.navigateSentEnvelopeAdd() {
    navigate(SentRoute.sentEnvelopeAddRoute)
}

fun NavController.navigateSentEnvelopeSearch() {
    navigate(SentRoute.sentEnvelopeSearchRoute)
}

fun NavController.navigateEnvelopeFilter(filter: String) {
    navigate(SentRoute.envelopeFilterRoute(filter))
}

fun NavGraphBuilder.sentNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    popBackStackWithDeleteFriendId: (Long) -> Unit,
    popBackStackWithEditedFriendId: (Long) -> Unit,
    popBackStackWithRefresh: () -> Unit,
    navigateSentEnvelope: (Long) -> Unit,
    navigateSentEnvelopeDetail: (Long) -> Unit,
    navigateSentEnvelopeEdit: (EnvelopeDetail) -> Unit,
    navigateSentEnvelopeAdd: () -> Unit,
    navigateSentEnvelopeSearch: () -> Unit,
    navigateEnvelopeFilter: (String) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
    onShowDialog: (DialogToken) -> Unit,
    popBackStackWithFilter: (String) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    composable(route = SentRoute.route) { navBackStackEntry ->
        val deletedFriendId = navBackStackEntry.savedStateHandle.get<Long>(SentRoute.FRIEND_ID_ARGUMENT_NAME)
        val editedFriendId = navBackStackEntry.savedStateHandle.get<Long>(SentRoute.EDITED_FRIEND_ID_ARGUMENT_NAME)
        val refresh = navBackStackEntry.savedStateHandle.get<Boolean>(SentRoute.SENT_REFRESH_ARGUMENT_NAME)
        val filter = navBackStackEntry.savedStateHandle.get<String>(SentRoute.FILTER_ENVELOPE_ARGUMENT)
        navBackStackEntry.savedStateHandle.set<String>(SentRoute.FILTER_ENVELOPE_ARGUMENT, null)
        navBackStackEntry.savedStateHandle.set<Boolean>(SentRoute.SENT_REFRESH_ARGUMENT_NAME, null)
        navBackStackEntry.savedStateHandle.set<Long>(SentRoute.EDITED_FRIEND_ID_ARGUMENT_NAME, null)

        SentRoute(
            padding = padding,
            filter = filter,
            deletedFriendId = deletedFriendId,
            editedFriendId = editedFriendId,
            refresh = refresh,
            navigateSentEnvelope = navigateSentEnvelope,
            navigateSentEnvelopeAdd = navigateSentEnvelopeAdd,
            navigateSentEnvelopeSearch = navigateSentEnvelopeSearch,
            navigateEnvelopeFilter = navigateEnvelopeFilter,
        )
    }

    composable(
        route = SentRoute.sentEnvelopeRoute("{${SentRoute.FRIEND_ID_ARGUMENT_NAME}}"),
        arguments = listOf(
            navArgument(SentRoute.FRIEND_ID_ARGUMENT_NAME) {
                type = NavType.LongType
            },
        ),
    ) { navBackStackEntry ->
        val editedFriendId = navBackStackEntry.savedStateHandle.get<Long>(SentRoute.EDITED_FRIEND_ID_ARGUMENT_NAME)
        SentEnvelopeRoute(
            popBackStack = popBackStack,
            editedFriendId = editedFriendId,
            popBackStackWithEditedFriendId = popBackStackWithEditedFriendId,
            navigateSentEnvelopeDetail = navigateSentEnvelopeDetail,
            popBackStackWithDeleteFriendId = popBackStackWithDeleteFriendId,
        )
    }

    composable(
        route = SentRoute.sentEnvelopeDetailRoute("{${SentRoute.ENVELOPE_ID_ARGUMENT_NAME}}"),
        arguments = listOf(
            navArgument(SentRoute.ENVELOPE_ID_ARGUMENT_NAME) {
                type = NavType.LongType
            },
        ),
    ) { navBackStackEntry ->
        val editedFriendId = navBackStackEntry.savedStateHandle.get<Long>(SentRoute.EDITED_FRIEND_ID_ARGUMENT_NAME)

        SentEnvelopeDetailRoute(
            popBackStack = popBackStack,
            editedFriendId = editedFriendId,
            popBackStackWithEditedFriendId = popBackStackWithEditedFriendId,
            navigateSentEnvelopeEdit = navigateSentEnvelopeEdit,
            onShowSnackbar = onShowSnackbar,
            onShowDialog = onShowDialog,
            handleException = handleException,
        )
    }

    composable(route = SentRoute.sentEnvelopeEditRoute("{${SentRoute.ENVELOPE_DETAIL_ARGUMENT_NAME}}")) {
        SentEnvelopeEditRoute(
            popBackStack = popBackStack,
            popBackStackWithEditedFriendId = popBackStackWithEditedFriendId,
        )
    }

    composable(route = SentRoute.sentEnvelopeAddRoute) {
        SentEnvelopeAddRoute(
            popBackStack = popBackStack,
            popBackStackWithRefresh = popBackStackWithRefresh,
            handleException = handleException,
        )
    }

    composable(route = SentRoute.sentEnvelopeSearchRoute) {
        SentEnvelopeSearchRoute(
            navigateSentEnvelopeDetail = navigateSentEnvelopeDetail,
            popBackStack = popBackStack,
        )
    }

    composable(
        route = SentRoute.envelopeFilterRoute("{${SentRoute.FILTER_ENVELOPE_ARGUMENT}}"),
    ) {
        EnvelopeFilterRoute(
            popBackStack = popBackStack,
            popBackStackWithFilter = popBackStackWithFilter,
            handleException = handleException,
        )
    }
}

object SentRoute {
    const val route = "sent"
    const val sentEnvelopeAddRoute = "sent-envelope-add"
    const val SENT_REFRESH_ARGUMENT_NAME = "sent-refresh"
    fun sentEnvelopeRoute(id: String) = "sent-envelope/$id"
    const val FRIEND_ID_ARGUMENT_NAME = "sent-envelope-id"
    const val EDITED_FRIEND_ID_ARGUMENT_NAME = "edited-friend-id"

    fun sentEnvelopeDetailRoute(id: String) = "sent-envelope-detail/$id"
    const val ENVELOPE_ID_ARGUMENT_NAME = "sent-envelope-detail-id"

    fun sentEnvelopeEditRoute(envelopeDetail: String) = "sent-envelope-edit/$envelopeDetail"
    const val ENVELOPE_DETAIL_ARGUMENT_NAME = "envelope-detail"

    const val sentEnvelopeSearchRoute = "sent-envelope-search"
    const val FILTER_ENVELOPE_ARGUMENT = "filter-envelope"

    fun envelopeFilterRoute(filter: String) = "envelope-filter/$filter"
}
