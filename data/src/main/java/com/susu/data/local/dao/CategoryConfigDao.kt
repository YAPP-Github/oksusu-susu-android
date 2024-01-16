package com.susu.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.susu.data.local.model.CategoryConfigEntity
import com.susu.data.local.model.EntityTable

@Dao
interface CategoryConfigDao {
    @Query("SELECT * FROM ${EntityTable.CATEGORY_CONFIG}")
    fun getCategoryConfig(): List<CategoryConfigEntity>

    @Insert
    fun insert(categoryConfig: List<CategoryConfigEntity>)
}
