package com.susu.feature.statistics.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.susu.feature.statistics.StatisticsScreen

fun NavController.navigateStatistics(navOptions: NavOptions) {
    navigate(StatisticsRoute.route, navOptions)
}

fun NavGraphBuilder.statisticsNavGraph(
    padding: PaddingValues,
) {
    composable(route = StatisticsRoute.route) {
        StatisticsScreen(padding)
    }
}

object StatisticsRoute {
    const val route = "statistics"
}
