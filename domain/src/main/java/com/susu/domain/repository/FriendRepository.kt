package com.susu.domain.repository

import com.susu.core.model.FriendSearch

interface FriendRepository {
    suspend fun createFriend(
        name: String,
        phoneNumber: String? = null,
        relationshipId: Long,
        customRelation: String? = null,
    ): Long

    suspend fun searchFriend(
        name: String,
    ): List<FriendSearch>

    suspend fun editFriend(
        id: Long,
        name: String,
        phoneNumber: String? = null,
        relationshipId: Long,
        customRelation: String? = null,
    )
}
