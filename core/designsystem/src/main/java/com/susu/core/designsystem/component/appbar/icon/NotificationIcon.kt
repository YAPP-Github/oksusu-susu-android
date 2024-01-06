package com.susu.core.designsystem.component.appbar.icon

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.R
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable

@Composable
fun NotificationIcon(
    onClick: () -> Unit = {},
) {
    Icon(
        modifier = Modifier
            .clip(CircleShape)
            .susuClickable(
                onClick = onClick,
            )
            .padding(SusuTheme.spacing.spacing_xs),
        painter = painterResource(id = R.drawable.ic_appbar_notification),
        contentDescription = stringResource(R.string.content_description_notification_icon),
        tint = Gray100,
    )
}

@Preview
@Composable
fun NotificationIconPreview() {
    SusuTheme {
        NotificationIcon()
    }
}
