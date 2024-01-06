package com.susu.feature.received.search

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
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
) : BaseViewModel<LedgerSearchState, LedgerSearchSideEffect>(
    LedgerSearchState(),
) {
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

    fun updateSearch(search: String) = intent { copy(search = search) }

    fun popBackStack() = postSideEffect(LedgerSearchSideEffect.PopBackStack)

    private fun updateRecentSearchList(searchList: List<String>) = intent { copy(searchList = searchList.toPersistentList()) }
}
