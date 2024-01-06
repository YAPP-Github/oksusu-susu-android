package com.susu.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = EntityTable.LEDGER_RECENT_SEARCH)
data class LedgerRecentSearchEntity(
    @PrimaryKey
    val searchKeyword: String,
    val saveTime: Long,
)
