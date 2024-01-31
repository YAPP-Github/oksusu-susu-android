package com.susu.data.data.repository

import com.susu.core.model.FriendSearch
import com.susu.data.remote.api.FriendService
import com.susu.data.remote.model.request.FriendRequest
import com.susu.data.remote.model.response.toModel
import com.susu.domain.repository.FriendRepository
import javax.inject.Inject

class FriendRepositoryImpl @Inject constructor(
    private val friendService: FriendService,
) : FriendRepository {
    override suspend fun createFriend(
        name: String,
        phoneNumber: String?,
        relationshipId: Long,
        customRelation: String?,
    ): Long = friendService.createFriend(
        FriendRequest(
            name = name,
            phoneNumber = phoneNumber,
            relationshipId = relationshipId,
            customRelation = customRelation,
        ),
    ).getOrThrow().id

    override suspend fun searchFriend(name: String): List<FriendSearch> = friendService.searchFriend(
        name = name,
    ).getOrThrow().data.map { it.toModel() }

    override suspend fun editFriend(
        id: Long,
        name: String,
        phoneNumber: String?,
        relationshipId: Long,
        customRelation: String?,
    ) = friendService.editFriend(
        id = id,
        friendRequest = FriendRequest(
            name = name,
            phoneNumber = phoneNumber,
            relationshipId = relationshipId,
            customRelation = customRelation,
        )
    ).getOrThrow()
}
