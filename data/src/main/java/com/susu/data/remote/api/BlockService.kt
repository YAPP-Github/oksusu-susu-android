package com.susu.data.remote.api

import com.susu.data.remote.model.request.BlockUserRequest
import com.susu.data.remote.retrofit.ApiResult
import retrofit2.http.Body
import retrofit2.http.POST

interface BlockService {

    @POST("blocks")
    suspend fun blockUser(
        @Body blockUserRequest: BlockUserRequest,
    ): ApiResult<Unit>
}
