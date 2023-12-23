package com.susu.feature.sent.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.susu.feature.sent.SentScreen

fun NavController.navigateSent(navOptions: NavOptions) {
    navigate(SentRoute.route, navOptions)
}

fun NavGraphBuilder.sentNavGraph(
    padding: PaddingValues,
) {
    composable(route = SentRoute.route) {
        SentScreen(padding)
    }
}

object SentRoute {
    const val route = "sent"
}
