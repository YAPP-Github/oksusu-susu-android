package com.susu.data.remote.api

import com.susu.data.remote.model.request.FriendRequest
import com.susu.data.remote.model.response.FriendResponse
import com.susu.data.remote.model.response.FriendSearchListResponse
import com.susu.data.remote.retrofit.ApiResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface FriendService {

    @POST("friends")
    suspend fun createFriend(
        @Body friendRequest: FriendRequest,
    ): ApiResult<FriendResponse>

    @GET("friends")
    suspend fun searchFriend(
        @Query("name") name: String,
    ): ApiResult<FriendSearchListResponse>

    @PUT("friends/{id}")
    suspend fun editFriend(
        @Path("id") id: Long,
        @Body friendRequest: FriendRequest,
    ): ApiResult<Unit>
}
