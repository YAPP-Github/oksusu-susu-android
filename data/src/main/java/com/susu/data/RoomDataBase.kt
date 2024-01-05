package com.susu.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.susu.data.dao.LedgerRecentSearchDao
import com.susu.data.model.entity.EnvelopeRecentSearchEntity
import com.susu.data.model.entity.LedgerRecentSearchEntity

@Database(
    entities = [
        LedgerRecentSearchEntity::class,
        EnvelopeRecentSearchEntity::class,
    ],
    version = 1,
)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun ledgerRecentSearchDao(): LedgerRecentSearchDao
}
