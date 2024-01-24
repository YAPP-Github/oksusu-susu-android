package com.susu.domain.repository

import com.susu.core.model.Envelope
import com.susu.core.model.FriendSearch
import com.susu.core.model.Relationship

interface FriendRepository {
    suspend fun createFriend(
        name: String,
        phoneNumber: String? = null,
        relationshipId: Int,
        customRelation: String? = null,
    ): Int

    suspend fun searchFriend(
        name: String,
    ): FriendSearch
}
