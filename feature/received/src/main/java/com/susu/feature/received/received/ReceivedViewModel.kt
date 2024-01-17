package com.susu.feature.received.received

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Ledger
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.domain.usecase.ledger.GetLedgerListUseCase
import com.susu.feature.received.navigation.argument.FilterArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReceivedViewModel @Inject constructor(
    private val getLedgerListUseCase: GetLedgerListUseCase,
) : BaseViewModel<ReceivedState, ReceivedEffect>(
    ReceivedState(),
) {
    private var page = 0
    private var isLast = false
    private var filter: FilterArgument = FilterArgument()
    private var isFirstVisit = true

    fun initData() {
        if (isFirstVisit.not()) return
        getLedgerList()
        isFirstVisit = false
    }

    fun filterIfNeed(filterUri: String?) {
        if (filterUri == null) return

        val filterArgument = Json.decodeFromUri<FilterArgument>(filterUri)
        if (filter == filterArgument) return

        filter = filterArgument
        intent {
            copy(
                selectedCategoryList = filter.selectedCategoryList.toPersistentList(),
                startAt = filter.startAt?.toJavaLocalDateTime(),
                endAt = filter.endAt?.toJavaLocalDateTime(),
            )
        }

        Timber.tag("테스트").d("filterIfNeed $page")
        getLedgerList(true)
    }

    fun getLedgerList(needClear: Boolean = false) = viewModelScope.launch {
        val currentList = if (needClear) {
            page = 0
            isLast = false
            emptyList()
        } else {
            currentState.ledgerList
        }

        if (isLast) return@launch

        Timber.tag("테스트").d("getLedgerList $page")

        getLedgerListUseCase(
            GetLedgerListUseCase.Param(
                page = page,
                categoryId = null, // TODO
                fromStartAt = currentState.startAt,
                toEndAt = currentState.endAt,
                sort = null,
            ),
        ).onSuccess { ledgerList ->
            isLast = ledgerList.isEmpty()
            page++
            val newLedgerList = currentList.plus(ledgerList).toPersistentList()
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
    fun navigateLedgerFilter(filterArgument: FilterArgument = filter) = postSideEffect(ReceivedEffect.NavigateLedgerFilter(filterArgument))
}
