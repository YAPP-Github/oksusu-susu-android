package com.susu.feature.envelopeadd.content.name

import com.susu.core.model.FriendSearch
import com.susu.core.ui.base.SideEffect
import com.susu.core.ui.base.UiState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class NameState(
    val name: String = "",
    val isSelectedFriend: Boolean = false,
    val friendList: PersistentList<FriendSearch> = persistentListOf(),
) : UiState

sealed interface NameEffect : SideEffect {
    data class UpdateParentName(val name: String) : NameEffect
    data class UpdateParentFriendId(val friendId: Long?) : NameEffect
    data object FocusClear : NameEffect
    data object ShowNotValidSnackbar : NameEffect
}
