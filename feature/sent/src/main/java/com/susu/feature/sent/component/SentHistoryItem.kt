package com.susu.feature.sent.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.badge.BadgeColor
import com.susu.core.designsystem.component.badge.BadgeStyle
import com.susu.core.designsystem.component.badge.SusuBadge
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.sent.R

@Composable
fun SentHistoryItem(
    modifier: Modifier = Modifier,
    isSent: Boolean = true,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(
                id = if (isSent) {
                    R.drawable.ic_round_arrow_sent
                } else {
                    R.drawable.ic_round_arrow_received
                },
            ),
            contentDescription = null,
            tint = if (isSent) Orange60 else Gray40,
            modifier = modifier.size(20.dp),
        )
        Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_s))

        // TODO: text 수정 필요
        SusuBadge(
            color = if (isSent) BadgeColor.Gray90 else BadgeColor.Gray40,
            text = "돌잔치",
            padding = BadgeStyle.extraSmallBadge,
        )
        Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_s))
        Text(
            text = "23.07.18",
            style = SusuTheme.typography.title_xxxs,
            color = if (isSent) Gray100 else Gray40,
        )
        Spacer(modifier = modifier.weight(1f))
        Text(
            text = "50,000원",
            style = SusuTheme.typography.title_xxs,
            color = if (isSent) Gray100 else Gray40,
        )
    }
}
