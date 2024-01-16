package com.susu.data.remote.api

import com.susu.data.remote.model.request.AccessTokenRequest
import com.susu.data.remote.model.response.TokenResponse
import com.susu.data.remote.retrofit.ApiResult
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {
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
