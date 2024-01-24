package com.susu.data.remote.model.response

import com.susu.core.model.Vote
import com.susu.data.remote.model.request.VoteOption
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VoteResponse(
    val id: Long,
    val category: String,
    val content: String,
    val isModified: Boolean,
    @SerialName("options")
    val optionList: List<VoteOption>
)

internal fun VoteResponse.toModel() = Vote(
    id = id,
    category = category,
    content = content,
    isModified = isModified,
    optionList = optionList.sortedBy { it.seq }.map { it.content },
)
