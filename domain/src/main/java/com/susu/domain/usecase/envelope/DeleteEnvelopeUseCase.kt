package com.susu.domain.usecase.envelope

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.EnvelopesRepository
import javax.inject.Inject

class DeleteEnvelopeUseCase @Inject constructor(
    private val envelopesRepository: EnvelopesRepository,
) {
    suspend operator fun invoke(id: Long) = runCatchingIgnoreCancelled {
        envelopesRepository.deleteEnvelope(id)
    }
}
