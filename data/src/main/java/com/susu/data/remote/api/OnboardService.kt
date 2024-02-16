package com.susu.data.remote.api

import com.susu.data.remote.model.response.OnboardVoteResponse
import com.susu.data.remote.retrofit.ApiResult
import retrofit2.http.GET

interface OnboardService {
    @GET("votes/onboarding")
    suspend fun getOnboardVote(): ApiResult<OnboardVoteResponse>
}
