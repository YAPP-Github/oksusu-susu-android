package com.susu.core.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.theme.Gray25
import com.susu.core.designsystem.theme.Gray70
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun AddConditionButton(
    onClick: () -> Unit,
) {
    BasicButton(
        padding = PaddingValues(SusuTheme.spacing.spacing_xxxxs),
        backgroundColor = Gray25,
        shape = RoundedCornerShape(4.dp),
        leftIcon = {
            Icon(
                painter = painterResource(
                    id = com.susu.core.ui.R.drawable.ic_add,
                ),
                contentDescription = stringResource(com.susu.core.ui.R.string.content_description_add_button),
                tint = Gray70,
            )
        },
    )
}

@Preview
@Composable
fun AddConditionButtonPreview() {
    SusuTheme {
        AddConditionButton {
        }
    }
}
