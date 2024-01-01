package com.susu.core.designsystem.component.textfieldbutton.style

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.SusuTheme

data class InnerButtonStyle(
    val paddingValues: PaddingValues = PaddingValues(0.dp),
    val textStyle: TextStyle = TextStyle.Default,
)

object XSmallInnerButtonStyle {
    val height28: @Composable () -> InnerButtonStyle = {
        InnerButtonStyle(
            paddingValues = PaddingValues(
                horizontal = SusuTheme.spacing.spacing_xxs,
                vertical = SusuTheme.spacing.spacing_xxxxs,
            ),
            textStyle = SusuTheme.typography.title_xxxs,
        )
    }

    val height24: @Composable () -> InnerButtonStyle = {
        InnerButtonStyle(
            paddingValues = PaddingValues(
                horizontal = SusuTheme.spacing.spacing_xxs,
                vertical = SusuTheme.spacing.spacing_xxxxxs,
            ),
            textStyle = SusuTheme.typography.title_xxxs,
        )
    }

    val height20: @Composable () -> InnerButtonStyle = {
        InnerButtonStyle(
            paddingValues = PaddingValues(
                horizontal = SusuTheme.spacing.spacing_xxs,
                vertical = SusuTheme.spacing.spacing_xxxxxs,
            ),
            textStyle = SusuTheme.typography.title_xxxxs,
        )
    }
}
