package com.susu.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.susu.data.model.entity.EntityTable
import com.susu.data.model.entity.LedgerRecentSearchEntity

@Dao
interface LedgerRecentSearchDao {
    @Query("SELECT * FROM ${EntityTable.LEDGER_RECENT_SEARCH} ORDER BY saveTime DESC LIMIT :count")
    fun getSearchList(count: Int): List<LedgerRecentSearchEntity>

    @Query("SELECT COUNT(*) FROM ${EntityTable.LEDGER_RECENT_SEARCH}")
    fun getSearchesCount(): Int

    @Query("DELETE FROM ${EntityTable.LEDGER_RECENT_SEARCH} WHERE search = :search")
    fun deleteBySearch(search: String): Int

    @Insert(onConflict = REPLACE)
    fun upsert(recentSearchEntity: LedgerRecentSearchEntity)

    @Query(
        "DELETE FROM ${EntityTable.LEDGER_RECENT_SEARCH} WHERE saveTime IN" +
            "(SELECT saveTime FROM ${EntityTable.LEDGER_RECENT_SEARCH} ORDER BY saveTime ASC LIMIT :count)",
    )
    fun deleteOldestSearch(count: Int): Int
}
