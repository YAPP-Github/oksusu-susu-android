package com.susu.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = EntityTable.LEDGER_RECENT_SEARCH)
data class LedgerRecentSearchEntity(
    @PrimaryKey
    val search: String,
    val saveTime: Long,
)
