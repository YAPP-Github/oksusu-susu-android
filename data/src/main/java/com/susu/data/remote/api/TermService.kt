package com.susu.data.remote.api

import com.susu.data.remote.model.response.TermDetailResponse
import com.susu.data.remote.model.response.TermResponse
import com.susu.data.remote.retrofit.ApiResult
import retrofit2.http.GET
import retrofit2.http.Path

interface TermService {
    @GET("terms")
    suspend fun getTerms(): ApiResult<List<TermResponse>>

    @GET("terms/{id}")
    suspend fun getTermDetail(@Path("id") id: Int): ApiResult<TermDetailResponse>
}
