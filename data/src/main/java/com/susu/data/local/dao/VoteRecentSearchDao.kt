package com.susu.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.susu.data.local.model.EntityTable
import com.susu.data.local.model.LedgerRecentSearchEntity
import com.susu.data.local.model.VoteRecentSearchEntity

@Dao
interface VoteRecentSearchDao {
    @Query("SELECT * FROM ${EntityTable.VOTE_RECENT_SEARCH} ORDER BY saveTime DESC LIMIT :count")
    fun getSearchList(count: Int): List<VoteRecentSearchEntity>

    @Query("SELECT COUNT(*) FROM ${EntityTable.VOTE_RECENT_SEARCH}")
    fun getSearchListCount(): Int

    @Query("DELETE FROM ${EntityTable.VOTE_RECENT_SEARCH} WHERE searchKeyword = :search")
    fun deleteBySearch(search: String)

    @Insert(onConflict = REPLACE)
    fun upsert(recentSearchEntity: LedgerRecentSearchEntity)

    @Query(
        "DELETE FROM ${EntityTable.VOTE_RECENT_SEARCH} WHERE saveTime IN" +
            "(SELECT saveTime FROM ${EntityTable.VOTE_RECENT_SEARCH} ORDER BY saveTime ASC LIMIT :count)",
    )
    fun deleteOldestSearch(count: Int)
}
