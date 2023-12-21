package com.susu.core.designsystem.component.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.component.button.basic.BasicBlackFilledButton
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun SusuBlackFilledButton(
    modifier: Modifier = Modifier,
    text: String = "",
    style: @Composable () -> ButtonStyle,
    @DrawableRes leftIcon: Int? = null,
    leftIconContentDescription: String? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconContentDescription: String? = null,
    isActive: Boolean = true,
    onClick: () -> Unit = {},
) {
    val (paddingValues, iconSpacing, textStyle) = style()
    BasicBlackFilledButton(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        text = text,
        padding = paddingValues,
        textStyle = textStyle,
        leftIcon = leftIcon,
        leftIconContentDescription = leftIconContentDescription,
        rightIcon = rightIcon,
        rightIconContentDescription = rightIconContentDescription,
        iconSpacing = iconSpacing,
        isActive = isActive,
        onClick = onClick,
    )
}

@Preview(showBackground = true)
@Composable
fun SusuBlackFilledLargeButtonPreview() {
    SusuTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            SusuBlackFilledButtonPreview(LargeButtonStyle.height62, LargeButtonStyle.height54, LargeButtonStyle.height46)

            SusuBlackFilledButtonPreview(MediumButtonStyle.height60, MediumButtonStyle.height52, MediumButtonStyle.height44)

            SusuBlackFilledButtonPreview(SmallButtonStyle.height48, SmallButtonStyle.height40, SmallButtonStyle.height32)

            SusuBlackFilledButtonPreview(XSmallButtonStyle.height44, XSmallButtonStyle.height36, XSmallButtonStyle.height28)
        }
    }
}

@Composable
private fun SusuBlackFilledButtonPreview(
    large: @Composable () -> ButtonStyle,
    medium: @Composable () -> ButtonStyle,
    small: @Composable () -> ButtonStyle,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        SusuBlackFilledButton(
            modifier = Modifier.fillMaxWidth(),
            style = large,
            text = "Button",
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            SusuBlackFilledButton(
                style = large,
                text = "Button",
                rightIcon = R.drawable.ic_arrow_left,
                leftIcon = R.drawable.ic_arrow_left,
            )

            SusuBlackFilledButton(
                style = large,
                text = "Button",
                isActive = false,
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            SusuBlackFilledButton(
                style = medium,
                text = "Button",
            )

            SusuBlackFilledButton(
                style = medium,
                text = "Button",
                isActive = false,
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            SusuBlackFilledButton(
                style = small,
                text = "Button",
            )

            SusuBlackFilledButton(
                style = small,
                text = "Button",
                isActive = false,
            )
        }
    }
}
