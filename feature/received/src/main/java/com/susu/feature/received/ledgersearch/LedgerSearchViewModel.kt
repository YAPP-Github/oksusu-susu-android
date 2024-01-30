package com.susu.feature.received.ledgersearch

import androidx.lifecycle.viewModelScope
import com.susu.core.model.Ledger
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.ledger.GetLedgerListUseCase
import com.susu.domain.usecase.ledgerrecentsearch.DeleteLedgerRecentSearchUseCase
import com.susu.domain.usecase.ledgerrecentsearch.GetLedgerRecentSearchListUseCase
import com.susu.domain.usecase.ledgerrecentsearch.UpsertLedgerRecentSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LedgerSearchViewModel @Inject constructor(
    private val upsertLedgerRecentSearchUseCase: UpsertLedgerRecentSearchUseCase,
    private val getLedgerRecentSearchListUseCase: GetLedgerRecentSearchListUseCase,
    private val deleteLedgerRecentSearchUseCase: DeleteLedgerRecentSearchUseCase,
    private val getLedgerListUseCase: GetLedgerListUseCase,
) : BaseViewModel<LedgerSearchState, LedgerSearchSideEffect>(
    LedgerSearchState(),
) {
    fun navigateLedgerDetail(ledger: Ledger) = postSideEffect(LedgerSearchSideEffect.NavigateLedgerDetail(ledger))

    fun getLedgerRecentSearchList() = viewModelScope.launch {
        getLedgerRecentSearchListUseCase()
            .onSuccess(::updateRecentSearchList)
    }

    fun deleteLedgerRecentSearch(search: String) = viewModelScope.launch {
        deleteLedgerRecentSearchUseCase(search)
            .onSuccess(::updateRecentSearchList)
    }

    fun upsertLedgerRecentSearch(search: String) = viewModelScope.launch {
        upsertLedgerRecentSearchUseCase(search)
            .onSuccess(::updateRecentSearchList)
    }

    fun clearFocus() = postSideEffect(LedgerSearchSideEffect.FocusClear)

    fun hideSearchResultEmpty() = intent { copy(showSearchResultEmpty = false) }

    fun updateSearch(search: String) = intent {
        copy(
            searchKeyword = search,
            ledgerList = if (search.isBlank()) persistentListOf() else ledgerList,
        )
    }

    fun getLedgerList(search: String) = viewModelScope.launch {
        if (search.isBlank()) return@launch

        getLedgerListUseCase(GetLedgerListUseCase.Param(title = search))
            .onSuccess {
                intent {
                    copy(
                        ledgerList = it.toPersistentList(),
                        showSearchResultEmpty = it.isEmpty(),
                    )
                }
            }
    }

    fun popBackStack() = postSideEffect(LedgerSearchSideEffect.PopBackStack)

    private fun updateRecentSearchList(searchList: List<String>) = intent {
        copy(
            recentSearchKeywordList = searchList.toPersistentList(),
        )
    }
}
