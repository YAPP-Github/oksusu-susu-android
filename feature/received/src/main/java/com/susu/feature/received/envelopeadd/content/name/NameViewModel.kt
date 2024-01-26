package com.susu.feature.received.envelopeadd.content.name

import androidx.lifecycle.viewModelScope
import com.susu.core.model.FriendSearch
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.friend.SearchFriendUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor(
    private val searchFriendUseCase: SearchFriendUseCase,
) : BaseViewModel<NameState, NameSideEffect>(
    NameState(),
) {
    fun updateName(name: String) = intent {
        postSideEffect(
            NameSideEffect.UpdateParentName(name),
            NameSideEffect.UpdateParentFriendId(null),
        )
        copy(
            name = name,
            friendList = if (name.isEmpty()) persistentListOf() else friendList,
            isSelectedFriend = false,
        )
    }

    fun selectFriend(friend: FriendSearch) = intent {
        postSideEffect(
            NameSideEffect.FocusClear,
            NameSideEffect.UpdateParentName(friend.friend.name),
            NameSideEffect.UpdateParentFriendId(friend.friend.id),
        )
        copy(
            name = friend.friend.name,
            isSelectedFriend = true,
        )
    }

    fun getFriendList(search: String) = viewModelScope.launch {
        if (search.isEmpty()) return@launch
        if (currentState.isSelectedFriend) return@launch

        searchFriendUseCase(name = search)
            .onSuccess {
                intent {
                    copy(
                        friendList = it.toPersistentList(),
                    )
                }
            }
    }
}
