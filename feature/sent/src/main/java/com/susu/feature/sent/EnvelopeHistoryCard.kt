package com.susu.feature.sent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.badge.BadgeColor
import com.susu.core.designsystem.component.badge.BadgeStyle
import com.susu.core.designsystem.component.badge.SusuBadge
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.button.XSmallButtonStyle
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun EnvelopeHistoryCard(
    modifier: Modifier = Modifier,
    historyCount: Int,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = SusuTheme.spacing.spacing_xxs),
        colors = CardDefaults.cardColors(
            containerColor = Gray10,
        ),
        shape = RoundedCornerShape(4.dp),
    ) {
        Column(
            modifier = modifier
                .padding(
                    top = SusuTheme.spacing.spacing_xl,
                    bottom = SusuTheme.spacing.spacing_m,
                    start = SusuTheme.spacing.spacing_m,
                    end = SusuTheme.spacing.spacing_m,
                ),
        ) {
            // 내역
            for (i in 0 until historyCount) {
                EnvelopeDetailItem(isSent = true)
                Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_xxs))
            }

            // 전체보기
            Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_xxs))
            SusuFilledButton(
                color = FilledButtonColor.Black,
                style = XSmallButtonStyle.height44,
                text = stringResource(R.string.envelope_show_history_all),
                modifier = modifier
                    .fillMaxWidth(),
            )
        }
    }
}

@Composable
fun EnvelopeDetailItem(
    modifier: Modifier = Modifier,
    isSent: Boolean = true,
) {
    Row(
        modifier = modifier.fillMaxWidth().background(Gray10),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = if (isSent) {
                painterResource(id = R.drawable.ic_round_arrow_sent)
            } else {
                painterResource(id = R.drawable.ic_round_arrow_received)
            },
            contentDescription = stringResource(R.string.envelope_hisotry_arrow),
            tint = if (isSent) Orange60 else Gray40,
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
