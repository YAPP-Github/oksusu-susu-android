package com.susu.domain.repository

import com.susu.core.model.Category

interface CategoryConfigRepository {
    suspend fun getCategoryConfig(): List<Category>
}
