package com.susu.data.remote.api

import com.susu.data.remote.model.request.RefreshTokenRequest
import com.susu.data.remote.model.response.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {
    @POST("auth/token/refresh")
    suspend fun refreshAccessToken(
        @Body refreshTokenRequest: RefreshTokenRequest,
    ): Response<TokenResponse>
}
