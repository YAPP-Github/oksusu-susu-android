package com.susu.data.remote.api

import com.susu.data.remote.model.response.MyStatisticsResponse
import com.susu.data.remote.model.response.SusuStatisticsResponse
import com.susu.data.remote.retrofit.ApiResult
import retrofit2.http.GET
import retrofit2.http.Query

interface StatisticsService {
    @GET("statistics/mine")
    suspend fun getMyStatistics(): ApiResult<MyStatisticsResponse>

    @GET("statistics/susu")
    suspend fun getSusuStatistics(
        @Query("age") age: String,
        @Query("relationshipId") relationshipId: Int,
        @Query("categoryId") categoryId: Int,
    ): ApiResult<SusuStatisticsResponse>
}
