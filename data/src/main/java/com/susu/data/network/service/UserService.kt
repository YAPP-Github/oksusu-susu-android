package com.susu.data.network.service

import com.susu.data.model.response.TokenResponse
import com.susu.data.model.request.AccessTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @POST("oauth/{provider}/login")
    suspend fun login(
        @Path("provider") provider: String,
        @Body accessTokenRequest: AccessTokenRequest,
    ): TokenResponse

    @POST("auth/logout")
    suspend fun logout(): Response<Unit?>

    @POST("auth/withdraw")
    suspend fun withdraw(): Response<Unit?>
}
