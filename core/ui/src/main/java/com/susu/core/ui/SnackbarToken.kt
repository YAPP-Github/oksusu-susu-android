package com.susu.core.ui

import androidx.annotation.DrawableRes

data class SnackbarToken(
    val message: String = "",
    @DrawableRes val actionIcon: Int? = null,
    val actionIconContentDescription: String? = null,
    val actionButtonText: String? = null,
    val onClickActionButton: () -> Unit = {},
)
