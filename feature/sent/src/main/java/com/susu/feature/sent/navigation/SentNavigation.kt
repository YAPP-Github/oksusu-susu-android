package com.susu.feature.sent.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.susu.feature.envelope.SentEnvelopeRoute
import com.susu.feature.envelopeadd.SentEnvelopeAddRoute
import com.susu.feature.envelopedetail.SentEnvelopeDetailRoute
import com.susu.feature.envelopeedit.SentEnvelopeEditRoute
import com.susu.feature.sent.SentRoute

fun NavController.navigateSent(navOptions: NavOptions) {
    navigate(SentRoute.route, navOptions)
}

// TODO: 수정?
fun NavController.navigateSentEnvelope(id: Long) {
    navigate(SentRoute.sentEnvelopeRoute(id = id.toString()))
}

fun NavController.navigateSentEnvelopeDetail() {
    navigate(SentRoute.sentEnvelopeDetailRoute)
}

fun NavController.navigateSentEnvelopeEdit() {
    navigate(SentRoute.sentEnvelopeEditRoute)
}

fun NavController.navigateSentEnvelopeAdd() {
    navigate(SentRoute.sentEnvelopeAddRoute)
}

fun NavGraphBuilder.sentNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    navigateSentEnvelope: (Long) -> Unit,
    navigateSentEnvelopeDetail: () -> Unit,
    navigateSentEnvelopeEdit: () -> Unit,
    navigateSentEnvelopeAdd: () -> Unit,
) {
    composable(route = SentRoute.route) {
        SentRoute(
            padding = padding,
            navigateSentEnvelope = navigateSentEnvelope,
            navigateSentEnvelopeAdd = navigateSentEnvelopeAdd,
        )
    }

    // TODO: 수정 필요
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

    composable(route = SentRoute.sentEnvelopeDetailRoute) {
        SentEnvelopeDetailRoute(
            popBackStack = popBackStack,
            navigateSentEnvelopeEdit = navigateSentEnvelopeEdit,
        )
    }

    composable(route = SentRoute.sentEnvelopeEditRoute) {
        SentEnvelopeEditRoute(
            popBackStack = popBackStack,
            navigateSentEnvelopeDetail = navigateSentEnvelopeDetail,
        )
    }

    composable(route = SentRoute.sentEnvelopeAddRoute) {
        SentEnvelopeAddRoute(
            popBackStack = popBackStack,
        )
    }
}

object SentRoute {
    const val route = "sent"
    const val sentEnvelopeDetailRoute = "sent-envelope-detail"
    const val sentEnvelopeEditRoute = "sent-envelope-edit"
    const val sentEnvelopeAddRoute = "sent-envelope-add"

    // TODO: 수정 필요
    fun sentEnvelopeRoute(id: String) = "sent-envelope/$id"
    const val FRIEND_ID_ARGUMENT_NAME = "sent-envelope-id"
}
