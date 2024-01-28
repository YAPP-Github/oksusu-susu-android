package com.susu.domain.usecase.envelope

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.EnvelopesRepository
import com.susu.domain.repository.FriendRepository
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

class CreateReceivedEnvelopeUseCase @Inject constructor(
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
                type = "RECEIVED",
                friendId = friendId,
                ledgerId = ledgerId,
                amount = amount,
                gift = gift,
                memo = memo,
                hasVisited = hasVisited,
                handedOverAt = handedOverAt,
            )
        }
    }

    data class Param(
        val friendId: Long? = null,
        val friendName: String? = null,
        val phoneNumber: String? = null,
        val relationshipId: Int? = null,
        val customRelation: String? = null,
        val ledgerId: Long,
        val amount: Long,
        val gift: String? = null,
        val memo: String? = null,
        val handedOverAt: LocalDateTime,
        val hasVisited: Boolean? = null,
    )
}
