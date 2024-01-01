package com.susu.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun SusuTheme(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalTypography provides Typography,
        LocalColorScheme provides LightColorScheme,
        LocalSpacing provides Spacing,
    ) {
        MaterialTheme(
            colorScheme = lightColorScheme(
                background = SusuTheme.colorScheme.background15,
            ),
            content = content,
        )
    }
}

object SusuTheme {
    val typography: SusuTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val colorScheme: SusuColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current

    /**
     * spacing_xxxxxs = 2.dp,
     *
     * spacing_xxxxs = 4.dp,
     *
     * spacing_xxxs = 6.dp,
     *
     * spacing_xxs = 8.dp,
     *
     * spacing_xs = 10.dp,
     *
     * spacing_s = 12.dp,
     *
     * spacing_xm = 14.dp,
     *
     * spacing_m = 16.dp,
     *
     * spacing_l = 20.dp,
     *
     * spacing_xl = 24.dp,
     *
     * spacing_xxl = 32.dp,
     *
     * spacing_xxxl = 36.dp,
     *
     * spacing_xxxxl = 40.dp,
     */
    val spacing: SusuSpacing
        @Composable
        @ReadOnlyComposable
        get() = LocalSpacing.current
}
