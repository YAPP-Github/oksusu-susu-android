package com.susu.feature.envelopeadd.content.name

import androidx.lifecycle.viewModelScope
import com.susu.core.model.FriendSearch
import com.susu.core.ui.USER_INPUT_REGEX
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.friend.SearchFriendUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor(
    private val searchFriendUseCase: SearchFriendUseCase,
) : BaseViewModel<NameState, NameEffect>(NameState()) {

    fun updateName(name: String) {
        if (!USER_INPUT_REGEX.matches(name)) { // 한글, 영문 0~10 글자
            if (name.length > 10) { // 길이 넘친 경우
                postSideEffect(NameEffect.ShowNotValidSnackbar)
            }
            return // 특수문자는 입력 안 됨
        }

        intent {
            postSideEffect(
                NameEffect.UpdateParentName(name),
                NameEffect.UpdateParentFriendId(null),
            )
            copy(
                name = name,
                friendList = if (name.isEmpty()) persistentListOf() else friendList,
                isSelectedFriend = false,
            )
        }
    }

    fun selectFriend(friend: FriendSearch) = intent {
        postSideEffect(
            NameEffect.FocusClear,
            NameEffect.UpdateParentName(friend.friend.name),
            NameEffect.UpdateParentFriendId(friend.friend.id),
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

    fun showKeyboardIfTextEmpty() = viewModelScope.launch {
        if (currentState.name.isEmpty()) {
            delay(500L)
            postSideEffect(NameEffect.ShowKeyboard)
        }
    }
}
