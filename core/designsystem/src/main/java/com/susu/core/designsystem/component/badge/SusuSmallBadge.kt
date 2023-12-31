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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun SusuSmallBadge(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    color: BadgeColor,
    text: String,
    padding: BadgePadding = BadgePadding.SmallBadgePadding,
) {
    Box(
        modifier = modifier.background(color.backgroundColor, shape),
    ) {
        Text(
            text = text,
            style = SusuTheme.typography.title_xxxs,
            color = color.textColor,
            modifier = modifier.padding(
                horizontal = padding.horizontalPadding,
                vertical = padding.verticalPadding,
            ),
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
                color = BadgeColor.BadgeGray20,
                text = "전체 100,000원",
            )
            SusuSmallBadge(
                color = BadgeColor.BadgeOrange60,
                text = "가족",
            )
            SusuSmallBadge(
                color = BadgeColor.BadgeBlue60,
                text = "미방문",
            )
            SusuSmallBadge(
                color = BadgeColor.BadgeGray90,
                text = "선물 O",
            )
            SusuSmallBadge(
                color = BadgeColor.BadgeGray40,
                text = "선물 O",
            )
            SusuSmallBadge(
                color = BadgeColor.BadgeGray30,
                text = "전체 100,000원",
            )
            SusuSmallBadge(
                color = BadgeColor.BadgeRed60,
                text = "미방문",
            )
        }
    }
}
