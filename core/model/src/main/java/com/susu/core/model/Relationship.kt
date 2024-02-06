package com.susu.core.model

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Stable
@Serializable
data class Relationship(
    val id: Long = -1,
    val relation: String = "",
    val customRelation: String? = null,
    val description: String? = null,
)
