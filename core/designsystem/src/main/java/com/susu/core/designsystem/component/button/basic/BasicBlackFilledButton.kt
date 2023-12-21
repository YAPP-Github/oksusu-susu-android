package com.susu.core.designsystem.component.button.basic

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun BasicBlackFilledButton(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    text: String = "",
    textStyle: TextStyle = TextStyle.Default,
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    iconSpacing: Dp = 0.dp,
    padding: PaddingValues = PaddingValues(0.dp),
    isActive: Boolean = true,
    onClick: () -> Unit = {},
) {
    BasicButton(
        modifier = modifier,
        shape = shape,
        text = text,
        textStyle = textStyle,
        textColor = Gray10,
        rippleColor = Gray10,
        backgroundColor = if (isActive) Gray100 else Gray30,
        leftIcon = leftIcon,
        rightIcon = rightIcon,
        padding = padding,
        iconSpacing = iconSpacing,
        isActive = isActive,
        onClick = onClick,
    )
}

@Preview
@Composable
fun BasicBlackFilledButtonPreview() {
    SusuTheme {
        BasicBlackFilledButton(
            text = "Button",
        )
    }
}
