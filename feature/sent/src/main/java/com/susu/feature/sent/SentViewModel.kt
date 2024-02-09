package com.susu.feature.sent

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Friend
import com.susu.core.ui.argument.EnvelopeFilterArgument
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.core.ui.extension.encodeToUri
import com.susu.domain.usecase.envelope.GetEnvelopesHistoryListUseCase
import com.susu.domain.usecase.envelope.GetEnvelopesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class SentViewModel @Inject constructor(
    private val getEnvelopesListUseCase: GetEnvelopesListUseCase,
    private val getEnvelopesHistoryListUseCase: GetEnvelopesHistoryListUseCase,
) : BaseViewModel<SentState, SentEffect>(
    SentState(),
) {
    private val mutex = Mutex()
    private var page = 0
    private var filter: EnvelopeFilterArgument = EnvelopeFilterArgument()
    private var filterUri: String? = null

    fun getEnvelopesList(refresh: Boolean?) = viewModelScope.launch {
        mutex.withLock {
            val currentList = if (refresh == true) {
                page = 0
                emptyList()
            } else {
                currentState.envelopesList
            }

            getEnvelopesListUseCase(
                GetEnvelopesListUseCase.Param(
                    page = page,
                    friendIds = currentState.selectedFriendList.map { it.id },
                    fromTotalAmounts = currentState.fromAmount,
                    toTotalAmounts = currentState.toAmount,
                    sort = EnvelopeAlign.entries[currentState.selectedAlignPosition].query,
                ),
            ).onSuccess { envelopesList ->
                page++
                val newEnvelopesList = currentList.plus(envelopesList.map { it.toState() }).toPersistentList()
                intent {
                    copy(
                        envelopesList = newEnvelopesList,
                        showEmptyEnvelopes = newEnvelopesList.isEmpty(),
                    )
                }
            }.onFailure {
                intent {
                    copy(
                        showEmptyEnvelopes = true,
                    )
                }
            }

            if (refresh == true) postSideEffect(SentEffect.ScrollToTop)
        }
    }

    fun filterIfNeed(filterUri: String?) {
        if (filterUri == null) return

        if (this.filterUri == filterUri) return
        this.filterUri = filterUri

        val ledgerFilterArgument = Json.decodeFromUri<EnvelopeFilterArgument>(filterUri)
        if (filter == ledgerFilterArgument) return

        filter = ledgerFilterArgument
        intent {
            copy(
                selectedFriendList = filter.selectedFriendList.toPersistentList(),
                fromAmount = filter.fromAmount,
                toAmount = filter.toAmount,
            )
        }

        getEnvelopesList(true)
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
                                expand = true,
                            )
                        } else {
                            it
                        }
                    }.toPersistentList(),
                )
            }
        }
    }

    fun hideEnvelopesHistoryList(id: Long) {
        intent {
            copy(
                envelopesList = envelopesList.map {
                    if (it.friend.id == id) {
                        it.copy(expand = false)
                    } else {
                        it
                    }
                }.toPersistentList(),
            )
        }
    }

    fun deleteEmptyFriendStatistics(id: Long) {
        val filteredList = currentState.envelopesList.filter {
            it.friend.id != id
        }.toPersistentList()

        intent { copy(envelopesList = filteredList) }
    }

    fun editFriendStatistics(id: Long) = viewModelScope.launch {
        getEnvelopesListUseCase(
            GetEnvelopesListUseCase.Param(
                friendIds = listOf(id),
            ),
        ).onSuccess { list ->
            val friendStatistics = list.firstOrNull() ?: return@launch
            val editedEnvelopeList = currentState.envelopesList.map {
                if (it.friend.id == id) {
                    it.copy(
                        receivedAmounts = friendStatistics.receivedAmounts,
                        sentAmounts = friendStatistics.sentAmounts,
                        totalAmounts = friendStatistics.totalAmounts,
                        expand = true,
                    )
                } else {
                    it
                }
            }.toPersistentList()

            intent {
                copy(envelopesList = editedEnvelopeList)
            }

            getEnvelopesHistoryList(id)
        }
    }

    fun navigateSentEnvelope(id: Long) = postSideEffect(SentEffect.NavigateEnvelope(id = id))
    fun navigateSentAdd() = postSideEffect(SentEffect.NavigateEnvelopeAdd)
    fun navigateSentEnvelopeSearch() = postSideEffect(SentEffect.NavigateEnvelopeSearch)
    fun navigateEnvelopeFilter() = postSideEffect(
        SentEffect.NavigateEnvelopeFilter(
            Json.encodeToUri(
                EnvelopeFilterArgument(
                    isSent = true,
                    selectedFriendList = currentState.selectedFriendList,
                    fromAmount = currentState.fromAmount,
                    toAmount = currentState.toAmount,
                ),
            ),
        ),
    )

    fun unselectFriend(friend: Friend) {
        intent {
            copy(
                selectedFriendList = selectedFriendList.remove(friend),
            )
        }

        getEnvelopesList(true)
    }

    fun removeMoney() {
        intent {
            copy(
                fromAmount = null,
                toAmount = null,
            )
        }

        getEnvelopesList(true)
    }

    fun showAlignBottomSheet() = intent {
        copy(showAlignBottomSheet = true)
    }

    fun hideAlignBottomSheet() = intent {
        copy(showAlignBottomSheet = false)
    }

    fun updateAlignPosition(position: Int) {
        hideAlignBottomSheet()
        intent {
            copy(selectedAlignPosition = position)
        }
        getEnvelopesList(true)
    }
}
