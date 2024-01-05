package com.susu.feature.received.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.susu.feature.received.received.ReceivedRoute
import com.susu.feature.received.search.LedgerSearchRoute

fun NavController.navigateReceived(navOptions: NavOptions) {
    navigate(ReceivedRoute.route, navOptions)
}

fun NavController.navigateLedgerSearch() {
    navigate(ReceivedRoute.ledgerSearchRoute)
}

fun NavGraphBuilder.receivedNavGraph(
    padding: PaddingValues,
    popBackStack: () -> Unit,
    navigateLedgerSearch: () -> Unit,
) {
    composable(route = ReceivedRoute.route) {
        ReceivedRoute(
            padding = padding,
            navigateLedgerSearch = navigateLedgerSearch,
        )
    }

    composable(route = ReceivedRoute.ledgerSearchRoute) {
        LedgerSearchRoute(
            popBackStack = popBackStack,
        )
    }
}

object ReceivedRoute {
    const val route = "received"
    const val ledgerSearchRoute = "ledger-search"
}
