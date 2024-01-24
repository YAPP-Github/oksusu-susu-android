package com.susu.core.model

data class Vote(
    val id: Long,
    val category: String,
    val content: String,
    val isModified: Boolean,
    val optionList: List<String>
)
