package com.susu.data.repository

import com.susu.core.android.Dispatcher
import com.susu.core.android.SusuDispatchers
import com.susu.data.local.dao.LedgerRecentSearchDao
import com.susu.data.local.model.LedgerRecentSearchEntity
import com.susu.domain.repository.LedgerRecentSearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LedgerRecentSearchRepositoryImpl @Inject constructor(
    private val dao: LedgerRecentSearchDao,
    @Dispatcher(SusuDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : LedgerRecentSearchRepository {
    override suspend fun getSearchList(count: Int): List<String> = withContext(ioDispatcher) {
        dao.getSearchList(count).map { it.search }
    }

    override suspend fun getSearchListCount(): Int = withContext(ioDispatcher) {
        dao.getSearchListCount()
    }

    override suspend fun deleteBySearch(search: String) = withContext(ioDispatcher) {
        dao.deleteBySearch(search)
    }

    override suspend fun upsert(search: String) = withContext(ioDispatcher) {
        val recentSearch = LedgerRecentSearchEntity(
            search = search,
            saveTime = System.currentTimeMillis(),
        )

        dao.upsert(recentSearch)
    }

    override suspend fun deleteOldestSearch(count: Int) = withContext(ioDispatcher) {
        dao.deleteOldestSearch(count)
    }
}
