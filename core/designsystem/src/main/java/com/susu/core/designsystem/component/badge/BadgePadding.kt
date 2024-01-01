package com.susu.core.designsystem.component.badge

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.susu.core.designsystem.theme.SusuTheme
data class BadgePadding(
    val horizontalPadding: Dp,
    val verticalPadding: Dp,
)

object BadgeStyle {
    val smallBadge: @Composable () -> BadgePadding = {
        BadgePadding(
            horizontalPadding = SusuTheme.spacing.spacing_xxs,
            verticalPadding = SusuTheme.spacing.spacing_xxxxxs,
        )
    }
    val extraSmallBadge: @Composable () -> BadgePadding = {
        BadgePadding(
            horizontalPadding = SusuTheme.spacing.spacing_xxs,
            verticalPadding = SusuTheme.spacing.spacing_xxxxxs,
        )
    }
}
