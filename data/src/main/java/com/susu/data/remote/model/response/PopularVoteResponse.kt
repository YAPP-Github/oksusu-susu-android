package com.susu.data.remote.model.response

import com.susu.core.model.Vote
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class PopularVoteResponse(
    val id: Long,
    val category: String,
    val content: String,
    val count: Int,
    val isModified: Boolean,
)

internal fun PopularVoteResponse.toModel() = Vote(
    id = id,
    uid = 0,
    category = category,
    content = content,
    isModified = isModified,
    count = count,
    createdAt = LocalDateTime.now().toKotlinLocalDateTime(),
    optionList = listOf(),
)
