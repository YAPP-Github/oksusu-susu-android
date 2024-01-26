package com.susu.feature.received.envelopeadd.content.name

import com.susu.core.model.FriendSearch
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class NameState(
    val name: String = "",
    val isSelectedFriend: Boolean = false,
    val friendList: PersistentList<FriendSearch> = persistentListOf()
) : UiState

sealed interface NameSideEffect : SideEffect {
    data class UpdateParentName(val name: String) : NameSideEffect
    data class UpdateParentFriendId(val friendId: Long?) : NameSideEffect
}
