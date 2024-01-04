package com.susu.data.network.service

import com.susu.data.model.request.AccessTokenRequest
import com.susu.data.model.response.TokenResponse
import com.susu.data.retrofit.ApiResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @POST("oauth/{provider}/login")
    suspend fun login(
        @Path("provider") provider: String,
        @Body accessTokenRequest: AccessTokenRequest,
    ): ApiResult<TokenResponse>

    @POST("auth/logout")
    suspend fun logout(): ApiResult<Unit>

    @POST("auth/withdraw")
    suspend fun withdraw(): ApiResult<Unit>
}
