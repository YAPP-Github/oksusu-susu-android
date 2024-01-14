package com.susu.core.model

data class Category(
    val id: Int,
    val seq: Int,
    val category: String,
    val customCategory: String? = null,
)
