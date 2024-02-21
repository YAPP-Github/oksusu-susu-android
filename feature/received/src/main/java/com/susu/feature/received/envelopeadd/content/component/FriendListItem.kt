package com.susu.feature.received.envelopeadd.content.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable
import com.susu.core.ui.util.to_yyyy_dot_MM_dot_dd
import java.time.LocalDateTime

@Composable
fun FriendListItem(
    name: String,
    relationship: String,
    category: String?,
    visitedAt: LocalDateTime?,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .susuClickable(onClick = onClick)
            .padding(
                vertical = SusuTheme.spacing.spacing_s,
            ),
        horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
            style = SusuTheme.typography.title_xs,
            color = Gray100,
            maxLines = 1,
        )
        Text(
            text = relationship,
            style = SusuTheme.typography.title_xs,
            color = Gray60,
            maxLines = 1,
        )
        Text(
            text = category ?: "",
            style = SusuTheme.typography.text_xs,
            color = Gray40,
            maxLines = 1,
        )
        Text(
            text = visitedAt?.to_yyyy_dot_MM_dot_dd() ?: "",
            style = SusuTheme.typography.text_xs,
            color = Gray40,
            maxLines = 1,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
fun FriendListItemPreview() {
    SusuTheme {
        Column {
            FriendListItem(
                name = "",
                relationship = "",
                category = null,
                visitedAt = null,
                onClick = {},
            )
        }
    }
}
