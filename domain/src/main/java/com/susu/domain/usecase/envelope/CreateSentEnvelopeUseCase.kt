package com.susu.domain.usecase.envelope

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.core.model.Category
import com.susu.domain.repository.EnvelopesRepository
import com.susu.domain.repository.FriendRepository
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

class CreateSentEnvelopeUseCase @Inject constructor(
    private val friendRepository: FriendRepository,
    private val envelopesRepository: EnvelopesRepository,
) {
    suspend operator fun invoke(param: Param) = runCatchingIgnoreCancelled {
        with(param) {
            val friendId = friendId ?: friendRepository.createFriend(
                name = friendName!!,
                phoneNumber = phoneNumber,
                relationshipId = relationshipId!!,
                customRelation = customRelation,
            )

            envelopesRepository.createEnvelope(
                type = "SENT",
                friendId = friendId,
                amount = amount,
                gift = gift,
                memo = memo,
                hasVisited = hasVisited,
                handedOverAt = handedOverAt,
                categoryId = category.id.toLong(),
                customCategory = category.customCategory
            )
        }
    }

    data class Param(
        val friendId: Long? = null,
        val friendName: String? = null,
        val phoneNumber: String? = null,
        val relationshipId: Long? = null,
        val customRelation: String? = null,
        val amount: Long,
        val gift: String? = null,
        val memo: String? = null,
        val handedOverAt: LocalDateTime,
        val hasVisited: Boolean? = null,
        val category: Category,
    )
}
