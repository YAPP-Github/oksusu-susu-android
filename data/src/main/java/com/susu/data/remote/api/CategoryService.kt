package com.susu.data.remote.api

import com.susu.data.remote.model.response.CategoryConfigResponse
import com.susu.data.remote.model.response.CategoryInfo
import com.susu.data.remote.retrofit.ApiResult
import retrofit2.http.GET

interface CategoryService {
    @GET("categories")
    suspend fun getCategoryConfig(): ApiResult<List<CategoryConfigResponse>>
}
