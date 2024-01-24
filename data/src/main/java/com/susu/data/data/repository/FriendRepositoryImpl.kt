package com.susu.data.data.repository

import com.susu.core.model.Envelope
import com.susu.core.model.FriendSearch
import com.susu.core.model.Relationship
import com.susu.data.remote.api.EnvelopesService
import com.susu.data.remote.api.FriendService
import com.susu.data.remote.model.request.FriendRequest
import com.susu.data.remote.model.response.toModel
import com.susu.domain.repository.EnvelopesRepository
import com.susu.domain.repository.FriendRepository
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val friendService: FriendService,
) : FriendRepository {
    override suspend fun createFriend(
        name: String,
        phoneNumber: String?,
        relationshipId: Int,
        customRelation: String?,
    ): Int = friendService.createFriend(
        FriendRequest(
            name = name,
            phoneNumber = phoneNumber,
            relationshipId = relationshipId,
            customRelation = customRelation,
        ),
    ).getOrThrow().id

    override suspend fun searchFriend(name: String): FriendSearch = friendService.searchFriend(
        name = name,
    ).getOrThrow().toModel()
}
