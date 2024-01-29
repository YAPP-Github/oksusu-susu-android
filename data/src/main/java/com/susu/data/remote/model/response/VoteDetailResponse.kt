package com.susu.data.remote.model.response

import com.susu.core.model.Profile
import com.susu.core.model.Vote
import com.susu.data.remote.model.request.VoteOption
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class VoteDetailResponse(
    val id: Long,
    val isMine: Boolean,
    val boardName: String,
    val content: String,
    val count: Int,
    val createdAt: LocalDateTime,
    val creatorProfile: UserProfile,
    val isModified: Boolean,
    val options: List<VoteOption>,
)

@Serializable
data class UserProfile(
    val id: Long,
    val name: String,
)

internal fun VoteDetailResponse.toModel() = Vote(
    id = id,
    uid = creatorProfile.id,
    profile = creatorProfile.toModel(),
    boardName = boardName,
    content = content,
    count = count,
    isModified = isModified,
    isMine = isMine,
    createdAt = createdAt,
    optionList = options.map {
        com.susu.core.model.VoteOption(
            id = it.id!!,
            content = it.content,
            count = it.count!!,
            isVoted = it.isVoted,
        )
    },
)

internal fun UserProfile.toModel() = Profile(
    id = id,
    name = name,
)
