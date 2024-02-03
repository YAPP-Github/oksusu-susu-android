package com.susu.domain.usecase.vote

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.VoteRepository
import javax.inject.Inject

class VoteUseCase @Inject constructor(
    private val voteRepository: VoteRepository,
) {
    suspend operator fun invoke(param: Param) = runCatchingIgnoreCancelled {
        with(param) {
            voteRepository.vote(
                id = id,
                isCancel = isCancel,
                optionId = optionId,
            )
        }
    }

    data class Param(
        val id: Long,
        val isCancel: Boolean,
        val optionId: Long,
    )
}
