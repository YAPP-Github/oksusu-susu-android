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

fun NavGraphBuilder.sentNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    navigateSentEnvelope: (Long) -> Unit,
    navigateSentEnvelopeDetail: (Long) -> Unit,
    navigateSentEnvelopeEdit: (EnvelopeDetail) -> Unit,
    navigateSentEnvelopeAdd: () -> Unit,
    navigateSentEnvelopeSearch: () -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
    onShowDialog: (DialogToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    composable(route = SentRoute.route) {
        SentRoute(
            padding = padding,
            navigateSentEnvelope = navigateSentEnvelope,
            navigateSentEnvelopeAdd = navigateSentEnvelopeAdd,
            navigateSentEnvelopeSearch = navigateSentEnvelopeSearch,
        )
    }

    composable(
        route = SentRoute.sentEnvelopeRoute("{${SentRoute.FRIEND_ID_ARGUMENT_NAME}}"),
        arguments = listOf(
            navArgument(SentRoute.FRIEND_ID_ARGUMENT_NAME) {
                type = NavType.LongType
            },
        ),
    ) {
        SentEnvelopeRoute(
            popBackStack = popBackStack,
            navigateSentEnvelopeDetail = navigateSentEnvelopeDetail,
        )
    }

    composable(
        route = SentRoute.sentEnvelopeDetailRoute("{${SentRoute.ENVELOPE_ID_ARGUMENT_NAME}}"),
        arguments = listOf(
            navArgument(SentRoute.ENVELOPE_ID_ARGUMENT_NAME) {
                type = NavType.LongType
            },
        ),
    ) {
        SentEnvelopeDetailRoute(
            popBackStack = popBackStack,
            navigateSentEnvelopeEdit = navigateSentEnvelopeEdit,
            onShowSnackbar = onShowSnackbar,
            onShowDialog = onShowDialog,
            handleException = handleException,
        )
    }

    composable(route = SentRoute.sentEnvelopeEditRoute("{${SentRoute.ENVELOPE_DETAIL_ARGUMENT_NAME}}")) {
        SentEnvelopeEditRoute(
            popBackStack = popBackStack,
        )
    }

    composable(route = SentRoute.sentEnvelopeAddRoute) {
        SentEnvelopeAddRoute(
            popBackStack = popBackStack,
            handleException = handleException,
        )
    }

    composable(route = SentRoute.sentEnvelopeSearchRoute) {
        SentEnvelopeSearchRoute(
            navigateSentEnvelopeDetail = navigateSentEnvelopeDetail,
            popBackStack = popBackStack,
        )
    }
}

object SentRoute {
    const val route = "sent"
    const val sentEnvelopeAddRoute = "sent-envelope-add"

    fun sentEnvelopeRoute(id: String) = "sent-envelope/$id"
    const val FRIEND_ID_ARGUMENT_NAME = "sent-envelope-id"

    fun sentEnvelopeDetailRoute(id: String) = "sent-envelope-detail/$id"
    const val ENVELOPE_ID_ARGUMENT_NAME = "sent-envelope-detail-id"

    fun sentEnvelopeEditRoute(envelopeDetail: String) = "sent-envelope-edit/$envelopeDetail"
    const val ENVELOPE_DETAIL_ARGUMENT_NAME = "envelope-detail"
    const val sentEnvelopeSearchRoute = "sent-envelope-search"
}
