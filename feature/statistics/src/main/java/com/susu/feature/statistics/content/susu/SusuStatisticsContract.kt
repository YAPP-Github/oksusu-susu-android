package com.susu.feature.statistics.content.susu

import com.susu.core.model.Category
import com.susu.core.model.Relationship
import com.susu.core.model.SusuStatistics
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

sealed interface SusuStatisticsEffect : SideEffect {
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : SusuStatisticsEffect
}

data class SusuStatisticsState(
    val isLoading: Boolean = false,
    val age: StatisticsAge = StatisticsAge.TWENTY,
    val relationship: Relationship = Relationship(),
    val category: Category = Category(),
    val categoryConfig: PersistentList<Category> = persistentListOf(),
    val relationshipConfig: PersistentList<Relationship> = persistentListOf(),
    val isAgeSheetOpen: Boolean = false,
    val isRelationshipSheetOpen: Boolean = false,
    val isCategorySheetOpen: Boolean = false,
    val susuStatistics: SusuStatistics = SusuStatistics(),
) : UiState

enum class StatisticsAge(val num: Int) {
    TEN(10), TWENTY(20), THIRTY(30), FOURTY(40), FIFTY(50), SIXTY(60), SEVENTY(70), EIGHTY(80), NINETY(90)
}
