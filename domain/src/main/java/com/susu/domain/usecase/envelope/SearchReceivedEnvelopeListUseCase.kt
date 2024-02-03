package com.susu.domain.usecase.envelope

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.EnvelopesRepository
import javax.inject.Inject

class SearchReceivedEnvelopeListUseCase @Inject constructor(
    private val envelopesRepository: EnvelopesRepository,
) {
    suspend operator fun invoke(param: Param) = runCatchingIgnoreCancelled {
        with(param) {
            envelopesRepository.searchEnvelope(
                friendIds = friendIds,
                ledgerId = ledgerId,
                types = "RECEIVED",
                fromAmount = fromAmount,
                toAmount = toAmount,
                page = page,
                sort = sort,
            )
        }
    }

    data class Param(
        val friendIds: List<Int>?,
        val ledgerId: Long,
        val fromAmount: Long?,
        val toAmount: Long?,
        val page: Int?,
        val sort: String?,
    )
}
