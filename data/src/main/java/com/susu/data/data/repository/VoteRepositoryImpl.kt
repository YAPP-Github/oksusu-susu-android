package com.susu.data.data.repository

import com.susu.core.model.Vote
import com.susu.data.local.model.toModel
import com.susu.data.remote.api.VoteService
import com.susu.data.remote.model.request.CreateVoteRequest
import com.susu.data.remote.model.request.VoteOption
import com.susu.data.remote.model.response.toModel
import com.susu.domain.repository.VoteRepository
import javax.inject.Inject

class VoteRepositoryImpl @Inject constructor(
    private val api: VoteService,
) : VoteRepository {
    override suspend fun createVote(
        content: String,
        optionList: List<String>,
        categoryId: Int,
    ): Vote = api.createVote(
        createVoteRequest = CreateVoteRequest(
            content = content,
            optionList = optionList.mapIndexed { index, voteContent ->
                VoteOption(
                    content = voteContent,
                    seq = index + 1,
                )
            },
            categoryId = categoryId,
        ),
    ).getOrThrow().toModel()

    override suspend fun getVoteList(
        content: String?,
        mine: Boolean?,
        sortType: String?,
        categoryId: Int?,
        page: Int?,
        size: Int?,
        sort: String?,
    ): List<Vote> = api.getVoteList(
        content = content,
        mine = mine,
        sortType = sortType,
        categoryId = categoryId,
        page = page,
        size = size,
        sort = sort,
    ).getOrThrow().toModel()

    override suspend fun getPopularVoteList(): List<Vote> = api.getPopularVoteList().getOrThrow().map { it.toModel() }
}
