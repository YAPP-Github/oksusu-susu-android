package com.susu.data.data.repository

import com.susu.core.android.Dispatcher
import com.susu.core.android.SusuDispatchers
import com.susu.data.local.dao.EnvelopeRecentSearchDao
import com.susu.data.local.model.EnvelopeRecentSearchEntity
import com.susu.domain.repository.EnvelopeRecentSearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EnvelopeRecentSearchRepositoryImpl @Inject constructor(
    private val dao: EnvelopeRecentSearchDao,
    @Dispatcher(SusuDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : EnvelopeRecentSearchRepository {
    override suspend fun getSearchList(count: Int): List<String> = withContext(ioDispatcher) {
        dao.getSearchList(count).map { it.searchKeyword }
    }

    override suspend fun getSearchListCount(): Int = withContext(ioDispatcher) {
        dao.getSearchListCount()
    }

    override suspend fun deleteBySearchKeyword(searchKeyword: String) = withContext(ioDispatcher) {
        dao.deleteBySearch(searchKeyword)
    }

    override suspend fun upsert(searchKeyword: String) = withContext(ioDispatcher) {
        val recentSearch = EnvelopeRecentSearchEntity(
            searchKeyword = searchKeyword,
            saveTime = System.currentTimeMillis(),
        )

        dao.upsert(recentSearch)
    }

    override suspend fun deleteOldestSearchKeyword(count: Int) = withContext(ioDispatcher) {
        dao.deleteOldestSearch(count)
    }
}
