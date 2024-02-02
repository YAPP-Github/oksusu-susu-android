package com.susu.domain.usecase.envelope

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.EnvelopesRepository
import javax.inject.Inject

class GetEnvelopeFilterConfigUseCase @Inject constructor(
    private val envelopesRepository: EnvelopesRepository,
) {
    suspend operator fun invoke() = runCatchingIgnoreCancelled {
        envelopesRepository.getEnvelopeFilterConfig()
    }
}
