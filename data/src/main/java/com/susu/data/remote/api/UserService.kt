package com.susu.data.remote.api

import com.susu.data.remote.model.request.UserPatchRequest
import com.susu.data.remote.model.response.UserResponse
import com.susu.data.remote.retrofit.ApiResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface UserService {
    @GET("users/my-info")
    suspend fun getUserInfo(): ApiResult<UserResponse>

    @PATCH("users/{uid}")
    suspend fun patchUserInfo(@Path("uid") uid: Int, @Body userPatchRequest: UserPatchRequest): ApiResult<UserResponse>
}
