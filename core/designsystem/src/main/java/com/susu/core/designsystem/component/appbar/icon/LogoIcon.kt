package com.susu.core.designsystem.component.appbar.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun LogoIcon() {
    Image(
        modifier = Modifier
            .size(
                width = 56.dp,
                height = 24.dp,
            ),
        painter = painterResource(id = R.drawable.ic_logo),
        contentDescription = stringResource(R.string.content_description_logo_image),
    )
}

@Preview
@Composable
fun LogoIconPreview() {
    SusuTheme {
        BackIcon()
    }
}
