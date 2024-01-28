package com.susu.domain.usecase.envelope

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.EnvelopesRepository
import javax.inject.Inject

class GetEnvelopesHistoryListUseCase @Inject constructor(
    private val envelopesRepository: EnvelopesRepository,
) {
    suspend operator fun invoke(param: Param) = runCatchingIgnoreCancelled {
        with(param) {
            envelopesRepository.getEnvelopesHistoryList(
                friendIds = friendIds,
                ledgerId = ledgerId,
                type = type,
                include = include,
                fromAmount = fromAmount,
                toAmount = toAmount,
                page = page,
                size = size,
                sort = sort,
            )
        }
    }

    data class Param(
        val friendIds: List<Int>? = emptyList(),
        val ledgerId: Int? = null,
        val type: List<String>? = emptyList(),
        val include: List<String>? = emptyList(),
        val fromAmount: Int? = null,
        val toAmount: Int? = null,
        val page: Int? = null,
        val size: Int? = null,
        val sort: String? = null,
    )
}
