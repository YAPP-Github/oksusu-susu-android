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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.component.badge.BadgeColor
import com.susu.core.designsystem.component.badge.BadgeStyle
import com.susu.core.designsystem.component.badge.SusuBadge
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable
import com.susu.core.ui.extension.toMoneyFormat
import com.susu.core.ui.util.to_yyyy_dot_MM_dot_dd
import com.susu.feature.sent.R
import java.time.LocalDateTime

enum class EnvelopeType {
    SENT, RECEIVED
}

@Composable
fun EnvelopeHistoryItem(
    modifier: Modifier = Modifier,
    type: String = "",
    event: String = "",
    date: LocalDateTime = LocalDateTime.now(),
    money: Long = 0,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .susuClickable(
                onClick = onClick,
            )
            .padding(
                horizontal = SusuTheme.spacing.spacing_m,
                vertical = SusuTheme.spacing.spacing_s,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(
                id = if (type == EnvelopeType.SENT.name) {
                    R.drawable.ic_round_arrow_sent
                } else {
                    R.drawable.ic_round_arrow_received
                },
            ),
            contentDescription = null,
            tint = if (type == EnvelopeType.SENT.name) Orange60 else Gray50,
        )
        Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_s))
        SusuBadge(
            color = if (type == EnvelopeType.SENT.name) BadgeColor.Gray90 else BadgeColor.Gray40,
            text = event,
            padding = BadgeStyle.smallBadge,
        )
        Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_s))
        Text(
            text = date.to_yyyy_dot_MM_dot_dd().substring(2),
            style = SusuTheme.typography.title_xxs,
            color = if (type == EnvelopeType.SENT.name) Gray100 else Gray50,
        )
        Spacer(modifier = modifier.weight(1f))
        Text(
            text = money.toInt().toMoneyFormat() + stringResource(R.string.sent_envelope_card_money_won),
            style = SusuTheme.typography.title_xs,
            color = if (type == EnvelopeType.SENT.name) Gray100 else Gray50,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffff)
@Composable
fun EnvelopeHistoryItemPreView() {
    SusuTheme {
        EnvelopeHistoryItem(
            type = "SENT",
            event = "돌잔치",
            date = LocalDateTime.now(),
            money = 50000,
        )
    }
}
