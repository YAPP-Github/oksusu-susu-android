package com.susu.core.designsystem.navigation

import androidx.annotation.DrawableRes
import com.susu.core.designsystem.R

enum class NavigationItem(
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    val tabName: String,
) {
    SENT(
        selectedIcon = R.drawable.ic_sent_filled,
        unselectedIcon = R.drawable.ic_sent_outlined,
        tabName = "보내요",
    ),
    RECEIVED(
        selectedIcon = R.drawable.ic_received_filled,
        unselectedIcon = R.drawable.ic_received_outlined,
        tabName = "받아요",
    ),
    STATISTICS(
        selectedIcon = R.drawable.ic_statistics_filled,
        unselectedIcon = R.drawable.ic_statistics_outlined,
        tabName = "통계",
    ),
    COMMUNITY(
        selectedIcon = R.drawable.ic_community_filled,
        unselectedIcon = R.drawable.ic_community_outlined,
        tabName = "투표",
    ),
    MYPAGE(
        selectedIcon = R.drawable.ic_my_page_filled,
        unselectedIcon = R.drawable.ic_my_page_outlined,
        tabName = "마이페이지",
    )
}
