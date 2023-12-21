package com.susu.core.designsystem.component.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.component.button.basic.BasicButton
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.Orange20
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme

enum class FilledButtonColor(
    val activeContentColor: Color,
    val inactiveContentColor: Color,
    val activeBackgroundColor: Color,
    val inactiveBackgroundColor: Color,
    val rippleColor: Color,
) {
    Black(
        activeContentColor = Gray10,
        inactiveContentColor = Gray10,
        activeBackgroundColor = Gray100,
        inactiveBackgroundColor = Gray30,
        rippleColor = Gray10,
    ),
    Orange(
        activeContentColor = Gray10,
        inactiveContentColor = Gray10,
        activeBackgroundColor = Orange60,
        inactiveBackgroundColor = Orange20,
        rippleColor = Color.Unspecified,
    )
}

@Composable
fun SusuFilledButton(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    text: String = "",
    color: FilledButtonColor,
    style: @Composable () -> ButtonStyle,
    @DrawableRes leftIcon: Int? = null,
    leftIconContentDescription: String? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconContentDescription: String? = null,
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
            leftIconContentDescription = leftIconContentDescription,
            rightIcon = rightIcon,
            rightIconContentDescription = rightIconContentDescription,
            padding = paddingValues,
            iconSpacing = iconSpacing,
            isActive = isActive,
            onClick = onClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SusuBlackFilledLargeButtonPreview() {
    SusuTheme {
        val color = FilledButtonColor.Black
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            SusuFilledButtonPreview(
                large = LargeButtonStyle.height62,
                medium = LargeButtonStyle.height54,
                small = LargeButtonStyle.height46,
                color = color,
            )

            SusuFilledButtonPreview(
                large = MediumButtonStyle.height60,
                medium = MediumButtonStyle.height52,
                small = MediumButtonStyle.height44,
                color = color,
            )

            SusuFilledButtonPreview(
                large = SmallButtonStyle.height48,
                medium = SmallButtonStyle.height40,
                small = SmallButtonStyle.height32,
                color = color,
            )

            SusuFilledButtonPreview(
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
fun SusuOrangeFilledLargeButtonPreview() {
    SusuTheme {
        val color = FilledButtonColor.Orange
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            SusuFilledButtonPreview(
                large = LargeButtonStyle.height62,
                medium = LargeButtonStyle.height54,
                small = LargeButtonStyle.height46,
                color = color,
            )

            SusuFilledButtonPreview(
                large = MediumButtonStyle.height60,
                medium = MediumButtonStyle.height52,
                small = MediumButtonStyle.height44,
                color = color,
            )

            SusuFilledButtonPreview(
                large = SmallButtonStyle.height48,
                medium = SmallButtonStyle.height40,
                small = SmallButtonStyle.height32,
                color = color,
            )

            SusuFilledButtonPreview(
                large = XSmallButtonStyle.height44,
                medium = XSmallButtonStyle.height36,
                small = XSmallButtonStyle.height28,
                color = color,
            )
        }
    }
}

@Composable
private fun SusuFilledButtonPreview(
    large: @Composable () -> ButtonStyle,
    medium: @Composable () -> ButtonStyle,
    small: @Composable () -> ButtonStyle,
    color: FilledButtonColor,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        SusuFilledButton(
            modifier = Modifier.fillMaxWidth(),
            style = large,
            color = color,
            text = "Button",
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            SusuFilledButton(
                style = large,
                color = color,
                text = "Button",
                rightIcon = R.drawable.ic_arrow_left,
                leftIcon = R.drawable.ic_arrow_left,
            )

            SusuFilledButton(
                style = large,
                color = color,
                text = "Button",
                isActive = false,
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            SusuFilledButton(
                style = medium,
                color = color,
                text = "Button",
            )

            SusuFilledButton(
                style = medium,
                color = color,
                text = "Button",
                isActive = false,
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            SusuFilledButton(
                style = small,
                color = color,
                text = "Button",
            )

            SusuFilledButton(
                style = small,
                color = color,
                text = "Button",
                isActive = false,
            )
        }
    }
}
