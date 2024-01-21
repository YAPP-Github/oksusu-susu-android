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
import com.susu.feature.received.envelopeadd.ReceivedEnvelopeAddRoute
import com.susu.feature.received.envelopedetail.ReceivedEnvelopeDetailRoute
import com.susu.feature.received.envelopeedit.ReceivedEnvelopeEditRoute
import com.susu.feature.received.ledgeradd.LedgerAddRoute
import com.susu.feature.received.ledgerdetail.LedgerDetailRoute
import com.susu.feature.received.ledgeredit.LedgerEditRoute
import com.susu.feature.received.ledgerfilter.LedgerFilterRoute
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

fun NavController.navigateLedgerFilter() {
    navigate(ReceivedRoute.ledgerFilterRoute)
}

fun NavController.navigateLedgerAdd() {
    navigate(ReceivedRoute.ledgerAddRoute)
}

fun NavController.navigateReceivedEnvelopeAdd() {
    navigate(ReceivedRoute.envelopeAddRoute)
}

fun NavController.navigateReceivedEnvelopeDetail() {
    navigate(ReceivedRoute.envelopeDetailRoute)
}

fun NavController.navigateReceivedEnvelopeEdit() {
    navigate(ReceivedRoute.envelopeEditRoute)
}

fun NavGraphBuilder.receivedNavGraph(
    padding: PaddingValues,
    navigateLedgerDetail: (Ledger) -> Unit,
    popBackStack: () -> Unit,
    popBackStackWithLedger: (String) -> Unit,
    popBackStackWithDeleteLedgerId: (Int) -> Unit,
    navigateLedgerSearch: () -> Unit,
    navigateLedgerEdit: (Ledger) -> Unit,
    navigateLedgerFilter: () -> Unit,
    navigateLedgerAdd: () -> Unit,
    navigateEnvelopAdd: () -> Unit,
    navigateEnvelopeDetail: () -> Unit,
    navigateEnvelopeEdit: () -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
    onShowDialog: (DialogToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    composable(route = ReceivedRoute.route) { navBackStackEntry ->
        val ledger = navBackStackEntry.savedStateHandle.get<String>(ReceivedRoute.LEDGER_ARGUMENT_NAME)
        val toDeleteLedgerId = navBackStackEntry.savedStateHandle.get<Int>(ReceivedRoute.LEDGER_ID_ARGUMENT_NAME) ?: -1
        ReceivedRoute(
            ledger = ledger,
            toDeleteLedgerId = toDeleteLedgerId,
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
            navigateEnvelopAdd = navigateEnvelopAdd,
            navigateEnvelopeDetail = navigateEnvelopeDetail,
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
        route = ReceivedRoute.ledgerFilterRoute,
    ) {
        LedgerFilterRoute(popBackStack = popBackStack)
    }

    composable(
        route = ReceivedRoute.ledgerAddRoute,
    ) {
        LedgerAddRoute(
            popBackStack = popBackStack,
            popBackStackWithLedger = popBackStackWithLedger,
            handleException = handleException,
        )
    }

    composable(
        route = ReceivedRoute.envelopeAddRoute,
    ) {
        ReceivedEnvelopeAddRoute(
            popBackStack = popBackStack,
        )
    }

    composable(
        route = ReceivedRoute.envelopeDetailRoute,
    ) {
        ReceivedEnvelopeDetailRoute(
            popBackStack = popBackStack,
            navigateReceivedEnvelopeEdit = navigateEnvelopeEdit,
        )
    }

    composable(
        route = ReceivedRoute.envelopeEditRoute,
    ) {
        ReceivedEnvelopeEditRoute(popBackStack = popBackStack)
    }
}

object ReceivedRoute {
    const val route = "received"
    const val LEDGER_ARGUMENT_NAME = "ledger"
    const val LEDGER_ID_ARGUMENT_NAME = "ledger-id"
    fun ledgerDetailRoute(ledger: String) = "ledger-detail/$ledger"
    fun ledgerEditRoute(ledger: String) = "ledger-edit/$ledger"
    const val ledgerSearchRoute = "ledger-search"

    const val ledgerAddRoute = "ledger-add" // TODO 파라미터 넘기는 방식으로 수정해야함.
    const val ledgerFilterRoute = "ledger-filter" // TODO 파라미터 넘기는 방식으로 수정해야함.

    const val envelopeAddRoute = "envelope-add"
    const val envelopeDetailRoute = "envelope-detail" // TODO 파라미터 넘기는 방식으로 수정해야함.
    const val envelopeEditRoute = "envelope-edit" // TODO 파라미터 넘기는 방식으로 수정해야함.
}
