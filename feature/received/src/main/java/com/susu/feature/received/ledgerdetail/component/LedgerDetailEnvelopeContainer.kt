package com.susu.feature.received.ledgerdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.badge.BadgeColor
import com.susu.core.designsystem.component.badge.BadgeStyle
import com.susu.core.designsystem.component.badge.SusuBadge
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.SearchEnvelope
import com.susu.core.ui.R
import com.susu.core.ui.extension.susuClickable
import com.susu.core.ui.extension.toMoneyFormat

@Composable
fun LedgerDetailEnvelopeContainer(
    envelope: SearchEnvelope = SearchEnvelope(),
    onClickSeeMoreIcon: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(
                start = SusuTheme.spacing.spacing_m,
                end = SusuTheme.spacing.spacing_m,
                top = SusuTheme.spacing.spacing_m,
            )
            .clip(RoundedCornerShape(4.dp))
            .background(Gray10)
            .fillMaxWidth()
            .height(100.dp)
            .susuClickable(onClick = onClickSeeMoreIcon)
            .padding(
                start = SusuTheme.spacing.spacing_m,
                end = SusuTheme.spacing.spacing_m,
                top = SusuTheme.spacing.spacing_m,
            ),
        verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_s),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        ) {
            SusuBadge(
                color = BadgeColor.Orange60,
                text = envelope.relation.customRelation ?: envelope.relation.relation,
                padding = BadgeStyle.smallBadge,
            )

            envelope.envelope.hasVisited?.let {
                SusuBadge(
                    color = BadgeColor.Blue60,
                    text = if (it) stringResource(R.string.word_visited) else stringResource(R.string.word_not_visited),
                    padding = BadgeStyle.smallBadge,
                )
            }

            envelope.envelope.gift?.let {
                SusuBadge(
                    color = BadgeColor.Gray90,
                    text = it,
                    padding = BadgeStyle.smallBadge,
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = envelope.friend.name,
                style = SusuTheme.typography.title_xs,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, false),
            )
            Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_s))
            Text(
                text = stringResource(id = R.string.money_unit_format, envelope.envelope.amount.toInt().toMoneyFormat()),
                style = SusuTheme.typography.title_m,
            )
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .susuClickable(onClick = onClickSeeMoreIcon),
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = stringResource(R.string.content_description_see_more_icon),
                tint = Gray50,
            )
        }
    }
}

@Preview
@Composable
fun LedgerDetailEnvelopeContainerPreview() {
    SusuTheme {
        LedgerDetailEnvelopeContainer {
        }
    }
}
