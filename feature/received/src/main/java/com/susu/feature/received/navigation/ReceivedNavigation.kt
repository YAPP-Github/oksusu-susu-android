package com.susu.feature.received.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.susu.core.model.Ledger
import com.susu.core.ui.DialogToken
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.encodeToUri
import com.susu.feature.received.ledgeradd.LedgerAddRoute
import com.susu.feature.received.ledgerdetail.LedgerDetailRoute
import com.susu.feature.received.ledgeredit.LedgerEditRoute
import com.susu.feature.received.ledgerfilter.LedgerFilterRoute
import com.susu.feature.received.navigation.argument.FilterArgument
import com.susu.feature.received.received.ReceivedRoute
import com.susu.feature.received.search.LedgerSearchRoute
import kotlinx.serialization.json.Json

fun NavController.navigateReceived(navOptions: NavOptions) {
    navigate(ReceivedRoute.route, navOptions)
}

fun NavController.navigateLedgerDetail(ledger: Ledger) {
    navigate(ReceivedRoute.ledgerDetailRoute(Json.encodeToUri(ledger)))
}

fun NavController.navigateLedgerSearch() {
    navigate(ReceivedRoute.ledgerSearchRoute)
}

fun NavController.navigateLedgerEdit(ledger: Ledger) {
    navigate(ReceivedRoute.ledgerEditRoute(Json.encodeToUri(ledger)))
}

fun NavController.navigateLedgerFilter(filter: FilterArgument) {
    navigate(ReceivedRoute.ledgerFilterRoute(Json.encodeToUri(filter)))
}

fun NavController.navigateLedgerAdd() {
    navigate(ReceivedRoute.ledgerAddRoute)
}

fun NavGraphBuilder.receivedNavGraph(
    padding: PaddingValues,
    navigateLedgerDetail: (Ledger) -> Unit,
    popBackStack: () -> Unit,
    popBackStackWithLedger: (String) -> Unit,
    popBackStackWithDeleteLedgerId: (Int) -> Unit,
    popBackStackWithFilter: (String) -> Unit,
    navigateLedgerSearch: () -> Unit,
    navigateLedgerEdit: (Ledger) -> Unit,
    navigateLedgerFilter: (FilterArgument) -> Unit,
    navigateLedgerAdd: () -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
    onShowDialog: (DialogToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    composable(route = ReceivedRoute.route) { navBackStackEntry ->
        val ledger = navBackStackEntry.savedStateHandle.get<String>(ReceivedRoute.LEDGER_ARGUMENT_NAME)
        val toDeleteLedgerId = navBackStackEntry.savedStateHandle.get<Int>(ReceivedRoute.LEDGER_ID_ARGUMENT_NAME) ?: -1
        val filter = navBackStackEntry.savedStateHandle.get<String>(ReceivedRoute.FILTER_ARGUMENT_NAME)
        ReceivedRoute(
            ledger = ledger,
            toDeleteLedgerId = toDeleteLedgerId,
            filter = filter,
            padding = padding,
            navigateLedgerDetail = navigateLedgerDetail,
            navigateLedgerSearch = navigateLedgerSearch,
            navigateLedgerFilter = navigateLedgerFilter,
            navigateLedgerAdd = navigateLedgerAdd,
        )
    }

    composable(
        route = ReceivedRoute.ledgerDetailRoute("{${ReceivedRoute.LEDGER_ARGUMENT_NAME}}"),
        arguments = listOf(
            navArgument(ReceivedRoute.LEDGER_ARGUMENT_NAME) {
                type = NavType.StringType
            },
        ),
    ) { navBackStackEntry ->
        val ledger = navBackStackEntry.savedStateHandle.get<String>(ReceivedRoute.LEDGER_ARGUMENT_NAME)
        LedgerDetailRoute(
            ledger = ledger,
            navigateLedgerEdit = navigateLedgerEdit,
            popBackStackWithLedger = popBackStackWithLedger,
            popBackStackWithDeleteLedgerId = popBackStackWithDeleteLedgerId,
            onShowSnackbar = onShowSnackbar,
            onShowDialog = onShowDialog,
            handleException = handleException,
        )
    }

    composable(route = ReceivedRoute.ledgerSearchRoute) {
        LedgerSearchRoute(
            popBackStack = popBackStack,
            navigateLedgerDetail = navigateLedgerDetail,
        )
    }
    composable(
        route = ReceivedRoute.ledgerEditRoute("{${ReceivedRoute.LEDGER_ARGUMENT_NAME}}"),
    ) {
        LedgerEditRoute(
            popBackStack = popBackStack,
            popBackStackWithLedger = popBackStackWithLedger,
        )
    }

    composable(
        route = ReceivedRoute.ledgerFilterRoute("{${ReceivedRoute.FILTER_ARGUMENT_NAME}}"),
    ) {
        LedgerFilterRoute(
            popBackStack = popBackStack,
            popBackStackWithFilter = popBackStackWithFilter,
        )
    }

    composable(
        route = ReceivedRoute.ledgerAddRoute,
    ) {
        LedgerAddRoute(
            popBackStack = popBackStack,
        )
    }
}

object ReceivedRoute {
    const val route = "received"
    const val LEDGER_ARGUMENT_NAME = "ledger"
    const val LEDGER_ID_ARGUMENT_NAME = "ledger-id"
    const val FILTER_ARGUMENT_NAME = "filter"
    fun ledgerDetailRoute(ledger: String) = "ledger-detail/$ledger"
    fun ledgerEditRoute(ledger: String) = "ledger-edit/$ledger"
    fun ledgerFilterRoute(filter: String) = "ledger-filter/$filter"
    const val ledgerSearchRoute = "ledger-search"

    const val ledgerAddRoute = "ledger-add" // TODO 파라미터 넘기는 방식으로 수정해야함.
}
