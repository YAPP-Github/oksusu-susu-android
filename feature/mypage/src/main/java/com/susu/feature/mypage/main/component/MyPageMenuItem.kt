package com.susu.feature.mypage.main.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.susu.core.designsystem.theme.Gray90
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable

@Composable
fun MyPageMenuItem(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(
        horizontal = SusuTheme.spacing.spacing_m,
        vertical = SusuTheme.spacing.spacing_s,
    ),
    titleText: String = "",
    titleTextColor: Color = Gray90,
    titleTextStyle: TextStyle = SusuTheme.typography.title_xxs,
    action: @Composable (() -> Unit)? = null,
    actionItemPadding: Dp = SusuTheme.spacing.spacing_m,
    onMenuClick: () -> Unit = {},
    rippleEnabled: Boolean = true,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .susuClickable(
                rippleEnabled = rippleEnabled,
                onClick = onMenuClick,
            )
            .padding(padding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = titleText,
            style = titleTextStyle,
            color = titleTextColor,
        )
        action?.let {
            Spacer(modifier = Modifier.width(actionItemPadding))
            it()
        }
    }
}
