package com.susu.data.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
data class CreateLedgerConfigResponse(
    val onlyStartAtCategoryIds: List<Int>,
)
