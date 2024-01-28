package com.susu.data.remote.api

import com.susu.data.remote.model.request.CreateVoteRequest
import com.susu.data.remote.model.response.PopularVoteResponse
import com.susu.data.remote.model.response.PostCategoryConfig
import com.susu.data.remote.model.response.VoteListResponse
import com.susu.data.remote.model.response.VoteResponse
import com.susu.data.remote.retrofit.ApiResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface VoteService {

    @POST("votes")
    suspend fun createVote(
        @Body createVoteRequest: CreateVoteRequest,
    ): ApiResult<VoteResponse>

    @GET("votes")
    suspend fun getVoteList(
        @Query("content") content: String?,
        @Query("mine") mine: Boolean?,
        @Query("sortType") sortType: String?,
        @Query("boardId") boardId: Int?,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("sort") sort: String?,
    ): ApiResult<VoteListResponse>

    @GET("votes/popular")
    suspend fun getPopularVoteList(): ApiResult<List<PopularVoteResponse>>

    @GET("posts/configs/create-post")
    suspend fun getPostCategoryConfig(): ApiResult<List<PostCategoryConfig>>
}
