package com.susu.feature.received.ledgerfilter.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.button.XSmallButtonStyle
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable

@Composable
fun SelectedFilterButton(
    name: String = "",
    onClickCloseIcon: () -> Unit = {},
) {
    SusuFilledButton(
        color = FilledButtonColor.Orange,
        style = XSmallButtonStyle.height28,
        text = name,
        isClickable = false,
        rightIcon = {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(12.dp)
                    .susuClickable(onClick = onClickCloseIcon),
                painter = painterResource(id = com.susu.core.ui.R.drawable.ic_close),
                contentDescription = stringResource(id = com.susu.core.ui.R.string.content_description_close_icon),
                tint = Gray10,
            )
        },
    )
}

@Preview
@Composable
fun SelectedFilterButtonPreview() {
    SusuTheme {
        SelectedFilterButton()
    }
}
