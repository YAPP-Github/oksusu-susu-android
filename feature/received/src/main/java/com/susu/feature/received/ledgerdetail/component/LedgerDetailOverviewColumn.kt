package com.susu.feature.received.ledgerdetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.component.badge.BadgeColor
import com.susu.core.designsystem.component.badge.BadgeStyle
import com.susu.core.designsystem.component.badge.SusuBadge
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.Gray80
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.R
import com.susu.core.ui.extension.toMoneyFormat

@Composable
fun LedgerDetailOverviewColumn(
    money: Long,
    count: Int,
    eventCategory: String,
    eventName: String,
    eventRange: String,
) {
    Column(
        modifier = Modifier.padding(horizontal = SusuTheme.spacing.spacing_m),
    ) {
        Text(
            text = stringResource(R.string.word_total_money, money.toMoneyFormat()),
            style = SusuTheme.typography.title_m,
        )

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxs))

        SusuBadge(
            color = BadgeColor.Gray30,
            text = stringResource(R.string.word_total_count, count),
            padding = BadgeStyle.smallBadge,
        )

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))

        Column(
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxxxs),
        ) {
            LedgerDetailOverviewContainer(
                name = stringResource(R.string.word_event_category),
                value = eventCategory, // TODO UiState로 변환
            )

            LedgerDetailOverviewContainer(
                name = stringResource(R.string.word_event_name),
                value = eventName, // TODO UiState로 변환
            )

            LedgerDetailOverviewContainer(
                name = stringResource(R.string.word_event_range),
                value = eventRange, // TODO UiState로 변환
            )
        }
    }
}

@Composable
private fun LedgerDetailOverviewContainer(
    name: String,
    value: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = name,
            style = SusuTheme.typography.title_xxxs,
            color = Gray60,
        )
        Text(
            text = value,
            style = SusuTheme.typography.title_xxxs,
            color = Gray80,
        )
    }
}

@Preview
@Composable
fun LedgerDetailOverviewPreview() {
    SusuTheme {
        LedgerDetailOverviewColumn(
            money = 0,
            count = 0,
            eventCategory = "장례식",
            eventName = "고모부 장례식",
            eventRange = "2023.05.12 - 2023.05.15",
        )
    }
}
