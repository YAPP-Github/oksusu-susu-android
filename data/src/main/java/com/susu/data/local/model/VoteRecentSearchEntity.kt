package com.susu.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = EntityTable.VOTE_RECENT_SEARCH)
data class VoteRecentSearchEntity(
    @PrimaryKey
    val searchKeyword: String,
    val saveTime: Long,
)
