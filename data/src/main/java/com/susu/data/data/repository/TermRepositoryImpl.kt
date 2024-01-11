package com.susu.data.data.repository

import com.susu.core.model.Term
import com.susu.core.model.TermDetail
import com.susu.data.remote.api.TermService
import com.susu.data.remote.model.response.toModel
import com.susu.domain.repository.TermRepository
import javax.inject.Inject

class TermRepositoryImpl @Inject constructor(
    private val termService: TermService,
) : TermRepository {
    override suspend fun getTerms(): List<Term> = termService.getTerms().getOrThrow().map { it.toModel() }

    override suspend fun getTermDetail(id: Int): TermDetail = termService.getTermDetail(id).getOrThrow().toModel()
}
