package com.susu.feature.envelopesearch

import androidx.lifecycle.viewModelScope
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.enveloperecentsearch.DeleteEnvelopeRecentSearchUseCase
import com.susu.domain.usecase.enveloperecentsearch.GetEnvelopeRecentSearchListUseCase
import com.susu.domain.usecase.enveloperecentsearch.UpsertEnvelopeRecentSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnvelopeSearchViewModel @Inject constructor(
    private val getEnvelopeRecentSearchUserCase: GetEnvelopeRecentSearchListUseCase,
    private val deleteEnvelopeRecentSearchUseCase: DeleteEnvelopeRecentSearchUseCase,
    private val upsertEnvelopeRecentSearchUseCase: UpsertEnvelopeRecentSearchUseCase,
) : BaseViewModel<EnvelopeSearchState, EnvelopeSearchEffect>(EnvelopeSearchState()) {

    fun getEnvelopeRecentSearchList() {
        viewModelScope.launch {
            getEnvelopeRecentSearchUserCase().onSuccess(::updateRecentSearchList)
        }
    }

    fun deleteEnvelopeRecentSearch(search: String) {
        viewModelScope.launch {
            deleteEnvelopeRecentSearchUseCase(search).onSuccess(::updateRecentSearchList)
        }
    }

    fun upsertEnvelopeRecentSearch(search: String) {
        viewModelScope.launch {
            upsertEnvelopeRecentSearchUseCase(search).onSuccess(::updateRecentSearchList)
        }
    }

    fun updateSearchKeyword(search: String) = intent {
        copy(
            searchKeyword = search,
            envelopeList = if (search.isBlank()) persistentListOf() else envelopeList,
        )
    }

    fun getEnvelopeList(search: String) = viewModelScope.launch {
        // TODO: 친구 검색 -> 결과가 있으면 봉투 검색
        // TODO: 카테고리 검색 -> 결과가 있으면 봉투 검색
    }

    private fun updateRecentSearchList(searchList: List<String>) {
        intent {
            copy(recentSearchKeywordList = searchList.toPersistentList())
        }
    }

}
