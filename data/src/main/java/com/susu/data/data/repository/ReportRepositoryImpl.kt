package com.susu.data.data.repository

import com.susu.data.remote.api.ReportService
import com.susu.data.remote.model.request.ReportVoteRequest
import com.susu.domain.repository.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportService: ReportService,
) : ReportRepository {
    override suspend fun reportVote(targetId: Long) = reportService.reportVote(
        ReportVoteRequest(
            targetId = targetId,
        ),
    ).getOrThrow()
}
