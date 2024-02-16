package com.susu.feature.envelope

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.envelope.GetEnvelopesHistoryListUseCase
import com.susu.domain.usecase.envelope.GetEnvelopesListUseCase
import com.susu.feature.sent.navigation.SentRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SentEnvelopeViewModel @Inject constructor(
    private val getEnvelopesListUseCase: GetEnvelopesListUseCase,
    private val getEnvelopesHistoryListUseCase: GetEnvelopesHistoryListUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<SentEnvelopeState, SentEnvelopeSideEffect>(
    SentEnvelopeState(),
) {
    private val friendId = savedStateHandle.get<Long>(SentRoute.FRIEND_ID_ARGUMENT_NAME)!!

    fun initData(onFinish: () -> Unit = {}) {
        getEnvelopeInfo()
        getEnvelopeHistoryList()
        onFinish()
    }

    private fun getEnvelopeInfo(id: Long = friendId) = viewModelScope.launch {
        val friendsList: List<Long> = listOf(id)

        getEnvelopesListUseCase(
            GetEnvelopesListUseCase.Param(friendIds = friendsList),
        ).onSuccess { envelope ->
            envelope.getOrNull(0)?.let {
                intent {
                    copy(
                        envelopeInfo = it,
                    )
                }
            } ?: postSideEffect(SentEnvelopeSideEffect.PopBackStackWithDeleteFriendId(id))
        }
    }

    fun getEnvelopeHistoryList(id: Long = friendId) = viewModelScope.launch {
        val friendsList: List<Long> = listOf(id)
        val includeList = listOf("CATEGORY")

        getEnvelopesHistoryListUseCase(
            GetEnvelopesHistoryListUseCase.Param(friendIds = friendsList, include = includeList),
        ).onSuccess { history ->
            intent {
                copy(
                    envelopeHistoryList = history.toPersistentList(),
                )
            }
        }
    }

    fun navigateSentEnvelopeAdd() = postSideEffect(SentEnvelopeSideEffect.NavigateEnvelopeAdd(currentState.envelopeInfo.friend))
    fun navigateSentEnvelopeDetail(id: Long) = postSideEffect(SentEnvelopeSideEffect.NavigateEnvelopeDetail(id = id))
    fun popBackStack() = postSideEffect(SentEnvelopeSideEffect.PopBackStack)
}
