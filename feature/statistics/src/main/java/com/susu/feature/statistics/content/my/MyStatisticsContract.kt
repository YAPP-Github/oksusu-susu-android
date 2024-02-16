package com.susu.feature.statistics.content.my

import com.susu.core.model.MyStatistics
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

sealed interface MyStatisticsEffect : SideEffect {
    data object ShowDataNotExistDialog: MyStatisticsEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : MyStatisticsEffect
}

data class MyStatisticsState(
    val isLoading: Boolean = false,
    val isBlind: Boolean = true,
    val statistics: MyStatistics = MyStatistics(),
) : UiState
