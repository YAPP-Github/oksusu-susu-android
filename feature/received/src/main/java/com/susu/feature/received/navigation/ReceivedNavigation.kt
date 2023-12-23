package com.susu.feature.received.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.susu.feature.received.ReceivedScreen

fun NavController.navigateReceived(navOptions: NavOptions) {
    navigate(ReceivedRoute.route, navOptions)
}

fun NavGraphBuilder.receivedNavGraph(
    padding: PaddingValues,
) {
    composable(route = ReceivedRoute.route) {
        ReceivedScreen(padding)
    }
}

object ReceivedRoute {
    const val route = "received"
}
