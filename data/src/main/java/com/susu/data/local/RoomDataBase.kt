package com.susu.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.susu.data.local.dao.LedgerRecentSearchDao
import com.susu.data.local.model.EnvelopeRecentSearchEntity
import com.susu.data.local.model.LedgerRecentSearchEntity

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
