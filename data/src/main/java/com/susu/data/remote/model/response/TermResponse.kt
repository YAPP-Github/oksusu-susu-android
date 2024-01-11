package com.susu.data.remote.model.response

import com.susu.core.model.Term
import com.susu.core.model.TermDetail
import kotlinx.serialization.Serializable

@Serializable
data class TermResponse(
    val id: Int,
    val title: String,
    val isEssential: Boolean,
)

@Serializable
data class TermDetailResponse(
    val id: Int,
    val title: String,
    val description: String,
    val isEssential: Boolean,
)

fun TermResponse.toModel(): Term = Term(
    id = id,
    title = title,
    isEssential = isEssential,
)

fun TermDetailResponse.toModel(): TermDetail = TermDetail(
    id = id,
    title = title,
    description = description,
    isEssential = isEssential,
)
