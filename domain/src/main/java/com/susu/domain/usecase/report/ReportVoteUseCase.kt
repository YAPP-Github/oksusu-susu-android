package com.susu.domain.usecase.report

import com.susu.core.common.runCatchingIgnoreCancelled
import com.susu.domain.repository.ReportRepository
import javax.inject.Inject

class ReportVoteUseCase @Inject constructor(
    private val reportRepository: ReportRepository,
) {
    suspend operator fun invoke(id: Long) = runCatchingIgnoreCancelled {
        reportRepository.reportVote(id)
    }
}
