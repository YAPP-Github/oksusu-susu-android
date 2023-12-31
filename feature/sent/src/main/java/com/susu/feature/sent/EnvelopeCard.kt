package com.susu.feature.sent

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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

@Composable
fun EnvelopeCard(
    modifier: Modifier = Modifier,
) {
    // TODO: 수정 필요
    var expanded by remember { mutableStateOf(false) }
    val degrees by animateFloatAsState(if (expanded) 180f else 0f, label = "")
    val historyCount = 3

    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .background(SusuTheme.colorScheme.background10),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_envelope),
            contentDescription = stringResource(R.string.envelope_card),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = modifier
                .padding(SusuTheme.spacing.spacing_m),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "김철수",
                    style = SusuTheme.typography.title_xs,
                    color = Gray100,
                )
                Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_s))
                SusuBadge(
                    color = BadgeColor.Gray20,
                    text = "전체 100,000원",
                    padding = BadgeStyle.smallBadge,
                )
                Spacer(modifier = modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_down),
                    contentDescription = stringResource(R.string.envelope_show_detail),
                    tint = Gray100,
                    modifier = modifier
                        .susuClickable {
                            expanded = !expanded
                        }
                        .rotate(degrees = degrees),
                )
            }
            Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_m))
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(R.string.envelope_sent),
                    style = SusuTheme.typography.title_xxxs,
                    color = Gray90,
                )
                Text(
                    text = stringResource(R.string.envelope_received),
                    style = SusuTheme.typography.title_xxxs,
                    color = Gray60,
                )
            }
            LinearProgressIndicator(
                progress = { 0.7f },
                color = SusuTheme.colorScheme.primary,
                trackColor = Orange20,
                strokeCap = StrokeCap.Round,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = SusuTheme.spacing.spacing_xxxxs),
            )
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "70,000원",
                    style = SusuTheme.typography.title_xxxs,
                    color = Gray90,
                )
                Text(
                    text = "30,000원",
                    style = SusuTheme.typography.title_xxxs,
                    color = Gray60,
                )
            }
        }
    }
    AnimatedVisibility(
        visible = expanded,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(),
    ) {
        EnvelopeHistoryCard(historyCount = historyCount)
    }
}
