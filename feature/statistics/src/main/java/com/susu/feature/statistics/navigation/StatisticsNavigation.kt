package com.susu.feature.statistics.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.susu.core.ui.DialogToken
import com.susu.feature.statistics.StatisticsRoute

fun NavController.navigateStatistics(navOptions: NavOptions) {
    navigate(StatisticsRoute.route, navOptions)
}

fun NavGraphBuilder.statisticsNavGraph(
    padding: PaddingValues,
    navigateToMyInfo: () -> Unit,
    onShowDialog: (DialogToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    composable(route = StatisticsRoute.route) {
        StatisticsRoute(
            padding = padding,
            navigateToMyInfo = navigateToMyInfo,
            onShowDialog = onShowDialog,
            handleException = handleException,
        )
    }
}

object StatisticsRoute {
    const val route = "statistics"
}
