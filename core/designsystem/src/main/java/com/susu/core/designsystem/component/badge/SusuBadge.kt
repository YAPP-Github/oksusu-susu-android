package com.susu.core.designsystem.component.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
fun SusuBadge(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    color: BadgeColor,
    text: String,
    padding: @Composable () -> BadgePadding,
) {
    val horizontalPadding = padding().horizontalPadding
    val verticalPadding = padding().verticalPadding

    Box(
        modifier = modifier.background(color.backgroundColor, shape),
    ) {
        Text(
            text = text,
            style = SusuTheme.typography.title_xxxs,
            color = color.textColor,
            modifier = modifier.padding(
                horizontal = horizontalPadding,
                vertical = verticalPadding,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SusuSmallBadgePreview() {
    SusuTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SusuBadge(
                    color = BadgeColor.Gray20,
                    text = "전체 100,000원",
                    padding = BadgeStyle.smallBadge,
                )
                SusuBadge(
                    color = BadgeColor.Orange60,
                    text = "가족",
                    padding = BadgeStyle.smallBadge,
                )
                SusuBadge(
                    color = BadgeColor.Blue60,
                    text = "미방문",
                    padding = BadgeStyle.smallBadge,
                )
                SusuBadge(
                    color = BadgeColor.Gray90,
                    text = "선물 O",
                    padding = BadgeStyle.smallBadge,
                )
                SusuBadge(
                    color = BadgeColor.Gray40,
                    text = "선물 O",
                    padding = BadgeStyle.smallBadge,
                )
                SusuBadge(
                    color = BadgeColor.Gray30,
                    text = "전체 100,000원",
                    padding = BadgeStyle.smallBadge,
                )
                SusuBadge(
                    color = BadgeColor.Red60,
                    text = "미방문",
                    padding = BadgeStyle.smallBadge,
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SusuBadge(
                    color = BadgeColor.Gray20,
                    text = "전체 100,000원",
                    padding = BadgeStyle.extraSmallBadge,
                )
                SusuBadge(
                    color = BadgeColor.Orange60,
                    text = "가족",
                    padding = BadgeStyle.extraSmallBadge,
                )
                SusuBadge(
                    color = BadgeColor.Blue60,
                    text = "미방문",
                    padding = BadgeStyle.extraSmallBadge,
                )
                SusuBadge(
                    color = BadgeColor.Gray90,
                    text = "선물 O",
                    padding = BadgeStyle.extraSmallBadge,
                )
                SusuBadge(
                    color = BadgeColor.Gray40,
                    text = "선물 O",
                    padding = BadgeStyle.extraSmallBadge,
                )
                SusuBadge(
                    color = BadgeColor.Gray30,
                    text = "전체 100,000원",
                    padding = BadgeStyle.extraSmallBadge,
                )
                SusuBadge(
                    color = BadgeColor.Red60,
                    text = "미방문",
                    padding = BadgeStyle.extraSmallBadge,
                )
            }
        }
    }
}
