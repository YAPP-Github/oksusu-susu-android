package com.susu.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.ui.extension.susuClickable

@Composable
fun ClearIconButton(
    iconSize: Dp,
    tint: Color = Gray30,
    onClick: () -> Unit,
) {
    Icon(
        modifier = Modifier
            .clip(CircleShape)
            .background(tint)
            .size(iconSize)
            .susuClickable(onClick = onClick),
        painter = painterResource(id = com.susu.core.ui.R.drawable.ic_close),
        contentDescription = "",
        tint = Gray10,
    )
}

@Preview
@Composable
fun ClearIconButtonPreview() {
    Column {
        ClearIconButton(
            iconSize = 24.dp,
            onClick = {},
        )
    }
}
