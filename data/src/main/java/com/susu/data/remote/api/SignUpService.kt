package com.susu.data.remote.api

import com.susu.data.remote.model.request.UserSignUpRequest
import com.susu.data.remote.model.response.TokenResponse
import com.susu.data.remote.model.response.ValidRegisterResponse
import com.susu.data.remote.retrofit.ApiResult
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
        @Body user: UserSignUpRequest,
    ): ApiResult<TokenResponse>

    @GET("oauth/{provider}/sign-up/valid")
    suspend fun checkValidRegister(
        @Path("provider") provider: String,
        @Query("accessToken") accessToken: String,
    ): ApiResult<ValidRegisterResponse>
}
