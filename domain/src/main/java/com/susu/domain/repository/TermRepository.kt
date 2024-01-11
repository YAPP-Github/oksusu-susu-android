package com.susu.domain.repository

import com.susu.core.model.Term
import com.susu.core.model.TermDetail

interface TermRepository {
    suspend fun getTerms(): List<Term>
    suspend fun getTermDetail(id: Int): TermDetail
}
