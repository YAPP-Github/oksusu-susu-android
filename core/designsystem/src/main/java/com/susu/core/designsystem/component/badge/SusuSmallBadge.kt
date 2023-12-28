package com.susu.core.designsystem.component.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.Gray20
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Gray70
import com.susu.core.designsystem.theme.Gray90
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun SusuSmallBadge(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    backgroundColor: Color,
    textColor: Color,
    text: String,
    horizontalPadding: Dp = SusuTheme.spacing.spacing_xxs,
    verticalPadding: Dp = SusuTheme.spacing.spacing_xxxxxs,
) {
    Box(
        modifier = modifier.background(backgroundColor, shape),
    ) {
        Text(
            text = text,
            style = SusuTheme.typography.title_xxxs,
            color = textColor,
            modifier = modifier.padding(horizontal = horizontalPadding, vertical = verticalPadding),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SusuSmallBadgePreview() {
    SusuTheme {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            SusuSmallBadge(
                backgroundColor = Gray20,
                textColor = Gray70,
                text = "전체 100,000원",
            )
            SusuSmallBadge(
                backgroundColor = SusuTheme.colorScheme.primary,
                textColor = SusuTheme.colorScheme.background10,
                text = "가족",
            )
            SusuSmallBadge(
                backgroundColor = SusuTheme.colorScheme.accent,
                textColor = SusuTheme.colorScheme.background10,
                text = "미방문",
            )
            SusuSmallBadge(
                backgroundColor = Gray90,
                textColor = SusuTheme.colorScheme.background10,
                text = "선물 O",
            )
            SusuSmallBadge(
                backgroundColor = Gray40,
                textColor = SusuTheme.colorScheme.background10,
                text = "선물 O",
            )
            SusuSmallBadge(
                backgroundColor = Gray30,
                textColor = Gray70,
                text = "전체 100,000원",
            )
            SusuSmallBadge(
                backgroundColor = SusuTheme.colorScheme.error,
                textColor = SusuTheme.colorScheme.background10,
                text = "미방문",
            )
        }
    }
}
