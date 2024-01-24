package com.susu.feature.received.received.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.Gray70
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable
import com.susu.core.ui.extension.toMoneyFormat

@Composable
fun LedgerCard(
    modifier: Modifier = Modifier,
    ledgerType: String,
    style: String,
    title: String,
    money: Int,
    count: Int,
    onClick: () -> Unit = {},
) {
    val badgeColor = BadgeColor.safeValueOf(style)

    Column(
        modifier = modifier
            .aspectRatio(1f)
            .background(Gray10)
            .susuClickable(onClick = onClick)
            .padding(SusuTheme.spacing.spacing_m),
    ) {
        SusuBadge(
            color = badgeColor,
            text = ledgerType,
            padding = BadgeStyle.smallBadge,
        )

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxs))

        Text(
            text = title,
            style = SusuTheme.typography.title_m,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(com.susu.core.ui.R.string.word_total_money, money.toMoneyFormat()),
            style = SusuTheme.typography.title_xxxs,
            color = Gray70,
        )

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxxxs))

        Text(
            text = stringResource(com.susu.core.ui.R.string.word_total_count, count),
            style = SusuTheme.typography.title_xxxs,
            color = Gray50,
        )
    }
}

@Preview
@Composable
fun LedgerCardPreview() {
    SusuTheme {
        LedgerCard(
            ledgerType = "결혼식",
            title = "나의 결혼식",
            style = "",
            count = 164,
            money = 10000,
        )
    }
}
