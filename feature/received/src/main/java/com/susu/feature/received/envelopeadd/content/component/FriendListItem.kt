package com.susu.feature.received.envelopeadd.content.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun FriendListItem(
    friend: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = SusuTheme.spacing.spacing_s,
            ),
        horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = friend,
            style = SusuTheme.typography.title_xs,
            color = Gray100,
        )
        Text(
            text = "친구",
            style = SusuTheme.typography.title_xs,
            color = Gray60,
        )
        Text(
            text = "결혼식",
            style = SusuTheme.typography.text_xs,
            color = Gray40,
        )
        Text(
            text = "2022.01.11",
            style = SusuTheme.typography.text_xs,
            color = Gray40,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
fun FriendListItemPreview() {
    val friendList = listOf("김철수", "국영수", "가나다")

    SusuTheme {
        Column {
            for (friend in friendList) {
                FriendListItem(friend = friend)
            }
        }
    }
}
