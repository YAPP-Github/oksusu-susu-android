package com.susu.feature.received.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.susu.feature.received.ledgerdetail.LedgerDetailRoute
import com.susu.feature.received.ledgeredit.LedgerEditRoute
import com.susu.feature.received.received.ReceivedRoute
import com.susu.feature.received.search.LedgerSearchRoute

fun NavController.navigateReceived(navOptions: NavOptions) {
    navigate(ReceivedRoute.route, navOptions)
}

fun NavController.navigateLedgerDetail(id: Int) {
    navigate(ReceivedRoute.ledgerDetailRoute(id.toString()))
}

fun NavController.navigateLedgerSearch() {
    navigate(ReceivedRoute.ledgerSearchRoute)
}

fun NavController.navigateLedgerEdit() {
    navigate(ReceivedRoute.ledgerEditRoute)
}

fun NavGraphBuilder.receivedNavGraph(
    padding: PaddingValues,
    navigateLedgerDetail: (Int) -> Unit,
    popBackStack: () -> Unit,
    navigateLedgerSearch: () -> Unit,
    navigateLedgerEdit: () -> Unit,
) {
    composable(route = ReceivedRoute.route) {
        ReceivedRoute(
            padding = padding,
            navigateLedgerDetail = navigateLedgerDetail,
            navigateLedgerSearch = navigateLedgerSearch,
        )
    }

    composable(
        route = ReceivedRoute.ledgerDetailRoute("{${ReceivedRoute.LEDGER_DETAIL_ARGUMENT_NAME}}"),
        arguments = listOf(
            navArgument(ReceivedRoute.LEDGER_DETAIL_ARGUMENT_NAME) {
                type = NavType.StringType
                defaultValue = "0"
            },
        ),
    ) {
        LedgerDetailRoute(
            navigateLedgerEdit = navigateLedgerEdit,
        )
    }

    composable(route = ReceivedRoute.ledgerSearchRoute) {
        LedgerSearchRoute(
            popBackStack = popBackStack,
        )
    }
    composable(
        route = ReceivedRoute.ledgerEditRoute,
    ) {
        LedgerEditRoute(popBackStack = popBackStack)
    }
}

object ReceivedRoute {
    const val route = "received"
    const val LEDGER_DETAIL_ARGUMENT_NAME = "ledgerDetailId"
    fun ledgerDetailRoute(id: String = "0") = "ledger-detail/$id"
    const val ledgerSearchRoute = "ledger-search"

    const val ledgerEditRoute = "ledger-edit" // TODO 파라미터 넘기는 방식으로 수정해야함.
}
