package com.susu.core.ui.argument

import com.susu.core.model.Friend
import kotlinx.serialization.Serializable

@Serializable
data class EnvelopeFilterArgument(
    val selectedFriendList: List<Friend> = emptyList(),
    val fromAmount: Long? = null,
    val toAmount: Long? = null,
)
