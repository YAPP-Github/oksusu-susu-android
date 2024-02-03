package com.susu.data.remote.api

import com.susu.data.remote.model.request.ReportVoteRequest
import com.susu.data.remote.retrofit.ApiResult
import retrofit2.http.Body
import retrofit2.http.POST

interface ReportService {

    @POST("reports")
    suspend fun reportVote(
        @Body reportVoteRequest: ReportVoteRequest,
    ): ApiResult<Unit>
}
