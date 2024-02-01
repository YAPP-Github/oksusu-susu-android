package com.susu.data.data.repository

import com.susu.core.model.Envelope
import com.susu.core.model.EnvelopeStatics
import com.susu.core.model.Relationship
import com.susu.core.model.SearchEnvelope
import com.susu.data.remote.api.BlockService
import com.susu.data.remote.api.EnvelopesService
import com.susu.data.remote.api.ReportService
import com.susu.data.remote.model.request.BlockUserRequest
import com.susu.data.remote.model.request.CategoryRequest
import com.susu.data.remote.model.request.EnvelopeRequest
import com.susu.data.remote.model.request.ReportVoteRequest
import com.susu.data.remote.model.response.toModel
import com.susu.domain.repository.BlockRepository
import com.susu.domain.repository.EnvelopesRepository
import com.susu.domain.repository.ReportRepository
import kotlinx.datetime.LocalDateTime
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
