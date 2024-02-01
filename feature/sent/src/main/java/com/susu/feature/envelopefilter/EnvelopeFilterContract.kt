package com.susu.feature.envelopefilter

import com.susu.core.model.Category
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class EnvelopeFilterState(
    val categoryConfig: PersistentList<Category> = persistentListOf(),
) : UiState

sealed interface EnvelopeFilterSideEffect : SideEffect {
    data object PopBackStack : EnvelopeFilterSideEffect
    data class PopBackStackWithFilter(val filter: String) : EnvelopeFilterSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : EnvelopeFilterSideEffect
}
