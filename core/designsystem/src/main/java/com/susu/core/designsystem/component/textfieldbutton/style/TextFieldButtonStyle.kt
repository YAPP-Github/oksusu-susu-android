package com.susu.core.designsystem.component.textfieldbutton.style

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.SusuTheme

data class TextFieldButtonStyle(
    val paddingValues: PaddingValues = PaddingValues(0.dp),
    val iconSpacing: Dp = 0.dp,
    val textStyle: TextStyle = TextStyle.Default,
    val innerButtonStyle: @Composable () -> InnerButtonStyle,
    val clearIconSize: Dp = 0.dp,
    val closeIconSize: Dp = 0.dp,
)

object LargeTextFieldButtonStyle {

    val height62: @Composable () -> TextFieldButtonStyle = {
        TextFieldButtonStyle(
            paddingValues = PaddingValues(
                start = SusuTheme.spacing.spacing_xl,
                end = SusuTheme.spacing.spacing_m,
                top = SusuTheme.spacing.spacing_m,
                bottom = SusuTheme.spacing.spacing_m,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxs,
            textStyle = SusuTheme.typography.title_s,
            innerButtonStyle = XSmallInnerButtonStyle.height28,
            clearIconSize = 24.dp,
            closeIconSize = 24.dp,
        )
    }

    val height54: @Composable () -> TextFieldButtonStyle = {
        TextFieldButtonStyle(
            paddingValues = PaddingValues(
                start = SusuTheme.spacing.spacing_l,
                end = SusuTheme.spacing.spacing_xm,
                top = SusuTheme.spacing.spacing_s,
                bottom = SusuTheme.spacing.spacing_s,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxs,
            textStyle = SusuTheme.typography.title_s,
            innerButtonStyle = XSmallInnerButtonStyle.height28,
            clearIconSize = 24.dp,
            closeIconSize = 24.dp,
        )
    }

    val height46: @Composable () -> TextFieldButtonStyle = {
        TextFieldButtonStyle(
            paddingValues = PaddingValues(
                start = SusuTheme.spacing.spacing_m,
                end = SusuTheme.spacing.spacing_s,
                top = SusuTheme.spacing.spacing_xs,
                bottom = SusuTheme.spacing.spacing_xs,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxs,
            textStyle = SusuTheme.typography.title_s,
            innerButtonStyle = XSmallInnerButtonStyle.height28,
            clearIconSize = 24.dp,
            closeIconSize = 24.dp,
        )
    }
}

object MediumTextFieldButtonStyle {

    val height60: @Composable () -> TextFieldButtonStyle = {
        TextFieldButtonStyle(
            paddingValues = PaddingValues(
                start = SusuTheme.spacing.spacing_xl,
                end = SusuTheme.spacing.spacing_m,
                top = SusuTheme.spacing.spacing_m,
                bottom = SusuTheme.spacing.spacing_m,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxs,
            textStyle = SusuTheme.typography.title_xs,
            innerButtonStyle = XSmallInnerButtonStyle.height28,
            clearIconSize = 24.dp,
            closeIconSize = 24.dp,
        )
    }

    val height52: @Composable () -> TextFieldButtonStyle = {
        TextFieldButtonStyle(
            paddingValues = PaddingValues(
                start = SusuTheme.spacing.spacing_l,
                end = SusuTheme.spacing.spacing_xm,
                top = SusuTheme.spacing.spacing_s,
                bottom = SusuTheme.spacing.spacing_s,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxs,
            textStyle = SusuTheme.typography.title_xs,
            innerButtonStyle = XSmallInnerButtonStyle.height28,
            clearIconSize = 24.dp,
            closeIconSize = 24.dp,
        )
    }

    val height44: @Composable () -> TextFieldButtonStyle = {
        TextFieldButtonStyle(
            paddingValues = PaddingValues(
                start = SusuTheme.spacing.spacing_m,
                end = SusuTheme.spacing.spacing_s,
                top = SusuTheme.spacing.spacing_xxs,
                bottom = SusuTheme.spacing.spacing_xxs,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxs,
            textStyle = SusuTheme.typography.title_xs,
            innerButtonStyle = XSmallInnerButtonStyle.height24,
            clearIconSize = 20.dp,
            closeIconSize = 20.dp,
        )
    }
}

object SmallTextFieldButtonStyle {

    val height48: @Composable () -> TextFieldButtonStyle = {
        TextFieldButtonStyle(
            paddingValues = PaddingValues(
                start = SusuTheme.spacing.spacing_m,
                end = SusuTheme.spacing.spacing_s,
                top = SusuTheme.spacing.spacing_s,
                bottom = SusuTheme.spacing.spacing_s,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxs,
            textStyle = SusuTheme.typography.title_xxs,
            innerButtonStyle = XSmallInnerButtonStyle.height24,
            clearIconSize = 20.dp,
            closeIconSize = 20.dp,
        )
    }

    val height40: @Composable () -> TextFieldButtonStyle = {
        TextFieldButtonStyle(
            paddingValues = PaddingValues(
                start = SusuTheme.spacing.spacing_s,
                end = SusuTheme.spacing.spacing_xxs,
                top = SusuTheme.spacing.spacing_xxs,
                bottom = SusuTheme.spacing.spacing_xxs,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxs,
            textStyle = SusuTheme.typography.title_xxs,
            innerButtonStyle = XSmallInnerButtonStyle.height24,
            clearIconSize = 20.dp,
            closeIconSize = 20.dp,
        )
    }

    val height32: @Composable () -> TextFieldButtonStyle = {
        TextFieldButtonStyle(
            paddingValues = PaddingValues(
                start = SusuTheme.spacing.spacing_xxs,
                end = SusuTheme.spacing.spacing_xxs,
                top = SusuTheme.spacing.spacing_xxxxs,
                bottom = SusuTheme.spacing.spacing_xxxxs,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxs,
            textStyle = SusuTheme.typography.title_xxs,
            innerButtonStyle = XSmallInnerButtonStyle.height20,
            clearIconSize = 16.dp,
            closeIconSize = 20.dp,
        )
    }
}

object XSmallTextFieldButtonStyle {

    val height44: @Composable () -> TextFieldButtonStyle = {
        TextFieldButtonStyle(
            paddingValues = PaddingValues(
                start = SusuTheme.spacing.spacing_m,
                end = SusuTheme.spacing.spacing_m,
                top = SusuTheme.spacing.spacing_s,
                bottom = SusuTheme.spacing.spacing_s,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxs,
            textStyle = SusuTheme.typography.title_xxxs,
            innerButtonStyle = XSmallInnerButtonStyle.height20,
            clearIconSize = 20.dp,
            closeIconSize = 20.dp,
        )
    }

    val height36: @Composable () -> TextFieldButtonStyle = {
        TextFieldButtonStyle(
            paddingValues = PaddingValues(
                start = SusuTheme.spacing.spacing_xxs,
                end = SusuTheme.spacing.spacing_xxs,
                top = SusuTheme.spacing.spacing_xxs,
                bottom = SusuTheme.spacing.spacing_xxs,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxxs,
            textStyle = SusuTheme.typography.title_xxxs,
            innerButtonStyle = XSmallInnerButtonStyle.height20,
            clearIconSize = 16.dp,
            closeIconSize = 16.dp,
        )
    }

    val height28: @Composable () -> TextFieldButtonStyle = {
        TextFieldButtonStyle(
            paddingValues = PaddingValues(
                start = SusuTheme.spacing.spacing_xxs,
                end = SusuTheme.spacing.spacing_xxxxs,
                top = SusuTheme.spacing.spacing_xxxxs,
                bottom = SusuTheme.spacing.spacing_xxxxs,
            ),
            iconSpacing = SusuTheme.spacing.spacing_xxxxs,
            textStyle = SusuTheme.typography.title_xxxs,
            innerButtonStyle = XSmallInnerButtonStyle.height20,
            clearIconSize = 16.dp,
            closeIconSize = 16.dp,
        )
    }
}
