package com.susu.domain.usecase.friend

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.CategoryConfigRepository
import com.susu.domain.repository.FriendRepository
import javax.inject.Inject

class CreateFriendUseCase @Inject constructor(
    private val friendRepository: FriendRepository,
) {
    suspend operator fun invoke(param: Param) = runCatchingIgnoreCancelled {
        with(param) {
            friendRepository.createFriend(
                name = name,
                phoneNumber = phoneNumber,
                relationshipId = relationshipId,
                customRelation = customRelation,
            )
        }
    }

    data class Param(
        val name: String = "",
        val phoneNumber: String? = null,
        val relationshipId: Int = 0,
        val customRelation: String? = null,
    )
}
