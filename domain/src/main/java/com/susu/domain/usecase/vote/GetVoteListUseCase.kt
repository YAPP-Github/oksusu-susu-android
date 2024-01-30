package com.susu.domain.usecase.vote

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.VoteRepository
import javax.inject.Inject

class GetVoteListUseCase @Inject constructor(
    private val voteRepository: VoteRepository,
) {
    suspend operator fun invoke(param: Param) = runCatchingIgnoreCancelled {
        with(param) {
            voteRepository.getVoteList(
                content = content,
                mine = mine,
                sortType = sortType,
                categoryId = categoryId,
                page = page,
                size = size,
                sort = sort,
            )
        }
    }

    data class Param(
        val content: String? = null,
        val mine: Boolean? = null,
        val sortType: String? = null,
        val categoryId: Int? = null,
        val page: Int? = null,
        val size: Int? = null,
        val sort: String? = null,
    )
}
