package com.susu.domain.usecase.vote

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.CategoryConfigRepository
import com.susu.domain.repository.VoteRepository
import javax.inject.Inject

class CreateVoteUseCase @Inject constructor(
    private val voteRepository: VoteRepository,
) {
    suspend operator fun invoke(param: Param) = runCatchingIgnoreCancelled {
        with(param) {
            voteRepository.createVote(
                content = content,
                optionList = optionList,
                categoryId = categoryId,
            )
        }
    }

    data class Param(
        val content: String,
        val optionList: List<String>,
        val categoryId: Int,
    )
}
