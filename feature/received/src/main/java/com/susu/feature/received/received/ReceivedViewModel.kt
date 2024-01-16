package com.susu.feature.received.received

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Ledger
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.domain.usecase.ledger.GetLedgerListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class ReceivedViewModel @Inject constructor(
    private val getLedgerListUseCase: GetLedgerListUseCase,
) : BaseViewModel<ReceivedState, ReceivedEffect>(
    ReceivedState(),
) {
    private var page = 0
    private var isLast = false

    fun getLedgerList() = viewModelScope.launch {
        if (isLast) return@launch

        getLedgerListUseCase(
            GetLedgerListUseCase.Param(
                page = page,
            ),
        ).onSuccess { ledgerList ->
            isLast = ledgerList.isEmpty()
            page++
            val newLedgerList = currentState.ledgerList.plus(ledgerList).toPersistentList()
            intent {
                copy(
                    ledgerList = newLedgerList,
                    showEmptyLedger = newLedgerList.isEmpty(),
                )
            }
        }
    }

    fun updateLedgerIfNeed(ledger: String?, toDeleteLedgerId: Int) {
        val toUpdateLedger = ledger?.let {
            Json.decodeFromUri<Ledger>(ledger)
        } ?: Ledger()
        val newList = currentState
            .ledgerList
            .filter { it.id != toDeleteLedgerId }
            .map { if (it.id == toUpdateLedger.id) toUpdateLedger else it }
            .toPersistentList()

        intent { copy(ledgerList = newList) }
    }

    fun showAlignBottomSheet() = intent { copy(showAlignBottomSheet = true) }
    fun hideAlignBottomSheet() = intent { copy(showAlignBottomSheet = false) }
    fun navigateLedgerDetail(ledger: Ledger) = postSideEffect(ReceivedEffect.NavigateLedgerDetail(ledger))
    fun navigateLedgerAdd() = postSideEffect(ReceivedEffect.NavigateLedgerAdd)
    fun navigateLedgerSearch() = postSideEffect(ReceivedEffect.NavigateLedgerSearch)
    fun navigateLedgerFilter() = postSideEffect(ReceivedEffect.NavigateLedgerFilter)
}
