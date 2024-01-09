package com.susu.feature.sent.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.susu.feature.envelope.SentEnvelopeRoute
import com.susu.feature.sent.SentRoute

fun NavController.navigateSent(navOptions: NavOptions) {
    navigate(SentRoute.route, navOptions)
}

fun NavController.navigateSentEnvelope() {
    navigate(SentRoute.sentEnvelopeRoute)
}

fun NavGraphBuilder.sentNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    navigateSentEnvelope: () -> Unit,
) {
    composable(route = SentRoute.route) {
        SentRoute(
            padding = padding,
            navigateSentEnvelope = navigateSentEnvelope,
        )
    }

    composable(route = SentRoute.sentEnvelopeRoute) {
        SentEnvelopeRoute(
            popBackStack = popBackStack
        )
    }
}

object SentRoute {
    const val route = "sent"
    const val sentEnvelopeRoute = "sent-envelope"
}
