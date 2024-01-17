package com.susu.core.designsystem.component.button

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun FilterButton(
    isFiltered: Boolean = false,
    onClickFilterButton: () -> Unit,
) {
    if (isFiltered) {
        SusuFilledButton(
            color = FilledButtonColor.Black,
            style = SmallButtonStyle.height32,
            leftIcon = {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = com.susu.core.ui.R.drawable.ic_filter),
                    contentDescription = stringResource(R.string.content_description_filter_icon),
                    tint = Gray10,
                )
            },
            onClick = onClickFilterButton,
        )
    } else {
        SusuGhostButton(
            color = GhostButtonColor.Black,
            style = SmallButtonStyle.height32,
            text = stringResource(com.susu.core.ui.R.string.word_filter),
            leftIcon = {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = com.susu.core.ui.R.drawable.ic_filter),
                    contentDescription = stringResource(R.string.content_description_filter_icon),
                )
            },
            onClick = onClickFilterButton,
        )
    }
}

@Preview
@Composable
fun FilterButtonPreview() {
    SusuTheme {
        FilterButton {

        }
    }
}
