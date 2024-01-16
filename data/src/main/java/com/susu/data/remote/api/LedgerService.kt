package com.susu.data.remote.api

import com.susu.data.remote.model.request.LedgerRequest
import com.susu.data.remote.model.response.LedgerListResponse
import com.susu.data.remote.model.response.LedgerResponse
import com.susu.data.remote.retrofit.ApiResult
import kotlinx.datetime.LocalDateTime
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface LedgerService {
    @GET("ledgers")
    suspend fun getLedgerList(
        @Query("title") title: String?,
        @Query("categoryId") categoryId: Long?,
        @Query("fromStartAt") fromStartAt: LocalDateTime,
        @Query("toEndAt") toEndAt: LocalDateTime,
        @Query("page") page: Int?,
        @Query("sort") sort: String?,
    ): ApiResult<LedgerListResponse>

    @PATCH("ledgers/{id}")
    suspend fun editLedger(
        @Path("id") id: Int,
        @Body ledgerRequest: LedgerRequest,
    ): ApiResult<LedgerResponse>

    @DELETE("ledgers")
    suspend fun deleteLedgerList(@Query("ids") idList: List<Int>): ApiResult<Unit>
}
