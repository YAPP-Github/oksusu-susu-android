package com.susu.feature.received.search

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.ledger.GetLedgerListUseCase
import com.susu.domain.usecase.ledgerrecentsearch.DeleteLedgerRecentSearchUseCase
import com.susu.domain.usecase.ledgerrecentsearch.GetLedgerRecentSearchListUseCase
import com.susu.domain.usecase.ledgerrecentsearch.UpsertLedgerRecentSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
    fun navigateLedgerDetail(id: Int) = postSideEffect(LedgerSearchSideEffect.NavigateLedgerDetail(id))

    fun getLedgerRecentSearchList() = viewModelScope.launch {
        getLedgerRecentSearchListUseCase()
            .onSuccess(::updateRecentSearchList)
            .onFailure { }
    }

    fun deleteLedgerRecentSearch(search: String) = viewModelScope.launch {
        deleteLedgerRecentSearchUseCase(search)
            .onSuccess(::updateRecentSearchList)
            .onFailure { }
    }

    fun upsertLedgerRecentSearch(search: String) = viewModelScope.launch {
        upsertLedgerRecentSearchUseCase(search)
            .onSuccess(::updateRecentSearchList)
            .onFailure { }
    }

    fun updateSearch(search: String) = intent { copy(searchKeyword = search) }

    fun getLedgerList(search: String) = viewModelScope.launch {
        getLedgerListUseCase(GetLedgerListUseCase.Param(title = search))
            .onSuccess { intent { copy(ledgerList = it.toPersistentList()) } }
    }

    fun popBackStack() = postSideEffect(LedgerSearchSideEffect.PopBackStack)

    private fun updateRecentSearchList(searchList: List<String>) = intent { copy(recentSearchKeywordList = searchList.toPersistentList()) }
}
