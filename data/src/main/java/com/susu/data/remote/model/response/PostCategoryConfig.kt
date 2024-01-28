package com.susu.data.remote.model.response

import com.susu.core.model.Category
import kotlinx.serialization.Serializable

@Serializable
data class PostCategoryConfig(
    val id: Int,
    val name: String,
)

internal fun PostCategoryConfig.toModel() = Category(
    id = id,
    name = name,
)
