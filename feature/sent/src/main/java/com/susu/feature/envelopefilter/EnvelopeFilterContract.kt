package com.susu.feature.envelopefilter

import com.susu.core.model.Friend
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class EnvelopeFilterState(
    val searchKeyword: String = "",
    val selectedFriendList: PersistentList<Friend> = persistentListOf(),
    val friendList: PersistentList<Friend> = persistentListOf(),
    val fromAmount: Long? = null,
    val toAmount: Long? = null,
    val maxFromAmount: Long = 0,
    val maxToAmount: Long = 0,
) : UiState {
    val sliderValue = fromAmount?.toFloat()?.rangeTo(toAmount?.toFloat()!!) ?: maxFromAmount.toFloat()..maxToAmount.toFloat()
    val sliderValueRange =  maxFromAmount.toFloat()..maxToAmount.toFloat()
}

sealed interface EnvelopeFilterSideEffect : SideEffect {
    data object PopBackStack : EnvelopeFilterSideEffect
    data class PopBackStackWithFilter(val filter: String) : EnvelopeFilterSideEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : EnvelopeFilterSideEffect
}
