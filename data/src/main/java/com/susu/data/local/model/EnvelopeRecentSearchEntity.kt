package com.susu.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = EntityTable.ENVELOPE_RECENT_SEARCH)
data class EnvelopeRecentSearchEntity(
    @PrimaryKey
    val searchKeyword: String,
    val saveTime: Long,
)
