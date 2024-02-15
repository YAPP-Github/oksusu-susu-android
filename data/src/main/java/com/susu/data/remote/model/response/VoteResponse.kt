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
    val board: BoardResponse,
    val content: String,
    val isMine: Boolean,
    val isModified: Boolean,
    val createdAt: LocalDateTime = java.time.LocalDateTime.now().toKotlinLocalDateTime(),
    val count: Long = 0,
    @SerialName("options")
    val optionList: List<VoteOption>,
)

@Serializable
data class BoardResponse(
    val id: Long,
    val name: String,
)

internal fun VoteResponse.toModel() = Vote(
    id = id,
    uid = uid,
    boardId = board.id,
    boardName = board.name,
    content = content,
    count = count,
    isModified = isModified,
    isMine = isMine,
    createdAt = createdAt,
    optionList = optionList.sortedBy { it.seq }.map {
        com.susu.core.model.VoteOption(
            id = it.id ?: 0L,
            content = it.content,
        )
    },
)
