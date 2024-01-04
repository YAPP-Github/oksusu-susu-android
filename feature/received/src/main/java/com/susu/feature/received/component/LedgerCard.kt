package com.susu.feature.received.component

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
import com.susu.feature.received.R
import java.text.DecimalFormat

fun Int.toCurrencyFormat(): String {
    // DecimalFormat은 Thread Safe하지 않으므로 지역 변수로 사용함.
    return DecimalFormat("#,###").format(this)
}

@Composable
fun LedgerCard(
    modifier: Modifier = Modifier,
    ledgerType: String, // TODO LedgerType에 따라 Badger 색상 변경 필요
    title: String,
    currency: Int,
    count: Int,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .aspectRatio(1f)
            .background(Gray10)
            .susuClickable(onClick = onClick)
            .padding(SusuTheme.spacing.spacing_m),
    ) {
        SusuBadge(
            color = BadgeColor.Orange60,
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
            text = stringResource(R.string.ledger_card_total_money, currency.toCurrencyFormat()),
            style = SusuTheme.typography.title_xxxs,
            color = Gray70,
        )

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxxxs))

        Text(
            text = stringResource(R.string.ledger_card_total_count, count),
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
            currency = 4335000,
            count = 164,
        )
    }
}
