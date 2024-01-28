package com.susu.core.designsystem.component.button

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.theme.Gray30
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable

@Composable
fun RefreshButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Icon(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Gray30,
                shape = RoundedCornerShape(size = 100.dp),
            )
            .size(44.dp)
            .clip(RoundedCornerShape(size = 100.dp))
            .susuClickable(onClick = onClick)
            .padding(SusuTheme.spacing.spacing_xs),
        painter = painterResource(id = R.drawable.ic_refresh),
        contentDescription = stringResource(com.susu.core.ui.R.string.content_description_refresh),
    )
}

@Preview(showBackground = true)
@Composable
fun RefreshButtonPreview() {
    SusuTheme {
        RefreshButton()
    }
}
