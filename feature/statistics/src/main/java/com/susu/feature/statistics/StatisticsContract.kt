package com.susu.feature.statistics

import androidx.annotation.StringRes
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

enum class StatisticsTab(@StringRes val stringId: Int) {
    MY(R.string.statistics_tab_my),
    AVERAGE(R.string.statistics_tab_average)
}

data class StatisticsState(
    val isLoading: Boolean = false,
    val isBlind: Boolean = false,
    val currentTab: StatisticsTab = StatisticsTab.MY,
) : UiState

sealed interface StatisticsEffect : SideEffect {
    data object NavigateToMyInfo : StatisticsEffect
}
