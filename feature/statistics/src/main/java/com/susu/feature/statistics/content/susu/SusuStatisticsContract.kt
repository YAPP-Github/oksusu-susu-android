package com.susu.feature.statistics.content.susu

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

sealed interface SusuStatisticsEffect : SideEffect

data class SusuStatisticsState(
    val isLoading: Boolean = false,
    val age: StatisticsAge = StatisticsAge.THIRTY,
    val relationshipId: Int = 0,
    val categoryId: Int = 0,
    val isRelationshipSheetOpen: Boolean = false,
    val isCategorySheetOpen: Boolean = false,
) : UiState

enum class StatisticsAge {
    TEN, TWENTY, THIRTY, FOURTY, FIFTY, SIXTY, SEVENTY, EIGHTY, NINETY
}
