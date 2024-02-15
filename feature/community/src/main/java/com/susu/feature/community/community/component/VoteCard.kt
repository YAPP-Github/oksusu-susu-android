package com.susu.feature.community.community.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.component.button.XSmallButtonStyle
import com.susu.core.designsystem.theme.Blue60
import com.susu.core.designsystem.theme.Gray15
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Vote
import com.susu.core.ui.extension.susuClickable
import com.susu.core.ui.util.to_yyyy_dot_MM_dot_dd
import com.susu.feature.community.R
import kotlinx.datetime.toJavaLocalDateTime
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Composable
fun VoteCard(
    vote: Vote = Vote(),
    currentTime: LocalDateTime = LocalDateTime.now(),
    onClick: () -> Unit = {},
    onClickReport: (Vote) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(
                start = SusuTheme.spacing.spacing_m,
                end = SusuTheme.spacing.spacing_m,
                bottom = SusuTheme.spacing.spacing_xxs,
            )
            .fillMaxWidth()
            .susuClickable(
                rippleEnabled = false,
                onClick = onClick,
            )
            .clip(RoundedCornerShape(8.dp))
            .background(Gray15)
            .padding(SusuTheme.spacing.spacing_m),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = vote.boardName, color = Orange60, style = SusuTheme.typography.title_xxxs)
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = com.susu.core.ui.R.drawable.ic_arrow_right),
                    contentDescription = null,
                    tint = Orange60,
                )
            }

            val totalMinutes = ChronoUnit.MINUTES.between(vote.createdAt.toJavaLocalDateTime(), currentTime)

            val writeTime = when {
                totalMinutes / 60 > 24 -> vote.createdAt.toJavaLocalDateTime().to_yyyy_dot_MM_dot_dd()
                totalMinutes / 60 > 0 -> stringResource(R.string.word_before_hour, totalMinutes / 60)
                totalMinutes / 60 == 0L -> stringResource(R.string.word_before_minute, totalMinutes % 60 + 1)
                else -> vote.createdAt.toJavaLocalDateTime().to_yyyy_dot_MM_dot_dd()
            }

            Text(
                text = writeTime,
                style = SusuTheme.typography.text_xxxs,
                color = Gray40,
            )
        }

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxs))

        Text(
            text = vote.content,
            style = SusuTheme.typography.text_xxxs,
        )

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_s))

        Column(
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxxxs),
        ) {
            vote.optionList.forEach { option ->
                SusuGhostButton(
                    textModifier = Modifier.weight(1f),
                    text = option.content,
                    color = GhostButtonColor.Black,
                    style = XSmallButtonStyle.height44,
                    isClickable = false,
                )
            }
        }

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_s))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.vote_card_count, vote.count),
                style = SusuTheme.typography.title_xxxs,
                color = Blue60,
            )

            if (vote.isMine.not()) {
                Image(
                    modifier = Modifier.clip(CircleShape).susuClickable(onClick = { onClickReport(vote) }),
                    painter = painterResource(id = R.drawable.ic_report),
                    contentDescription = stringResource(com.susu.core.ui.R.string.content_description_report_button),
                )
            }
        }
    }
}

@Preview
@Composable
fun VoteCardPreview() {
    SusuTheme {
        VoteCard()
    }
}
