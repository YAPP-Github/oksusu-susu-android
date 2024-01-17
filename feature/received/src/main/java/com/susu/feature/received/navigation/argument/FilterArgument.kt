package com.susu.feature.received.navigation.argument

import com.susu.core.model.Category
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class FilterArgument(
    val selectedCategoryList: List<Category> = emptyList(),
    val startAt: LocalDateTime? = null,
    val endAt: LocalDateTime ? = null,
)
