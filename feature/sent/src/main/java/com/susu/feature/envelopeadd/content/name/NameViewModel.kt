package com.susu.feature.envelopeadd.content.name

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor() : BaseViewModel<NameState, NameEffect>(NameState()) {

    fun updateName(name: String) = intent {
        postSideEffect(NameEffect.UpdateParentName(name), NameEffect.SearchFriendByName)
        copy(name = name)
    }

    // 검색 결과로 나온 이름을 선택한 경우 - 검색 하지 않음
    fun selectName(name: String) = intent {
        postSideEffect(NameEffect.UpdateParentName(name))
        copy(name = name)
    }

    fun searchFriend() {
        // TODO: 현재 입력된 String으로 친구 검색하여 표시
    }

    fun mockSearch() {
        viewModelScope.launch {
            intent { copy(isSearching = true) }
            delay(2000L)
            intent { copy(isSearching = false, searchedFriends = listOf("김철수")) }
        }
    }
}
