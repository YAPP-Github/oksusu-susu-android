package com.susu.core.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.SusuTheme

data class ButtonStyle(
    val paddingValues: PaddingValues = PaddingValues(0.dp),
    val iconSpacing: Dp = 0.dp,
    val textStyle: TextStyle = TextStyle.Default,
)

object LargeButtonStyle {

    val height62: @Composable () -> ButtonStyle = {
        ButtonStyle(
            paddingValues = PaddingValues(
                horizontal = SusuTheme.spacing.spacing_xl,
                vertical = SusuTheme.spacing.spacing_m,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxs,
            textStyle = SusuTheme.typography.title_s,
        )
    }

    val height54: @Composable () -> ButtonStyle = {
        ButtonStyle(
            paddingValues = PaddingValues(
                horizontal = SusuTheme.spacing.spacing_l,
                vertical = SusuTheme.spacing.spacing_s,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxxs,
            textStyle = SusuTheme.typography.title_s,
        )
    }

    val height46: @Composable () -> ButtonStyle = {
        ButtonStyle(
            paddingValues = PaddingValues(
                horizontal = SusuTheme.spacing.spacing_m,
                vertical = SusuTheme.spacing.spacing_xs,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxxs,
            textStyle = SusuTheme.typography.title_s,
        )
    }
}

object MediumButtonStyle {

    val height60: @Composable () -> ButtonStyle = {
        ButtonStyle(
            paddingValues = PaddingValues(
                horizontal = SusuTheme.spacing.spacing_xl,
                vertical = SusuTheme.spacing.spacing_m,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxs,
            textStyle = SusuTheme.typography.title_xs,
        )
    }

    val height52: @Composable () -> ButtonStyle = {
        ButtonStyle(
            paddingValues = PaddingValues(
                horizontal = SusuTheme.spacing.spacing_l,
                vertical = SusuTheme.spacing.spacing_s,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxxs,
            textStyle = SusuTheme.typography.title_xs,
        )
    }

    val height44: @Composable () -> ButtonStyle = {
        ButtonStyle(
            paddingValues = PaddingValues(
                horizontal = SusuTheme.spacing.spacing_m,
                vertical = SusuTheme.spacing.spacing_xxs,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxs,
            textStyle = SusuTheme.typography.title_xs,
        )
    }
}

object SmallButtonStyle {

    val height48: @Composable () -> ButtonStyle = {
        ButtonStyle(
            paddingValues = PaddingValues(
                horizontal = SusuTheme.spacing.spacing_m,
                vertical = SusuTheme.spacing.spacing_s,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxs,
            textStyle = SusuTheme.typography.title_xxs,
        )
    }

    val height40: @Composable () -> ButtonStyle = {
        ButtonStyle(
            paddingValues = PaddingValues(
                horizontal = SusuTheme.spacing.spacing_s,
                vertical = SusuTheme.spacing.spacing_xxs,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxxs,
            textStyle = SusuTheme.typography.title_xxs,
        )
    }

    val height32: @Composable () -> ButtonStyle = {
        ButtonStyle(
            paddingValues = PaddingValues(
                horizontal = SusuTheme.spacing.spacing_xxs,
                vertical = SusuTheme.spacing.spacing_xxxxs,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxxxs,
            textStyle = SusuTheme.typography.title_xxs,
        )
    }
}

object XSmallButtonStyle {

    val height44: @Composable () -> ButtonStyle = {
        ButtonStyle(
            paddingValues = PaddingValues(
                horizontal = SusuTheme.spacing.spacing_m,
                vertical = SusuTheme.spacing.spacing_s,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxs,
            textStyle = SusuTheme.typography.title_xxxs,
        )
    }

    val height36: @Composable () -> ButtonStyle = {
        ButtonStyle(
            paddingValues = PaddingValues(
                horizontal = SusuTheme.spacing.spacing_s,
                vertical = SusuTheme.spacing.spacing_xxs,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxxs,
            textStyle = SusuTheme.typography.title_xxxs,
        )
    }

    val height28: @Composable () -> ButtonStyle = {
        ButtonStyle(
            paddingValues = PaddingValues(
                horizontal = SusuTheme.spacing.spacing_xxs,
                vertical = SusuTheme.spacing.spacing_xxxxs,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxxxs,
            textStyle = SusuTheme.typography.title_xxxs,
        )
    }
}
