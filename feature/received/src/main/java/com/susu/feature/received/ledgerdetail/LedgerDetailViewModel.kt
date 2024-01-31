package com.susu.feature.received.ledgerdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.susu.core.model.Envelope
import com.susu.core.model.Ledger
import com.susu.core.model.SearchEnvelope
import com.susu.core.model.exception.NotFoundLedgerException
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.core.ui.extension.encodeToUri
import com.susu.core.ui.util.to_yyyy_dot_MM_dot_dd
import com.susu.domain.usecase.envelope.SearchReceivedEnvelopeListUseCase
import com.susu.domain.usecase.ledger.DeleteLedgerUseCase
import com.susu.domain.usecase.ledger.GetLedgerUseCase
import com.susu.feature.received.navigation.ReceivedRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class LedgerDetailViewModel @Inject constructor(
    private val searchReceivedEnvelopeListUseCase: SearchReceivedEnvelopeListUseCase,
    private val deleteLedgerUseCase: DeleteLedgerUseCase,
    private val getLedgerUseCase: GetLedgerUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<LedgerDetailState, LedgerDetailSideEffect>(
    LedgerDetailState(),
) {
    private val argument = savedStateHandle.get<String>(ReceivedRoute.LEDGER_ARGUMENT_NAME)!!
    private var ledger = Ledger()

    private var page = 0
    private var isLast = false

    private var isFirstVisited: Boolean = true

    fun addEnvelopeIfNeed(envelopeUri: String?) = intent {
        val envelope = envelopeUri?.let {
            Json.decodeFromUri<Envelope>(it)
        } ?: return@intent this

        if (envelope.id in envelopeList.map { it.envelope.id }) return@intent this

        val searchEnvelope = SearchEnvelope(
            envelope = envelope,
            friend = envelope.friend,
            relation = envelope.relationship,
        )

        copy(
            envelopeList = envelopeList.add(0, searchEnvelope),
        )
    }

    fun updateEnvelopeIfNeed(envelopeUri: String?) = intent {
        val envelope = envelopeUri?.let {
            Json.decodeFromUri<Envelope>(it)
        } ?: return@intent this

        val searchEnvelope = SearchEnvelope(
            envelope = envelope,
            friend = envelope.friend,
            relation = envelope.relationship,
        )

        copy(
            envelopeList = envelopeList.map {
                run {
                    if (it.envelope.id == searchEnvelope.envelope.id) {
                        searchEnvelope
                    } else {
                        it
                    }
                }.run {
                    if (friend.id == searchEnvelope.friend.id) copy(
                        friend = searchEnvelope.friend,
                        relation = searchEnvelope.relation,
                    )
                    else this
                }
            }.toPersistentList(),
        )
    }

    fun deleteEnvelopeIfNeed(toDeleteEnvelopeId: Long?) {
        if (toDeleteEnvelopeId == null) return

        intent {
            copy(
                envelopeList = envelopeList
                    .filter { it.envelope.id != toDeleteEnvelopeId }
                    .toPersistentList(),
            )
        }
    }

    fun getLedger() = viewModelScope.launch {
        ledger = Json.decodeFromUri<Ledger>(argument)
        getLedgerUseCase(id = ledger.id)
            .onSuccess { ledger ->
                this@LedgerDetailViewModel.ledger = ledger
                intent {
                    with(ledger) {
                        val category = ledger.category
                        copy(
                            name = ledger.title,
                            money = ledger.totalAmounts,
                            count = ledger.totalCounts,
                            category = if (category.customCategory.isNullOrEmpty()) category.name else category.customCategory!!,
                            startDate = ledger.startAt.toJavaLocalDateTime().to_yyyy_dot_MM_dot_dd(),
                            endDate = ledger.endAt.toJavaLocalDateTime().to_yyyy_dot_MM_dot_dd(),
                        )
                    }
                }
            }
    }

    fun initReceivedEnvelopeList() {
        if (isFirstVisited.not()) return
        getReceivedEnvelopeList(true)
        isFirstVisited = false
    }

    fun getReceivedEnvelopeList(needClear: Boolean = false) = viewModelScope.launch {
        val currentList = if (needClear) {
            page = 0
            isLast = false
            emptyList()
        } else {
            currentState.envelopeList
        }

        searchReceivedEnvelopeListUseCase(
            param = SearchReceivedEnvelopeListUseCase.Param(
                friendIds = null,
                ledgerId = ledger.id,
                fromAmount = null,
                toAmount = null,
                page = page,
                sort = null,
            ),
        ).onSuccess { envelopeList ->
            isLast = envelopeList.isEmpty()
            page++
            val newEnvelopeList = currentList.plus(envelopeList).toPersistentList()
            intent {
                copy(
                    envelopeList = newEnvelopeList,
                )
            }
        }.onFailure {
            postSideEffect(LedgerDetailSideEffect.HandleException(it, ::getReceivedEnvelopeList))
        }
    }

    fun navigateLedgerEdit() = postSideEffect(LedgerDetailSideEffect.NavigateLedgerEdit(ledger))

    fun popBackStackWithLedger() = postSideEffect(LedgerDetailSideEffect.PopBackStackWithLedger(Json.encodeToUri(ledger)))
    fun showDeleteDialog() = postSideEffect(
        LedgerDetailSideEffect.ShowDeleteDialog(
            onConfirmRequest = ::deleteLedger,
        ),
    )

    private fun deleteLedger() = viewModelScope.launch {
        deleteLedgerUseCase(ledger.id)
            .onSuccess {
                postSideEffect(
                    LedgerDetailSideEffect.ShowDeleteSuccessSnackbar,
                    LedgerDetailSideEffect.PopBackStackWithDeleteLedgerId(ledger.id),
                )
            }
            .onFailure { throwable ->
                when (throwable) {
                    is NotFoundLedgerException -> postSideEffect(LedgerDetailSideEffect.ShowSnackbar(throwable.message))
                    else -> postSideEffect(LedgerDetailSideEffect.HandleException(throwable, ::deleteLedger))
                }
            }
    }

    fun navigateEnvelopeAdd() = postSideEffect(
        LedgerDetailSideEffect.NavigateEnvelopeAdd(ledger.category.customCategory ?: ledger.category.name, ledger.id),
    )

    fun navigateEnvelopeDetail(envelope: Envelope) = postSideEffect(LedgerDetailSideEffect.NavigateEnvelopeDetail(envelope, ledger.id))
}
