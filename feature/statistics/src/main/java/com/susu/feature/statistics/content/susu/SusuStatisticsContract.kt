package com.susu.feature.statistics.content.susu

import com.susu.core.model.StatisticsOption
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

sealed interface SusuStatisticsEffect : SideEffect {
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : SusuStatisticsEffect
}

data class SusuStatisticsState(
    val isLoading: Boolean = false,
    val age: StatisticsAge = StatisticsAge.THIRTY,
    val relationshipId: Int = 0,
    val categoryId: Int = 0,
    val statisticsOption: StatisticsOption = StatisticsOption(),
    val isAgeSheetOpen: Boolean = false,
    val isRelationshipSheetOpen: Boolean = false,
    val isCategorySheetOpen: Boolean = false,
) : UiState

enum class StatisticsAge(val num: Int) {
    TEN(10), TWENTY(20), THIRTY(30), FOURTY(40), FIFTY(50), SIXTY(60), SEVENTY(70), EIGHTY(80), NINETY(90)
}
