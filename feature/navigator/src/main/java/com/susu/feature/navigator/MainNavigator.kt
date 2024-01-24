package com.susu.feature.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Ledger
import com.susu.feature.community.navigation.navigateCommunity
import com.susu.feature.loginsignup.navigation.LoginSignupRoute
import com.susu.feature.mypage.navigation.navigateMyPage
import com.susu.feature.mypage.navigation.navigateMyPageInfo
import com.susu.feature.mypage.navigation.navigateMyPageSocial
import com.susu.feature.received.navigation.ReceivedRoute
import com.susu.feature.received.navigation.argument.FilterArgument
import com.susu.feature.received.navigation.navigateLedgerAdd
import com.susu.feature.received.navigation.navigateLedgerDetail
import com.susu.feature.received.navigation.navigateLedgerEdit
import com.susu.feature.received.navigation.navigateLedgerFilter
import com.susu.feature.received.navigation.navigateLedgerSearch
import com.susu.feature.received.navigation.navigateReceived
import com.susu.feature.received.navigation.navigateReceivedEnvelopeAdd
import com.susu.feature.received.navigation.navigateReceivedEnvelopeDetail
import com.susu.feature.received.navigation.navigateReceivedEnvelopeEdit
import com.susu.feature.sent.navigation.SentRoute
import com.susu.feature.sent.navigation.navigateSent
import com.susu.feature.sent.navigation.navigateSentEnvelope
import com.susu.feature.sent.navigation.navigateSentEnvelopeAdd
import com.susu.feature.sent.navigation.navigateSentEnvelopeDetail
import com.susu.feature.sent.navigation.navigateSentEnvelopeEdit
import com.susu.feature.statistics.navigation.navigateStatistics

internal class MainNavigator(
    val navController: NavHostController,
) {
    val startDestination = LoginSignupRoute.Parent.Vote.route
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTab: MainNavigationTab?
        @Composable get() = currentDestination
            ?.route
            ?.let(MainNavigationTab::find)

    val statusBarColor: Color
        @Composable
        get() = when (currentDestination?.route) {
            in listOf(
                ReceivedRoute.ledgerSearchRoute,
                ReceivedRoute.ledgerFilterRoute("{${ReceivedRoute.FILTER_ARGUMENT_NAME}}"),
                ReceivedRoute.envelopeDetailRoute,
                ReceivedRoute.envelopeEditRoute,
                SentRoute.sentEnvelopeRoute,
                SentRoute.sentEnvelopeDetailRoute,
                SentRoute.sentEnvelopeEditRoute,
            ),
            -> SusuTheme.colorScheme.background10

            else -> SusuTheme.colorScheme.background15
        }

    fun navigate(tab: MainNavigationTab) {
        val navOptions = navOptions {
            popUpTo(SentRoute.route) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainNavigationTab.SENT -> navController.navigateSent(navOptions)
            MainNavigationTab.RECEIVED -> navController.navigateReceived(navOptions)
            MainNavigationTab.STATISTICS -> navController.navigateStatistics(navOptions)
            MainNavigationTab.COMMUNITY -> navController.navigateCommunity(navOptions)
            MainNavigationTab.MY_PAGE -> navController.navigateMyPage(navOptions)
        }
    }

    fun navigateSent() {
        navController.navigate(SentRoute.route) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    fun navigateSentEnvelope() {
        navController.navigateSentEnvelope()
    }

    fun navigateSentEnvelopeDetail() {
        navController.navigateSentEnvelopeDetail()
    }

    fun navigateSentEnvelopeEdit() {
        navController.navigateSentEnvelopeEdit()
    }

    fun navigateSentEnvelopeAdd() {
        navController.navigateSentEnvelopeAdd()
    }

    fun navigateLogin() {
        navController.navigate(LoginSignupRoute.Parent.Login.route) {
            popUpTo(id = navController.graph.id) {
                inclusive = true
            }
        }
    }

    fun navigateSignup() {
        navController.navigate(LoginSignupRoute.Parent.SignUp.route) {
            popUpTo(id = navController.graph.id) {
                inclusive = true
            }
        }
    }

    fun navigateLedgerDetail(ledger: Ledger) {
        navController.navigateLedgerDetail(ledger)
    }

    fun navigateLedgerSearch() {
        navController.navigateLedgerSearch()
    }

    fun navigateLedgerEdit(ledger: Ledger) {
        navController.navigateLedgerEdit(ledger)
    }

    fun navigateLedgerFilter(filter: FilterArgument) {
        navController.navigateLedgerFilter(filter)
    }

    fun navigateLedgerAdd() {
        navController.navigateLedgerAdd()
    }

    fun navigateMyPageInfo() {
        navController.navigateMyPageInfo()
    }

    fun navigateMyPageSocial() {
        navController.navigateMyPageSocial()
    }

    fun navigateReceivedEnvelopeAdd(categoryName: String, ledgerId: Long) {
        navController.navigateReceivedEnvelopeAdd(categoryName, ledgerId)
    }

    fun navigateReceivedEnvelopeDetail() {
        navController.navigateReceivedEnvelopeDetail()
    }

    fun navigateReceivedEnvelopeEdit() {
        navController.navigateReceivedEnvelopeEdit()
    }

    fun popBackStackIfNotHome() {
        if (!isSameCurrentDestination(SentRoute.route)) {
            navController.popBackStack()
        }
    }

    private fun isSameCurrentDestination(route: String) =
        navController.currentDestination?.route == route

    @Composable
    fun shouldShowBottomBar(): Boolean {
        val currentRoute = currentDestination?.route ?: return false
        return currentRoute in MainNavigationTab
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
