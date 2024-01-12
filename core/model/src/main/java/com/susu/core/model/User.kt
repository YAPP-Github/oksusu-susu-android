package com.susu.core.model

data class User(
    val name: String,
    val gender: String,
    val termAgreement: List<Int>,
    val birth: Int,
)
