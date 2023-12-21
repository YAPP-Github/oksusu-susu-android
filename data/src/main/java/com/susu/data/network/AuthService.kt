package com.susu.data.network

import com.susu.data.model.TokenEntity
import com.susu.data.model.UserEntity
import com.susu.data.model.request.AccessTokenRequest
import com.susu.data.model.request.RefreshTokenRequest
import com.susu.data.model.response.ValidRegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {
    @POST("oauth/{provider}/login")
    suspend fun login(
        @Path("provider") provider: String,
        @Body accessTokenRequest: AccessTokenRequest,
    ): TokenEntity

    @POST("oauth/{provider}/sign-up")
    suspend fun signUp(
        @Path("provider") provider: String,
        @Body user: UserEntity,
    ): TokenEntity

    @GET("oauth/{provider}/sign-up/valid")
    suspend fun checkValidRegister(
        @Path("provider") provider: String,
        @Body accessTokenRequest: AccessTokenRequest,
    ): ValidRegisterResponse

    @POST("auth/logout")
    suspend fun logout()

    @POST("auth/token/refresh")
    suspend fun refreshAccessToken(
        @Body refreshTokenRequest: RefreshTokenRequest,
    ): TokenEntity
}
