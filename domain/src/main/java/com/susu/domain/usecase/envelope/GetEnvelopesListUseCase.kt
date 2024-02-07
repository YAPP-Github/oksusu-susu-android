package com.susu.domain.usecase.envelope

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.EnvelopesRepository
import javax.inject.Inject

class GetEnvelopesListUseCase @Inject constructor(
    private val envelopesRepository: EnvelopesRepository,
) {
    suspend operator fun invoke(param: Param) = runCatchingIgnoreCancelled {
        with(param) {
            envelopesRepository.getEnvelopesList(
                friendIds = friendIds,
                fromTotalAmounts = fromTotalAmounts,
                toTotalAmounts = toTotalAmounts,
                page = page,
                size = size,
                sort = sort,
            )
        }
    }

    data class Param(
        val friendIds: List<Long>? = emptyList(),
        val fromTotalAmounts: Long? = null,
        val toTotalAmounts: Long? = null,
        val page: Int? = null,
        val size: Int? = null,
        val sort: String? = null,
    )
}
