package com.susu.core.designsystem.component.badge

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class BadgePadding(
    val horizontalPadding: Dp = 8.dp,
    val verticalPadding: Dp = 0.dp,
) {
    SmallBadgePadding(
        verticalPadding = 2.dp
    ),
    ExtraSmallBadgePadding,
}
