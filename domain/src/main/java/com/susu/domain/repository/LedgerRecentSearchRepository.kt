package com.susu.domain.repository

interface LedgerRecentSearchRepository {
    suspend fun getSearchList(count: Int): List<String>

    suspend fun getSearchListCount(): Int

    suspend fun deleteBySearch(search: String)

    suspend fun upsert(search: String)

    suspend fun deleteOldestSearch(count: Int)
}
