package com.susu.core.designsystem.component.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable

@Composable
fun SusuFloatingButton(
    modifier: Modifier = Modifier,
    @DrawableRes imageResId: Int = R.drawable.ic_floating_button_add,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .shadow(elevation = 16.dp, shape = CircleShape)
            .clip(CircleShape)
            .background(Gray100)
            .susuClickable(rippleColor = Gray10, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = stringResource(id = com.susu.core.ui.R.string.content_description_add_button),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SusuFloatingButtonPreview() {
    SusuTheme {
        Box(modifier = Modifier.padding(20.dp)) {
            SusuFloatingButton()
        }
    }
}
