package com.susu.domain.usecase.vote

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.VoteRepository
import javax.inject.Inject

class EditVoteUseCase @Inject constructor(
    private val voteRepository: VoteRepository,
) {
    suspend operator fun invoke(param: Param) = runCatchingIgnoreCancelled {
        with(param) {
            voteRepository.editVote(
                id = id,
                boardId = boardId,
                content = content,
            )
        }
    }

    data class Param(
        val id: Long,
        val boardId: Long,
        val content: String,
    )
}
