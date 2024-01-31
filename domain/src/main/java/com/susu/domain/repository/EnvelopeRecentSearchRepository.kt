package com.susu.domain.repository

interface EnvelopeRecentSearchRepository {
    suspend fun getSearchList(count: Int): List<String>

    suspend fun getSearchListCount(): Int

    suspend fun deleteBySearchKeyword(searchKeyword: String)

    suspend fun upsert(searchKeyword: String)

    suspend fun deleteOldestSearchKeyword(count: Int)
}
