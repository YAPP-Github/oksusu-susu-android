package com.susu.core.designsystem.component.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.Orange20
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme

enum class GhostButtonColor(
    val activeContentColor: Color,
    val inactiveContentColor: Color,
    val activeBackgroundColor: Color,
    val inactiveBackgroundColor: Color,
    val rippleColor: Color,
) {
    Black(
        activeContentColor = Gray100,
        inactiveContentColor = Gray30,
        activeBackgroundColor = Gray10,
        inactiveBackgroundColor = Gray10,
        rippleColor = Color.Unspecified,
    ),
    Orange(
        activeContentColor = Orange60,
        inactiveContentColor = Orange20,
        activeBackgroundColor = Gray10,
        inactiveBackgroundColor = Gray10,
        rippleColor = Color.Unspecified,
    ),
}

@Composable
fun SusuGhostButton(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    text: String? = null,
    color: GhostButtonColor,
    style: @Composable () -> ButtonStyle,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    isActive: Boolean = true,
    onClick: () -> Unit = {},
) {
    val (paddingValues, iconSpacing, textStyle) = style()
    color.run {
        BasicButton(
            modifier = modifier,
            shape = shape,
            text = text,
            textStyle = textStyle,
            contentColor = if (isActive) activeContentColor else inactiveContentColor,
            rippleColor = rippleColor,
            backgroundColor = if (isActive) activeBackgroundColor else inactiveBackgroundColor,
            leftIcon = leftIcon,
            rightIcon = rightIcon,
            padding = paddingValues,
            iconSpacing = iconSpacing,
            isActive = isActive,
            onClick = onClick,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
fun SusuBlackGhostButtonPreview() {
    SusuTheme {
        val color = GhostButtonColor.Black
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            SusuGhostButtonPreview(
                large = LargeButtonStyle.height62,
                medium = LargeButtonStyle.height54,
                small = LargeButtonStyle.height46,
                color = color,
            )

            SusuGhostButtonPreview(
                large = MediumButtonStyle.height60,
                medium = MediumButtonStyle.height52,
                small = MediumButtonStyle.height44,
                color = color,
            )

            SusuGhostButtonPreview(
                large = SmallButtonStyle.height48,
                medium = SmallButtonStyle.height40,
                small = SmallButtonStyle.height32,
                color = color,
            )

            SusuGhostButtonPreview(
                large = XSmallButtonStyle.height44,
                medium = XSmallButtonStyle.height36,
                small = XSmallButtonStyle.height28,
                color = color,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SusuOrangeGhostButtonPreview() {
    SusuTheme {
        val color = GhostButtonColor.Orange
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            SusuGhostButtonPreview(
                large = LargeButtonStyle.height62,
                medium = LargeButtonStyle.height54,
                small = LargeButtonStyle.height46,
                color = color,
            )

            SusuGhostButtonPreview(
                large = MediumButtonStyle.height60,
                medium = MediumButtonStyle.height52,
                small = MediumButtonStyle.height44,
                color = color,
            )

            SusuGhostButtonPreview(
                large = SmallButtonStyle.height48,
                medium = SmallButtonStyle.height40,
                small = SmallButtonStyle.height32,
                color = color,
            )

            SusuGhostButtonPreview(
                large = XSmallButtonStyle.height44,
                medium = XSmallButtonStyle.height36,
                small = XSmallButtonStyle.height28,
                color = color,
            )
        }
    }
}

@Composable
private fun SusuGhostButtonPreview(
    large: @Composable () -> ButtonStyle,
    medium: @Composable () -> ButtonStyle,
    small: @Composable () -> ButtonStyle,
    color: GhostButtonColor,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        SusuGhostButton(
            modifier = Modifier.fillMaxWidth(),
            style = large,
            color = color,
            text = "Button",
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            SusuGhostButton(
                style = large,
                color = color,
                text = "Button",
                leftIcon = {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = "",
                        tint = color.activeContentColor,
                    )
                },
                rightIcon = {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = "",
                        tint = color.activeContentColor,
                    )
                },
            )

            SusuGhostButton(
                style = large,
                color = color,
                text = "Button",
                isActive = false,
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            SusuGhostButton(
                style = medium,
                color = color,
                text = "Button",
            )

            SusuGhostButton(
                style = medium,
                color = color,
                text = "Button",
                isActive = false,
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            SusuGhostButton(
                style = small,
                color = color,
                text = "Button",
            )

            SusuGhostButton(
                style = small,
                color = color,
                text = "Button",
                isActive = false,
            )
        }
    }
}
