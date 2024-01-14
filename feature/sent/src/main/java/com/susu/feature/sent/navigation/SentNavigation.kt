package com.susu.feature.sent.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.susu.feature.envelope.SentEnvelopeRoute
import com.susu.feature.envelopedetail.SentEnvelopeDetailRoute
import com.susu.feature.envelopeedit.SentEnvelopeEditRoute
import com.susu.feature.sent.SentRoute

fun NavController.navigateSent(navOptions: NavOptions) {
    navigate(SentRoute.route, navOptions)
}

fun NavController.navigateSentEnvelope() {
    navigate(SentRoute.sentEnvelopeRoute)
}

fun NavController.navigateSentEnvelopeDetail() {
    navigate(SentRoute.sentEnvelopeDetailRoute)
}

fun NavController.navigateSentEnvelopeEdit() {
    navigate(SentRoute.sentEnvelopeEditRoute)
}

fun NavGraphBuilder.sentNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    navigateSentEnvelope: () -> Unit,
    navigateSentEnvelopeDetail: () -> Unit,
    navigateSentEnvelopeEdit: () -> Unit,
) {
    composable(route = SentRoute.route) {
        SentRoute(
            padding = padding,
            navigateSentEnvelope = navigateSentEnvelope,
        )
    }

    composable(route = SentRoute.sentEnvelopeRoute) {
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
}

object SentRoute {
    const val route = "sent"
    const val sentEnvelopeRoute = "sent-envelope"
    const val sentEnvelopeDetailRoute = "sent-envelope-detail"
    const val sentEnvelopeEditRoute = "sent-envelope-edit"
}
