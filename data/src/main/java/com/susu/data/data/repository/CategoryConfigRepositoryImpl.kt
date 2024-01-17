package com.susu.data.data.repository

import com.susu.core.android.Dispatcher
import com.susu.core.android.SusuDispatchers
import com.susu.core.model.Category
import com.susu.data.local.dao.CategoryConfigDao
import com.susu.data.local.model.toEntity
import com.susu.data.local.model.toModel
import com.susu.data.remote.api.CategoryService
import com.susu.data.remote.model.response.toModel
import com.susu.domain.repository.CategoryConfigRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryConfigRepositoryImpl @Inject constructor(
    private val dao: CategoryConfigDao,
    private val api: CategoryService,
    @Dispatcher(SusuDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : CategoryConfigRepository {
    override suspend fun getCategoryConfig(): List<Category> = withContext(ioDispatcher) {
        val localCategoryConfig = dao.getCategoryConfig().map { it.toModel() }
        if (localCategoryConfig.isNotEmpty()) return@withContext localCategoryConfig

        val remoteCategoryConfig = api.getCategoryConfig().getOrThrow().map { it.toModel() }
        dao.insert(remoteCategoryConfig.map { it.toEntity() })
        return@withContext remoteCategoryConfig
    }
}
