package com.susu.data.network.service

import com.susu.data.model.TokenResponse
import com.susu.data.model.request.RefreshTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {
    @POST("auth/token/refresh")
    suspend fun refreshAccessToken(
        @Body refreshTokenRequest: RefreshTokenRequest,
    ): Response<TokenResponse>
}
