package com.susu.feature.envelopefilter

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class EnvelopeFilterState(
    val temp: String = "",
) : UiState

sealed interface EnvelopeFilterSideEffect : SideEffect {
    data object PopBackStack : EnvelopeFilterSideEffect
    data class PopBackStackWithFilter(val filter: String) : EnvelopeFilterSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : EnvelopeFilterSideEffect
}
