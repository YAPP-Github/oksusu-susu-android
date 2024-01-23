package com.susu.data.remote.api

import com.susu.data.remote.model.request.FriendRequest
import com.susu.data.remote.model.response.FriendResponse
import com.susu.data.remote.retrofit.ApiResult
import retrofit2.http.Body
import retrofit2.http.POST

interface FriendService {

    @POST("friends")
    suspend fun createFriend(
        @Body friendRequest: FriendRequest
    ): ApiResult<FriendResponse>
}
