package com.susu.data.remote.api

import com.susu.data.remote.model.response.EnvelopeConfigResponse
import com.susu.data.remote.model.response.MyStatisticsResponse
import com.susu.data.remote.retrofit.ApiResult
import retrofit2.http.GET

interface StatisticsService {
    @GET("statistics/mine")
    suspend fun getMyStatistics(): ApiResult<MyStatisticsResponse>

    @GET("envelopes/configs/create-envelopes")
    suspend fun getEnvelopeConfig(): ApiResult<EnvelopeConfigResponse> // TODO: 봉투 생성 작업 시 삭제
}
