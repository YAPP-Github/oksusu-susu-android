package com.susu.feature.navigator

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.susu.feature.community.navigation.CommunityRoute
import com.susu.feature.mypage.navigation.MyPageRoute
import com.susu.feature.received.navigation.ReceivedRoute
import com.susu.feature.sent.navigation.SentRoute
import com.susu.feature.statistics.navigation.StatisticsRoute

enum class MainNavigationTab(
    @DrawableRes val selectedIconId: Int,
    @DrawableRes val unselectedIconId: Int,
    @StringRes val labelId: Int,
    val route: String,
) {
    SENT(
        selectedIconId = R.drawable.ic_sent_filled,
        unselectedIconId = R.drawable.ic_sent_outlined,
        labelId = R.string.bottom_navigation_item_label_sent,
        route = SentRoute.route,
    ),
    RECEIVED(
        selectedIconId = R.drawable.ic_received_filled,
        unselectedIconId = R.drawable.ic_received_outlined,
        labelId = R.string.bottom_navigation_item_label_received,
        route = ReceivedRoute.route,
    ),
    STATISTICS(
        selectedIconId = R.drawable.ic_statistics_filled,
        unselectedIconId = R.drawable.ic_statistics_outlined,
        labelId = R.string.bottom_navigation_item_label_statistics,
        route = StatisticsRoute.route,
    ),
    COMMUNITY(
        selectedIconId = R.drawable.ic_community_filled,
        unselectedIconId = R.drawable.ic_community_outlined,
        labelId = R.string.bottom_navigation_item_label_community,
        route = CommunityRoute.route,
    ),
    MY_PAGE(
        selectedIconId = R.drawable.ic_my_page_filled,
        unselectedIconId = R.drawable.ic_my_page_outlined,
        labelId = R.string.bottom_navigation_item_label_my_page,
        route = MyPageRoute.defaultRoute,
    ),
    ;

    companion object {
        operator fun contains(route: String): Boolean {
            return entries.map { it.route }.contains(route)
        }

        fun find(route: String): MainNavigationTab? {
            return entries.find { it.route == route }
        }
    }
}
