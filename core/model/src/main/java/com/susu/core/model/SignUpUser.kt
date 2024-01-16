package com.susu.core.model

data class SignUpUser(
    val name: String,
    val gender: String,
    val termAgreement: List<Int>,
    val birth: Int,
)
