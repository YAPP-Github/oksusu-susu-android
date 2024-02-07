package com.susu.data.remote.api

import com.susu.data.remote.model.request.EnvelopeRequest
import com.susu.data.remote.model.response.EnvelopeDetailResponse
import com.susu.data.remote.model.response.EnvelopeFilterConfigResponse
import com.susu.data.remote.model.response.EnvelopeResponse
import com.susu.data.remote.model.response.EnvelopesHistoryListResponse
import com.susu.data.remote.model.response.EnvelopesListResponse
import com.susu.data.remote.model.response.RelationShipListResponse
import com.susu.data.remote.model.response.SearchEnvelopeResponse
import com.susu.data.remote.retrofit.ApiResult
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EnvelopesService {
    @GET("envelopes/friend-statistics")
    suspend fun getEnvelopesList(
        @Query("friendIds") friendIds: List<Long>?,
        @Query("fromTotalAmounts") fromTotalAmounts: Long?,
        @Query("toTotalAmounts") toTotalAmounts: Long?,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("sort") sort: String?,
    ): ApiResult<EnvelopesListResponse>

    @GET("envelopes/configs/create-envelopes")
    suspend fun getRelationShipConfigList(): ApiResult<RelationShipListResponse>

    @POST("envelopes")
    suspend fun createEnvelope(
        @Body envelopeRequest: EnvelopeRequest,
    ): ApiResult<EnvelopeResponse>

    @GET("envelopes")
    suspend fun getEnvelopesHistoryList(
        @Query("friendIds") friendIds: List<Long>?,
        @Query("ledgerId") ledgerId: Int?,
        @Query("types") types: List<String>?,
        @Query("include") include: List<String>?,
        @Query("fromAmount") fromAmount: Int?,
        @Query("toAmount") toAmount: Int?,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("sort") sort: String?,
    ): ApiResult<EnvelopesHistoryListResponse>

    @GET("envelopes/{id}")
    suspend fun getEnvelopeDetail(
        @Path("id") id: Long,
    ): ApiResult<EnvelopeDetailResponse>

    @PATCH("envelopes/{id}")
    suspend fun editEnvelope(
        @Path("id") id: Long,
        @Body envelopeRequest: EnvelopeRequest,
    ): ApiResult<EnvelopeResponse>

    @GET("envelopes/{id}")
    suspend fun getEnvelope(
        @Path("id") id: Long,
    ): ApiResult<EnvelopeResponse>

    @DELETE("envelopes/{id}")
    suspend fun deleteEnvelope(
        @Path("id") id: Long,
    ): ApiResult<Unit>

    @GET("envelopes")
    suspend fun searchEnvelope(
        @Query("friendIds") friendIds: List<Int>?,
        @Query("ledgerId") ledgerId: Long?,
        @Query("type") types: String?,
        @Query("include") include: String = "CATEGORY,FRIEND,RELATIONSHIP,FRIEND_RELATIONSHIP",
        @Query("fromAmount") fromAmount: Long?,
        @Query("toAmount") toAmount: Long?,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("sort") sort: String?,
    ): ApiResult<SearchEnvelopeResponse>

    @GET("envelopes/configs/search-filter")
    suspend fun getEnvelopeFilterConfig(): ApiResult<EnvelopeFilterConfigResponse>
}
