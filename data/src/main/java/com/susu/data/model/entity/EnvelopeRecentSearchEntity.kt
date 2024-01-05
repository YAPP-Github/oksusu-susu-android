package com.susu.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = EntityTable.ENVELOPE_RECENT_SEARCH)
data class EnvelopeRecentSearchEntity(
    @PrimaryKey
    val search: String,
    val saveTime: Long,
)
