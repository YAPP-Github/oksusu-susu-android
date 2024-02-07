package com.susu.feature.sent

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.argument.EnvelopeFilterArgument
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.encodeToUri
import com.susu.domain.usecase.envelope.GetEnvelopesHistoryListUseCase
import com.susu.domain.usecase.envelope.GetEnvelopesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class SentViewModel @Inject constructor(
    private val getEnvelopesListUseCase: GetEnvelopesListUseCase,
    private val getEnvelopesHistoryListUseCase: GetEnvelopesHistoryListUseCase,
) : BaseViewModel<SentState, SentEffect>(
    SentState(),
) {
    private var page = 0

    fun getEnvelopesList(refresh: Boolean?) = viewModelScope.launch {
        if (currentState.isLoading) return@launch

        intent { copy(isLoading = true) }

        if (refresh == true) {
            intent { copy(envelopesList = persistentListOf()) }
            page = 0
        }

        getEnvelopesListUseCase(
            GetEnvelopesListUseCase.Param(page = page),
        ).onSuccess { envelopesList ->
            page++
            val newEnvelopesList = currentState.envelopesList.plus(envelopesList.map { it.toState() }).toPersistentList()
            intent {
                copy(
                    envelopesList = newEnvelopesList,
                    showEmptyEnvelopes = newEnvelopesList.isEmpty(),
                )
            }
        }

        intent { copy(isLoading = false) }
    }

    fun getEnvelopesHistoryList(id: Long) = viewModelScope.launch {
        val friendsList: List<Long> = listOf(id)
        val includeList = listOf("CATEGORY", "FRIEND")

        getEnvelopesHistoryListUseCase(
            GetEnvelopesHistoryListUseCase.Param(friendIds = friendsList, include = includeList),
        ).onSuccess { history ->
            val envelopesHistorySubList = if (history.size < 3) history else history.take(3)
            val newEnvelopesHistoryList = envelopesHistorySubList.toPersistentList()
            intent {
                copy(
                    envelopesList = envelopesList.map {
                        if (it.friend.id == id) {
                            it.copy(
                                envelopesHistoryList = newEnvelopesHistoryList,
                                expand = !it.expand,
                            )
                        } else {
                            it
                        }
                    }.toPersistentList(),
                )
            }
        }
    }

    fun deleteEmptyFriendStatistics(id: Long) {
        val filteredList = currentState.envelopesList.filter {
            it.friend.id != id
        }.toPersistentList()

        intent { copy(envelopesList = filteredList) }
    }

    fun navigateSentEnvelope(id: Long) = postSideEffect(SentEffect.NavigateEnvelope(id = id))
    fun navigateSentAdd() = postSideEffect(SentEffect.NavigateEnvelopeAdd)
    fun navigateSentEnvelopeSearch() = postSideEffect(SentEffect.NavigateEnvelopeSearch)
    fun navigateEnvelopeFilter() = postSideEffect(SentEffect.NavigateEnvelopeFilter(Json.encodeToUri(EnvelopeFilterArgument())))
}
