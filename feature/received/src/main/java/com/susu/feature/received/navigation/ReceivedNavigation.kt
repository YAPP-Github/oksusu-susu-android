package com.susu.feature.received.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.susu.core.model.Envelope
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
import com.susu.feature.received.ledgersearch.LedgerSearchRoute
import com.susu.feature.received.navigation.argument.LedgerFilterArgument
import com.susu.feature.received.received.ReceivedRoute
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

fun NavController.navigateLedgerFilter(filter: LedgerFilterArgument) {
    navigate(ReceivedRoute.ledgerFilterRoute(Json.encodeToUri(filter)))
}

fun NavController.navigateLedgerAdd() {
    navigate(ReceivedRoute.ledgerAddRoute)
}

fun NavController.navigateReceivedEnvelopeAdd(ledger: Ledger) {
    navigate(ReceivedRoute.envelopeAddRoute(Json.encodeToUri(ledger)))
}

fun NavController.navigateReceivedEnvelopeDetail(envelope: Envelope, ledger: Ledger) {
    navigate(ReceivedRoute.envelopeDetailRoute(Json.encodeToUri(envelope), Json.encodeToUri(ledger)))
}

fun NavController.navigateReceivedEnvelopeEdit(envelope: Envelope, ledger: Ledger) {
    navigate(ReceivedRoute.envelopeEditRoute(Json.encodeToUri(envelope), Json.encodeToUri(ledger)))
}

@Suppress("detekt:LongMethod")
fun NavGraphBuilder.receivedNavGraph(
    padding: PaddingValues,
    envelopeFilterArgumentName: String,
    navigateLedgerDetail: (Ledger) -> Unit,
    popBackStack: () -> Unit,
    popBackStackWithLedger: (String) -> Unit,
    popBackStackWithDeleteLedgerId: (Long) -> Unit,
    popBackStackWithFilter: (String) -> Unit,
    navigateLedgerSearch: () -> Unit,
    navigateLedgerEdit: (Ledger) -> Unit,
    navigateLedgerFilter: (LedgerFilterArgument) -> Unit,
    navigateLedgerAdd: () -> Unit,
    navigateEnvelopeFilter: (String) -> Unit,
    navigateEnvelopAdd: (Ledger) -> Unit,
    navigateEnvelopeDetail: (Envelope, Ledger) -> Unit,
    navigateEnvelopeEdit: (Envelope, Ledger) -> Unit,
    popBackStackWithEnvelope: (String) -> Unit,
    popBackStackWithDeleteReceivedEnvelopeId: (Long) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
    onShowDialog: (DialogToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    composable(route = ReceivedRoute.route) { navBackStackEntry ->
        val ledger = navBackStackEntry.savedStateHandle.get<String>(ReceivedRoute.LEDGER_ARGUMENT_NAME)
        val toDeleteLedgerId = navBackStackEntry.savedStateHandle.get<Long>(ReceivedRoute.LEDGER_ID_ARGUMENT_NAME) ?: -1
        val filter = navBackStackEntry.savedStateHandle.get<String>(ReceivedRoute.FILTER_LEDGER_ARGUMENT_NAME)
        navBackStackEntry.savedStateHandle.set<String>(ReceivedRoute.FILTER_LEDGER_ARGUMENT_NAME, null)
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
        val envelope = navBackStackEntry.savedStateHandle.get<String>(ReceivedRoute.ENVELOPE_ARGUMENT_NAME)
        val toDeleteEnvelopeId = navBackStackEntry.savedStateHandle.get<Long>(ReceivedRoute.ENVELOPE_ID_ARGUMENT_NAME)
        val envelopeFilter = navBackStackEntry.savedStateHandle.get<String>(envelopeFilterArgumentName)
        navBackStackEntry.savedStateHandle.set<String>(envelopeFilterArgumentName, null)
        LedgerDetailRoute(
            envelope = envelope,
            envelopeFilter = envelopeFilter,
            navigateEnvelopeFilter = navigateEnvelopeFilter,
            toDeleteEnvelopeId = toDeleteEnvelopeId,
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
        )
    }

    composable(
        route = ReceivedRoute.ledgerFilterRoute("{${ReceivedRoute.FILTER_LEDGER_ARGUMENT_NAME}}"),
    ) {
        LedgerFilterRoute(
            popBackStack = popBackStack,
            popBackStackWithFilter = popBackStackWithFilter,
            handleException = handleException,
        )
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
        route = ReceivedRoute.envelopeAddRoute(
            ledger = "{${ReceivedRoute.LEDGER_ARGUMENT_NAME}}",
        ),
    ) {
        ReceivedEnvelopeAddRoute(
            popBackStack = popBackStack,
            onShowSnackbar = onShowSnackbar,
            popBackStackWithEnvelope = popBackStackWithEnvelope,
            handleException = handleException,
        )
    }

    composable(
        route = ReceivedRoute.envelopeDetailRoute(
            envelope = "{${ReceivedRoute.ENVELOPE_ARGUMENT_NAME}}",
            ledger = "{${ReceivedRoute.LEDGER_ARGUMENT_NAME}}",
        ),
    ) {
        ReceivedEnvelopeDetailRoute(
            popBackStackWithDeleteReceivedEnvelopeId = popBackStackWithDeleteReceivedEnvelopeId,
            popBackStackWithReceivedEnvelope = popBackStackWithEnvelope,
            navigateReceivedEnvelopeEdit = navigateEnvelopeEdit,
            onShowSnackbar = onShowSnackbar,
            onShowDialog = onShowDialog,
            handleException = handleException,
        )
    }

    composable(
        route = ReceivedRoute.envelopeEditRoute(
            envelope = "{${ReceivedRoute.ENVELOPE_ARGUMENT_NAME}}",
            ledger = "{${ReceivedRoute.LEDGER_ARGUMENT_NAME}}",
        ),
    ) {
        ReceivedEnvelopeEditRoute(
            popBackStack = popBackStack,
            handleException = handleException,
        )
    }
}

object ReceivedRoute {
    const val route = "received"
    const val LEDGER_ARGUMENT_NAME = "ledger"
    const val LEDGER_ID_ARGUMENT_NAME = "ledger-id"
    const val CATEGORY_ARGUMENT_NAME = "category-name"
    const val ENVELOPE_ARGUMENT_NAME = "envelope"
    const val ENVELOPE_ID_ARGUMENT_NAME = "envelope-id"

    const val FILTER_LEDGER_ARGUMENT_NAME = "filter-ledger"
    fun ledgerDetailRoute(ledger: String) = "ledger-detail/$ledger"
    fun ledgerEditRoute(ledger: String) = "ledger-edit/$ledger"
    fun ledgerFilterRoute(filter: String) = "ledger-filter/$filter"
    const val ledgerSearchRoute = "ledger-search"

    const val ledgerAddRoute = "ledger-add"

    fun envelopeAddRoute(ledger: String) = "envelope-add/$ledger"
    fun envelopeDetailRoute(envelope: String, ledger: String) = "envelope-detail/$envelope/$ledger"
    fun envelopeEditRoute(envelope: String, ledger: String) = "envelope-edit/$envelope/$ledger"
}
