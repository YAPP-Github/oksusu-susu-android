package com.susu.data.remote.api

import com.susu.data.remote.model.response.MyStatisticsResponse
import com.susu.data.remote.retrofit.ApiResult
import retrofit2.http.GET

interface StatisticsService {
    @GET("statistics/mine")
    suspend fun getMyStatistics(): ApiResult<MyStatisticsResponse>
}
