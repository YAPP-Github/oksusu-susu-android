package com.susu.core.designsystem.component.button.basic

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
fun BasicFilledButton(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: FilledButtonColor,
    text: String = "",
    textStyle: TextStyle = TextStyle.Default,
    @DrawableRes leftIcon: Int? = null,
    leftIconContentDescription: String? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconContentDescription: String? = null,
    iconSpacing: Dp = 0.dp,
    padding: PaddingValues = PaddingValues(0.dp),
    isActive: Boolean = true,
    onClick: () -> Unit = {},
) {
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
            padding = padding,
            iconSpacing = iconSpacing,
            isActive = isActive,
            onClick = onClick,
        )
    }
}

@Preview
@Composable
fun BasicBlackFilledButtonPreview() {
    SusuTheme {
        BasicFilledButton(
            color = FilledButtonColor.Black,
            text = "Button",
        )
    }
}
