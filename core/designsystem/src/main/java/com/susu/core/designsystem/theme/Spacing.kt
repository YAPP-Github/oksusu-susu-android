package com.susu.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val Spacing = SusuSpacing(
    spacing_xxxxxs = 2.dp,
    spacing_xxxxs = 4.dp,
    spacing_xxxs = 6.dp,
    spacing_xxs = 8.dp,
    spacing_xs = 10.dp,
    spacing_s = 12.dp,
    spacing_xm = 14.dp,
    spacing_m = 16.dp,
    spacing_l = 20.dp,
    spacing_xl = 24.dp,
    spacing_xxl = 32.dp,
    spacing_xxxl = 36.dp,
    spacing_xxxxl = 40.dp,
    spacing_xxxxxxl = 48.dp,
)

@Suppress("ConstructorParameterNaming")
@Immutable
data class SusuSpacing(
    val spacing_xxxxxs: Dp,
    val spacing_xxxxs: Dp,
    val spacing_xxxs: Dp,
    val spacing_xxs: Dp,
    val spacing_xs: Dp,
    val spacing_s: Dp,
    val spacing_m: Dp,
    val spacing_xm: Dp,
    val spacing_l: Dp,
    val spacing_xl: Dp,
    val spacing_xxl: Dp,
    val spacing_xxxl: Dp,
    val spacing_xxxxl: Dp,
    val spacing_xxxxxxl: Dp,
)

val LocalSpacing = staticCompositionLocalOf {
    SusuSpacing(
        spacing_xxxxxs = Dp.Unspecified,
        spacing_xxxxs = Dp.Unspecified,
        spacing_xxxs = Dp.Unspecified,
        spacing_xxs = Dp.Unspecified,
        spacing_xs = Dp.Unspecified,
        spacing_s = Dp.Unspecified,
        spacing_m = Dp.Unspecified,
        spacing_xm = Dp.Unspecified,
        spacing_l = Dp.Unspecified,
        spacing_xl = Dp.Unspecified,
        spacing_xxl = Dp.Unspecified,
        spacing_xxxl = Dp.Unspecified,
        spacing_xxxxl = Dp.Unspecified,
        spacing_xxxxxxl = Dp.Unspecified,
    )
}
