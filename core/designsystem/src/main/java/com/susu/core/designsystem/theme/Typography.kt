package com.susu.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.susu.core.designsystem.R

internal val pretendardFamily = FontFamily(
    Font(R.font.pretendardbold, FontWeight.Bold),
    Font(R.font.pretendardregular, FontWeight.Normal),
)

private val pretendardStyle = TextStyle(
    fontFamily = pretendardFamily,
    letterSpacing = TextUnit(0.98f, TextUnitType.Sp),
    platformStyle = PlatformTextStyle(
        includeFontPadding = false,
    ),
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None,
    ),
)

private val pretendardTitleStyle = pretendardStyle.copy(
    fontWeight = FontWeight.Bold,
)

private val pretendardTextStyle = pretendardStyle.copy(
    fontWeight = FontWeight.Normal,
)

internal val Typography = SusuTypography(
    title_xxxxl = pretendardTitleStyle.copy(
        fontSize = 40.sp
    ),
    title_xxxl = pretendardTitleStyle.copy(
        fontSize = 36.sp
    ),
    title_xxl = pretendardTitleStyle.copy(
        fontSize = 32.sp
    ),
    title_xl = pretendardTitleStyle.copy(
        fontSize = 28.sp
    ),
    title_l = pretendardTitleStyle.copy(
        fontSize = 24.sp
    ),
    title_m = pretendardTitleStyle.copy(
        fontSize = 20.sp
    ),
    title_s = pretendardTitleStyle.copy(
        fontSize = 18.sp
    ),
    title_xs = pretendardTitleStyle.copy(
        fontSize = 16.sp
    ),
    title_xxs = pretendardTitleStyle.copy(
        fontSize = 14.sp
    ),
    title_xxxs = pretendardTitleStyle.copy(
        fontSize = 12.sp
    ),
    title_xxxxs = pretendardTitleStyle.copy(
        fontSize = 10.sp
    ),

    text_xxxxl = pretendardTextStyle.copy(
        fontSize = 40.sp
    ),
    text_xxxl = pretendardTextStyle.copy(
        fontSize = 36.sp
    ),
    text_xxl = pretendardTextStyle.copy(
        fontSize = 32.sp
    ),
    text_xl = pretendardTextStyle.copy(
        fontSize = 28.sp
    ),
    text_l = pretendardTextStyle.copy(
        fontSize = 24.sp
    ),
    text_m = pretendardTextStyle.copy(
        fontSize = 20.sp
    ),
    text_s = pretendardTextStyle.copy(
        fontSize = 18.sp
    ),
    text_xs = pretendardTextStyle.copy(
        fontSize = 16.sp
    ),
    text_xxs = pretendardTextStyle.copy(
        fontSize = 14.sp
    ),
    text_xxxs = pretendardTextStyle.copy(
        fontSize = 12.sp
    ),
    text_xxxxs = pretendardTextStyle.copy(
        fontSize = 10.sp
    ),
)

@Immutable
data class SusuTypography(
    val title_xxxxl: TextStyle,
    val title_xxxl: TextStyle,
    val title_xxl: TextStyle,
    val title_xl: TextStyle,
    val title_l: TextStyle,
    val title_m: TextStyle,
    val title_s: TextStyle,
    val title_xs: TextStyle,
    val title_xxs: TextStyle,
    val title_xxxs: TextStyle,
    val title_xxxxs: TextStyle,

    val text_xxxxl: TextStyle,
    val text_xxxl: TextStyle,
    val text_xxl: TextStyle,
    val text_xl: TextStyle,
    val text_l: TextStyle,
    val text_m: TextStyle,
    val text_s: TextStyle,
    val text_xs: TextStyle,
    val text_xxs: TextStyle,
    val text_xxxs: TextStyle,
    val text_xxxxs: TextStyle,
)

val LocalTypography = staticCompositionLocalOf {
    SusuTypography(
        title_xxxxl = pretendardStyle,
        title_xxxl = pretendardStyle,
        title_xxl = pretendardStyle,
        title_xl = pretendardStyle,
        title_l = pretendardStyle,
        title_m = pretendardStyle,
        title_s = pretendardStyle,
        title_xs = pretendardStyle,
        title_xxs = pretendardStyle,
        title_xxxs = pretendardStyle,
        title_xxxxs = pretendardStyle,

        text_xxxxl = pretendardStyle,
        text_xxxl = pretendardStyle,
        text_xxl = pretendardStyle,
        text_xl = pretendardStyle,
        text_l = pretendardStyle,
        text_m = pretendardStyle,
        text_s = pretendardStyle,
        text_xs = pretendardStyle,
        text_xxs = pretendardStyle,
        text_xxxs = pretendardStyle,
        text_xxxxs = pretendardStyle,
    )
}
