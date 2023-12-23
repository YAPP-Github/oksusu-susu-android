package com.susu.feature.community.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.susu.feature.community.CommunityScreen

fun NavController.navigateCommunity(navOptions: NavOptions) {
    navigate(CommunityRoute.route, navOptions)
}

fun NavGraphBuilder.communityNavGraph(
    padding: PaddingValues,
) {
    composable(route = CommunityRoute.route) {
        CommunityScreen(padding)
    }
}

object CommunityRoute {
    const val route = "community"
}
