package com.susu.domain.repository

import com.susu.core.model.RecentSearch

interface LedgerRecentSearchRepository {
    suspend fun getSearchList(count: Int): List<RecentSearch>

    suspend fun getSearchListCount(): Int

    suspend fun deleteBySearch(search: String)

    suspend fun upsert(search: String)

    suspend fun deleteOldestSearch()
}
