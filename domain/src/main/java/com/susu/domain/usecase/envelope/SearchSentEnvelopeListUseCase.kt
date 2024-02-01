package com.susu.domain.usecase.envelope

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.EnvelopesRepository
import javax.inject.Inject

class SearchSentEnvelopeListUseCase @Inject constructor(
    private val envelopesRepository: EnvelopesRepository,
) {
    suspend operator fun invoke(param: Param) = runCatchingIgnoreCancelled {
        with(param) {
            envelopesRepository.searchEnvelope(
                friendIds = friendIds,
                types = "SENT",
                fromAmount = fromAmount,
                toAmount = toAmount,
                page = page,
                sort = sort,
                ledgerId = null,
            )
        }
    }

    data class Param(
        val friendIds: List<Int>? = null,
        val fromAmount: Long? = null,
        val toAmount: Long? = null,
        val page: Int? = null,
        val sort: String? = null,
    )
}
