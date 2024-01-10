package com.susu.feature.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.community.navigation.navigateCommunity
import com.susu.feature.loginsignup.navigation.LoginSignupRoute
import com.susu.feature.mypage.navigation.navigateMyPage
import com.susu.feature.received.navigation.ReceivedRoute
import com.susu.feature.received.navigation.navigateLedgerDetail
import com.susu.feature.received.navigation.navigateLedgerEdit
import com.susu.feature.received.navigation.navigateLedgerSearch
import com.susu.feature.received.navigation.navigateReceived
import com.susu.feature.sent.navigation.SentRoute
import com.susu.feature.sent.navigation.navigateSent
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
            in listOf(ReceivedRoute.ledgerSearchRoute) -> SusuTheme.colorScheme.background10
            else -> SusuTheme.colorScheme.background15
        }

    fun navigate(tab: MainNavigationTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
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

    fun navigateLogin() {
        navController.navigate(LoginSignupRoute.Parent.Login.route)
    }

    fun navigateSignup() {
        navController.navigate(LoginSignupRoute.Parent.SignUp.route) {
            popUpTo(id = navController.graph.id) {
                inclusive = true
            }
        }
    }

    fun navigateLedgerDetail(id: Int) {
        navController.navigateLedgerDetail(id)
    }

    fun navigateLedgerSearch() {
        navController.navigateLedgerSearch()
    }

    fun navigateLedgerEdit() {
        navController.navigateLedgerEdit()
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
