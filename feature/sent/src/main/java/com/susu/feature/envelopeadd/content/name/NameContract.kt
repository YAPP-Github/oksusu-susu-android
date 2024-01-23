package com.susu.feature.envelopeadd.content.name

import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState

data class NameState(
    val isSearching: Boolean = false,
    val name: String = "",
    val searchedFriends: List<String> = emptyList(), // TODO: 입력 시 친구 검색
) : UiState

sealed interface NameEffect : SideEffect {
    data class UpdateParentName(val name: String) : NameEffect
    data object SearchFriendByName : NameEffect
}
