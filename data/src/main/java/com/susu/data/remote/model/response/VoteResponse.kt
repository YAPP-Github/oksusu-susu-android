package com.susu.data.remote.model.response

import com.susu.core.model.Vote
import com.susu.data.remote.model.request.VoteOption
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VoteResponse(
    val id: Long,
    val uid: Long = 0,
    val category: String,
    val content: String,
    val isModified: Boolean,
    val createdAt: LocalDateTime = java.time.LocalDateTime.now().toKotlinLocalDateTime(),
    @SerialName("options")
    val optionList: List<VoteOption>,
)

internal fun VoteResponse.toModel() = Vote(
    id = id,
    uid = uid,
    category = category,
    content = content,
    isModified = isModified,
    createdAt = createdAt,
    optionList = optionList.sortedBy { it.seq }.map {
        com.susu.core.model.VoteOption(
            id = it.id ?: 0L,
            content = it.content,
        )
    },
)
