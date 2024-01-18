package com.susu.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.susu.data.local.dao.CategoryConfigDao
import com.susu.data.local.model.CategoryConfigEntity

@Database(
    entities = [
        CategoryConfigEntity::class,
    ],
    version = 1,
)
abstract class RoomInMemoryDataBase : RoomDatabase() {
    abstract fun categoryConfigDao(): CategoryConfigDao
}
