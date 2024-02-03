package com.susu.domain.repository

import com.susu.core.model.Category
import com.susu.core.model.Vote

interface VoteRepository {
    suspend fun createVote(
        content: String,
        optionList: List<String>,
        categoryId: Int,
    ): Vote

    suspend fun getVoteList(
        content: String?,
        mine: Boolean?,
        sortType: String?,
        categoryId: Int?,
        page: Int?,
        size: Int?,
        sort: String?,
    ): List<Vote>

    suspend fun getPopularVoteList(): List<Vote>

    suspend fun getPostCategoryConfig(): List<Category>

    suspend fun getVoteDetail(
        id: Long,
    ): Vote

    suspend fun vote(
        id: Long,
        isCancel: Boolean,
        optionId: Long,
    )

    suspend fun editVote(
        id: Long,
        boardId: Long,
        content: String,
    ): Vote

    suspend fun deleteVote(
        id: Long,
    )
}
