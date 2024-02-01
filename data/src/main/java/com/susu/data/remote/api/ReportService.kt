package com.susu.data.remote.api

import com.susu.data.remote.model.request.CreateVoteRequest
import com.susu.data.remote.model.request.EditVoteRequest
import com.susu.data.remote.model.request.ReportVoteRequest
import com.susu.data.remote.model.request.VoteRequest
import com.susu.data.remote.model.response.PopularVoteResponse
import com.susu.data.remote.model.response.PostCategoryConfig
import com.susu.data.remote.model.response.VoteDetailResponse
import com.susu.data.remote.model.response.VoteListResponse
import com.susu.data.remote.model.response.VoteResponse
import com.susu.data.remote.retrofit.ApiResult
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ReportService {

    @POST("reports")
    suspend fun reportVote(
        @Body reportVoteRequest: ReportVoteRequest,
    ): ApiResult<Unit>
}
