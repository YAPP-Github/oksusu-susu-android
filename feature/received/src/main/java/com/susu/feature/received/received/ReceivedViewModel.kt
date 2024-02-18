package com.susu.feature.received.received

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Category
import com.susu.core.model.Ledger
import com.susu.core.ui.base.BaseViewModel
import com.susu.core.ui.extension.decodeFromUri
import com.susu.core.ui.util.currentDate
import com.susu.core.ui.util.isBetween
import com.susu.domain.usecase.ledger.GetLedgerListUseCase
import com.susu.feature.received.navigation.argument.LedgerFilterArgument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class ReceivedViewModel @Inject constructor(
    private val getLedgerListUseCase: GetLedgerListUseCase,
) : BaseViewModel<ReceivedState, ReceivedEffect>(
    ReceivedState(),
) {
    private val mutex = Mutex()

    private var page = 0
    private var isLast = false
    private var filter: LedgerFilterArgument = LedgerFilterArgument()
    private var filterUri: String? = null
    private var isFirstVisit = true

    fun logSearchIconClickEvent() = postSideEffect(ReceivedEffect.LogSearchIconClickEvent)

    fun initData() {
        if (isFirstVisit.not()) return
        getLedgerList()
        isFirstVisit = false
    }

    fun filterIfNeed(filterUri: String?) {
        if (filterUri == null) return

        if (this.filterUri == filterUri) return
        this.filterUri = filterUri

        val ledgerFilterArgument = Json.decodeFromUri<LedgerFilterArgument>(filterUri)
        if (filter == ledgerFilterArgument) return

        filter = ledgerFilterArgument
        intent {
            copy(
                selectedCategoryList = filter.selectedCategoryList.toPersistentList(),
                startAt = filter.startAt?.toJavaLocalDateTime(),
                endAt = filter.endAt?.toJavaLocalDateTime(),
            )
        }

        getLedgerList(true)
    }

    fun removeCategory(category: Category) {
        intent {
            filter = filter.copy(
                selectedCategoryList = selectedCategoryList.minus(category),
            )
            copy(
                selectedCategoryList = selectedCategoryList.minus(category).toPersistentList(),
            )
        }

        getLedgerList(true)
    }

    fun clearDate() {
        intent {
            copy(
                startAt = null,
                endAt = null,
            )
        }

        getLedgerList(true)
    }

    fun getLedgerList(needClear: Boolean = false, onFinish: () -> Unit = {}) = viewModelScope.launch {
        mutex.withLock {
            val currentList = if (needClear) {
                page = 0
                isLast = false
                emptyList()
            } else {
                currentState.ledgerList
            }

            if (isLast) return@launch

            getLedgerListUseCase(
                GetLedgerListUseCase.Param(
                    page = page,
                    categoryIdList = filter.selectedCategoryList.map { it.id },
                    fromStartAt = currentState.startAt,
                    toEndAt = currentState.endAt,
                    sort = LedgerAlign.entries[currentState.selectedAlignPosition].query,
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

            if (needClear) postSideEffect(ReceivedEffect.ScrollToTop)
            onFinish()
        }
    }

    fun updateLedgerIfNeed(ledger: String?, toDeleteLedgerId: Long) {
        val toUpdateLedger = ledger?.let {
            Json.decodeFromUri<Ledger>(ledger)
        } ?: Ledger()
        val newList = currentState
            .ledgerList
            .filter { it.id != toDeleteLedgerId }
            .map { if (it.id == toUpdateLedger.id) toUpdateLedger else it }
            .toPersistentList()

        intent {
            copy(
                ledgerList = newList,
                showEmptyLedger = newList.isEmpty(),
            )
        }
    }

    fun addLedgerIfNeed(ledger: String?) {
        val toAddLedger = ledger?.let {
            Json.decodeFromUri<Ledger>(ledger)
        } ?: return

        if (toAddLedger.id in currentState.ledgerList.map { it.id }) return

        if (filter.selectedCategoryList.isNotEmpty() && toAddLedger.category !in filter.selectedCategoryList) return

        if (
            isBetween(
                target = toAddLedger.startAt.toJavaLocalDateTime(),
                start = filter.startAt?.toJavaLocalDateTime() ?: currentDate.minusYears(100),
                end = filter.endAt?.toJavaLocalDateTime() ?: currentDate.plusYears(100),
            ).not()
        ) {
            return
        }

        intent {
            copy(
                ledgerList = currentState
                    .ledgerList
                    .add(0, toAddLedger),
                showEmptyLedger = false,
            )
        }
    }

    fun showAlignBottomSheet() = intent { copy(showAlignBottomSheet = true) }
    fun hideAlignBottomSheet() = intent { copy(showAlignBottomSheet = false) }
    fun navigateLedgerDetail(ledger: Ledger) = postSideEffect(ReceivedEffect.NavigateLedgerDetail(ledger))
    fun navigateLedgerAdd() = postSideEffect(ReceivedEffect.NavigateLedgerAdd)
    fun navigateLedgerSearch() = postSideEffect(ReceivedEffect.NavigateLedgerSearch)
    fun navigateLedgerFilter() = postSideEffect(
        ReceivedEffect.NavigateLedgerFilter(
            with(currentState) {
                LedgerFilterArgument(
                    selectedCategoryList = selectedCategoryList,
                    startAt = startAt?.toKotlinLocalDateTime(),
                    endAt = endAt?.toKotlinLocalDateTime(),
                )
            },
        ),
    )

    fun updateSelectedAlignPosition(position: Int) {
        intent { copy(selectedAlignPosition = position) }
        getLedgerList(true)
    }
}
