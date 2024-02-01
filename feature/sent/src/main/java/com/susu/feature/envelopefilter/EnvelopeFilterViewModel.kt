package com.susu.feature.envelopefilter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.model.Friend
import com.susu.core.ui.argument.EnvelopeFilterArgument
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.core.ui.extension.encodeToUri
import com.susu.domain.usecase.friend.SearchFriendUseCase
import com.susu.feature.sent.navigation.SentRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class EnvelopeFilterViewModel @Inject constructor(
    private val searchFriendUseCase: SearchFriendUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<EnvelopeFilterState, EnvelopeFilterSideEffect>(
    EnvelopeFilterState(),
) {
    private val argument = savedStateHandle.get<String>(SentRoute.FILTER_ENVELOPE_ARGUMENT)!!
    private var filter = EnvelopeFilterArgument()

    fun initData() {
        initFilter()
    }

    private fun initFilter() {
        filter = Json.decodeFromUri(argument)
        intent {
            copy(
                selectedFriendList = filter.selectedFriendList.toPersistentList(),
                fromAmount = filter.fromAmount,
                toAmount = filter.toAmount,
            )
        }
    }

    fun updateName(searchKeyword: String) = intent {
        copy(
            searchKeyword = searchKeyword,
        )
    }

    fun getFriendList(search: String) = viewModelScope.launch {
        searchFriendUseCase(name = search)
            .onSuccess {
                intent {
                    copy(
                        friendList = it.map { it.friend }.toPersistentList(),
                    )
                }
            }
    }

    fun popBackStack() = postSideEffect(EnvelopeFilterSideEffect.PopBackStack)
    fun popBackStackWithFilter() {
        val filter = EnvelopeFilterArgument(
            selectedFriendList = currentState.selectedFriendList,
            fromAmount = currentState.fromAmount,
            toAmount = currentState.toAmount,
        )

        postSideEffect(EnvelopeFilterSideEffect.PopBackStackWithFilter(Json.encodeToUri(filter)))
    }
}
