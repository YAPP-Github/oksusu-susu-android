package com.susu.data.network.service

import com.susu.data.model.TokenResponse
import com.susu.data.model.UserEntity
import com.susu.data.model.response.ValidRegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SignUpService {
    @POST("oauth/{provider}/sign-up")
    suspend fun signUp(
        @Path("provider") provider: String,
        @Query("accessToken") accessToken: String,
        @Body user: UserEntity,
    ): TokenResponse

    @GET("oauth/{provider}/sign-up/valid")
    suspend fun checkValidRegister(
        @Path("provider") provider: String,
        @Query("accessToken") accessToken: String,
    ): ValidRegisterResponse
}
