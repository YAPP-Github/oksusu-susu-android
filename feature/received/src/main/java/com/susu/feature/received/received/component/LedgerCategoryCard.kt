package com.susu.feature.received.received.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.feature.received.R

@Composable
fun LedgerCategoryCard(
    name: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = Gray40,
        )

        Text(
            text = stringResource(R.string.ledger_category_card_event_category),
            style = SusuTheme.typography.title_xxxs,
            color = Gray40,
        )

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(4.dp)
                .background(Gray40),
        )

        Text(
            text = name,
            style = SusuTheme.typography.title_xxxs,
            color = Gray40,
        )

        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = Gray40,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LedgerCategoryCardPreview() {
    SusuTheme {
        LedgerCategoryCard(
            name = "결혼식",
        )
    }
}
