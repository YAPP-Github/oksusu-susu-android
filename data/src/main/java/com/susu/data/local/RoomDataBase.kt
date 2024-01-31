package com.susu.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.susu.data.local.dao.EnvelopeRecentSearchDao
import com.susu.data.local.dao.LedgerRecentSearchDao
import com.susu.data.local.dao.VoteRecentSearchDao
import com.susu.data.local.model.EnvelopeRecentSearchEntity
import com.susu.data.local.model.LedgerRecentSearchEntity
import com.susu.data.local.model.VoteRecentSearchEntity

@Database(
    entities = [
        LedgerRecentSearchEntity::class,
        EnvelopeRecentSearchEntity::class,
        VoteRecentSearchEntity::class,
    ],
    version = 1,
)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun ledgerRecentSearchDao(): LedgerRecentSearchDao
    abstract fun envelopeRecentSearchDao(): EnvelopeRecentSearchDao
    abstract fun voteRecentSearchDao(): VoteRecentSearchDao
}
