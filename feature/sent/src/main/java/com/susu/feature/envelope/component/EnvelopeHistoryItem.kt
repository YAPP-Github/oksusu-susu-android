package com.susu.feature.envelope.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.susu.core.designsystem.component.badge.BadgeColor
import com.susu.core.designsystem.component.badge.BadgeStyle
import com.susu.core.designsystem.component.badge.SusuBadge
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.sent.R

@Composable
fun EnvelopeHistoryItem(
    modifier: Modifier = Modifier,
    isSent: Boolean = true,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .susuClickable(
                onClick = onClick
            )
            .padding(
                horizontal = SusuTheme.spacing.spacing_m,
                vertical = SusuTheme.spacing.spacing_s,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // TODO: text 변경하기
        Icon(
            painter = painterResource(
                id = if (isSent) {
                    R.drawable.ic_round_arrow_sent
                } else {
                    R.drawable.ic_round_arrow_received
                },
            ),
            contentDescription = null,
            tint = if (isSent) Orange60 else Gray50,
        )
        Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_s))
        SusuBadge(
            color = if (isSent) BadgeColor.Gray90 else BadgeColor.Gray40,
            text = "결혼식",
            padding = BadgeStyle.smallBadge,
        )
        Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_s))
        Text(
            text = "22.07.18",
            style = SusuTheme.typography.title_xxs,
            color = if (isSent) Gray100 else Gray50,
        )
        Spacer(modifier = modifier.weight(1f))
        Text(
            text = " 100,000원",
            style = SusuTheme.typography.title_xs,
            color = if (isSent) Gray100 else Gray50,
        )
    }
}
