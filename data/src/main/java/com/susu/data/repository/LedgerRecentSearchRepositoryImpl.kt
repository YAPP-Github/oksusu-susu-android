package com.susu.data.repository

import com.susu.core.android.Dispatcher
import com.susu.core.android.SusuDispatchers
import com.susu.core.model.RecentSearch
import com.susu.data.local.dao.LedgerRecentSearchDao
import com.susu.data.local.model.toData
import com.susu.data.local.model.toModel
import com.susu.domain.repository.LedgerRecentSearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LedgerRecentSearchRepositoryImpl @Inject constructor(
    private val dao: LedgerRecentSearchDao,
    @Dispatcher(SusuDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : LedgerRecentSearchRepository {
    override suspend fun getSearchList(count: Int): List<RecentSearch> = withContext(ioDispatcher) {
        dao.getSearchList(count).map { it.toModel() }
    }

    override suspend fun getSearchListCount(): Int = withContext(ioDispatcher) {
        dao.getSearchListCount()
    }

    override suspend fun deleteBySearch(search: String) = withContext(ioDispatcher) {
        dao.deleteBySearch(search)
    }

    override suspend fun upsert(recentSearch: RecentSearch) = withContext(ioDispatcher) {
        dao.upsert(recentSearch.toData())
    }

    override suspend fun deleteOldestSearch() = withContext(ioDispatcher) {
        dao.deleteOldestSearch()
    }
}
