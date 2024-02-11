package com.susu.feature.sent.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.badge.BadgeColor
import com.susu.core.designsystem.component.badge.BadgeStyle
import com.susu.core.designsystem.component.badge.SusuBadge
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.Gray90
import com.susu.core.designsystem.theme.Orange20
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable
import com.susu.core.ui.extension.toMoneyFormat
import com.susu.feature.sent.FriendStatisticsState
import com.susu.feature.sent.R

@Composable
fun SentCard(
    state: FriendStatisticsState = FriendStatisticsState(),
    onClickHistory: (Boolean, Long) -> Unit = { _, _ -> },
    onClickHistoryShowAll: (Long) -> Unit = {},
) {
    val degrees by animateFloatAsState(if (state.expand) 180f else 0f, label = "")
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .background(SusuTheme.colorScheme.background10),
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_envelope),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth(),
        )
        Column(
            modifier = Modifier
                .padding(SusuTheme.spacing.spacing_m),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = if (state.friend.name.length >= 10) "${state.friend.name.take(10)}..." else state.friend.name,
                    style = SusuTheme.typography.title_xs,
                    color = Gray100,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_s))
                SusuBadge(
                    color = BadgeColor.Gray20,
                    text = stringResource(R.string.sent_envelope_card_monee_total) + state.totalAmounts.toMoneyFormat() +
                        stringResource(R.string.sent_envelope_card_money_won),
                    padding = BadgeStyle.smallBadge,
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = stringResource(R.string.content_description_envelope_show_history),
                    tint = Gray100,
                    modifier = Modifier
                        .clip(CircleShape)
                        .susuClickable(
                            onClick = {
                                onClickHistory(state.expand, state.friend.id)
                            },
                        )
                        .rotate(degrees = degrees),
                )
            }
            Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(R.string.sent_screen_envelope_sent),
                    style = SusuTheme.typography.title_xxxs,
                    color = Gray90,
                )
                Text(
                    text = stringResource(R.string.sent_screen_envelope_received),
                    style = SusuTheme.typography.title_xxxs,
                    color = Gray60,
                )
            }
            LinearProgressIndicator(
                progress = { state.sentAmounts.toFloat() / state.totalAmounts },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = SusuTheme.spacing.spacing_xxxxs),
                color = SusuTheme.colorScheme.primary,
                trackColor = Orange20,
                strokeCap = StrokeCap.Round,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = state.sentAmounts.toMoneyFormat() + stringResource(R.string.sent_envelope_card_money_won),
                    style = SusuTheme.typography.title_xxxs,
                    color = Gray90,
                )
                Text(
                    text = state.receivedAmounts.toMoneyFormat() + stringResource(R.string.sent_envelope_card_money_won),
                    style = SusuTheme.typography.title_xxxs,
                    color = Gray60,
                )
            }
        }
    }
    AnimatedVisibility(
        visible = state.expand,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(),
    ) {
        SentHistoryCard(
            envelopeHistoryList = state.envelopesHistoryList,
            friendId = state.friend.id,
            onClickHistoryShowAll = onClickHistoryShowAll,
        )
    }
}
