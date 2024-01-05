package com.susu.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.susu.core.model.RecentSearch

@Entity(tableName = EntityTable.LEDGER_RECENT_SEARCH)
data class LedgerRecentSearchEntity(
    @PrimaryKey
    val search: String,
    val saveTime: Long,
)

internal fun LedgerRecentSearchEntity.toModel() = RecentSearch(
    search = search,
    saveTime = saveTime,
)

internal fun RecentSearch.toData() = LedgerRecentSearchEntity(
    search = search,
    saveTime = saveTime,
)
