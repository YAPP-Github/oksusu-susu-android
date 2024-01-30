package com.susu.feature.sent

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.envelope.GetEnvelopesHistoryListUseCase
import com.susu.domain.usecase.envelope.GetEnvelopesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SentViewModel @Inject constructor(
    private val getEnvelopesListUseCase: GetEnvelopesListUseCase,
    private val getEnvelopesHistoryListUseCase: GetEnvelopesHistoryListUseCase,
) : BaseViewModel<SentState, SentEffect>(
    SentState(),
) {
    private var page = 0

    fun getEnvelopesList() = viewModelScope.launch {
        getEnvelopesListUseCase(
            GetEnvelopesListUseCase.Param(page = page),
        ).onSuccess { envelopesList ->
            page++
            val newEnvelopesList = currentState.envelopesList.plus(envelopesList).toPersistentList()
            intent {
                copy(
                    envelopesList = newEnvelopesList,
                    showEmptyEnvelopes = newEnvelopesList.isEmpty(),
                )
            }
        }
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
                    envelopesHistoryList = newEnvelopesHistoryList,
                )
            }
        }
    }

    fun navigateSentEnvelope() = postSideEffect(SentEffect.NavigateEnvelope)
    fun navigateSentAdd() = postSideEffect(SentEffect.NavigateEnvelopeAdd)
}
