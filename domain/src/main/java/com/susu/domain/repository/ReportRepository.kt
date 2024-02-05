package com.susu.domain.repository

interface ReportRepository {

    suspend fun reportVote(
        targetId: Long,
    )
}
