package com.susu.feature.received.received

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Ledger
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.ledger.GetLedgerListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
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

    fun showAlignBottomSheet() = intent { copy(showAlignBottomSheet = true) }
    fun hideAlignBottomSheet() = intent { copy(showAlignBottomSheet = false) }
    fun navigateLedgerDetail(ledger: Ledger) = postSideEffect(ReceivedEffect.NavigateLedgerDetail(ledger))
    fun navigateLedgerAdd() = postSideEffect(ReceivedEffect.NavigateLedgerAdd)
    fun navigateLedgerSearch() = postSideEffect(ReceivedEffect.NavigateLedgerSearch)
    fun navigateLedgerFilter() = postSideEffect(ReceivedEffect.NavigateLedgerFilter)
}
