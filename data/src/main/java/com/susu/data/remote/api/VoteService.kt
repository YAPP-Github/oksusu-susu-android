package com.susu.data.remote.api

import com.susu.data.remote.model.request.CreateVoteRequest
import com.susu.data.remote.model.response.VoteResponse
import com.susu.data.remote.retrofit.ApiResult
import retrofit2.http.Body
import retrofit2.http.POST

interface VoteService {

    @POST("/api/v1/votes")
    suspend fun createVote(
        @Body createVoteRequest: CreateVoteRequest
    ): ApiResult<VoteResponse>
}
