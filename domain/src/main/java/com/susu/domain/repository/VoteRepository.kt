package com.susu.domain.repository

import com.susu.core.model.Vote

interface VoteRepository {
    suspend fun createVote(
         content: String,
         optionList: List<String>,
         categoryId: Int
    ): Vote
}
