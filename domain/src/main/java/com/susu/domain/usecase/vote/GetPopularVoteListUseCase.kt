package com.susu.domain.usecase.vote

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.VoteRepository
import javax.inject.Inject

class GetPopularVoteListUseCase @Inject constructor(
    private val voteRepository: VoteRepository,
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        voteRepository.getPopularVoteList()
    }

}
