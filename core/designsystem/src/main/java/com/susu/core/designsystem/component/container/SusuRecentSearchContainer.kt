package com.susu.core.designsystem.component.container

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.R
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable

@Composable
fun SusuRecentSearchContainer(
    modifier: Modifier = Modifier,
    @DrawableRes typeIconId: Int? = null,
    typeIconContentDescription: String? = null,
    text: String = "",
    onClickCloseIcon: () -> Unit = {},
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .height(30.dp)
            .fillMaxWidth()
            .susuClickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_m),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (typeIconId != null) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = typeIconId),
                contentDescription = typeIconContentDescription,
            )
        }

        Text(
            modifier = Modifier.weight(1f),
            text = text,
            style = SusuTheme.typography.title_s,
        )

        if (typeIconId == null) {
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .susuClickable(onClick = onClickCloseIcon),
                painter = painterResource(id = R.drawable.ic_recent_search_close),
                contentDescription = stringResource(com.susu.core.ui.R.string.content_description_close_icon),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SusuRecentSearchContainerPreview() {
    SusuTheme {
        SusuRecentSearchContainer(
            text = "나의 결혼식",
            typeIconId = R.drawable.ic_clear,
            typeIconContentDescription = "",
        )
    }
}
