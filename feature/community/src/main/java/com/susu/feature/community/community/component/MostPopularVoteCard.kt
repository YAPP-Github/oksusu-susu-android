package com.susu.feature.community.community.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.component.button.XSmallButtonStyle
import com.susu.core.designsystem.theme.Gray15
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Vote
import com.susu.core.ui.extension.susuClickable
import com.susu.core.ui.extension.toMoneyFormat
import com.susu.feature.community.R

@Composable
fun MostPopularVoteCard(vote: Vote, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Gray15)
            .width(296.dp)
            .susuClickable(rippleEnabled = false, onClick = onClick)
            .padding(SusuTheme.spacing.spacing_m),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = vote.boardName, color = Gray60, style = SusuTheme.typography.title_xxxs)
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = com.susu.core.ui.R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = Gray50,
            )
        }

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxs))

        Text(
            text = vote.content,
            style = SusuTheme.typography.text_xxxs,
        )

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xs))

        SusuGhostButton(
            textModifier = Modifier.weight(1f),
            text = stringResource(R.string.popular_vote_card_count, vote.count.toMoneyFormat()),
            textAlign = TextAlign.Center,
            color = GhostButtonColor.Black,
            style = XSmallButtonStyle.height44,
            isClickable = false,
            leftIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_vote),
                    contentDescription = null,
                )
            },
        )
    }
}

@Preview
@Composable
fun MostPopularVoteCardPreview() {
    SusuTheme {
        MostPopularVoteCard(vote = Vote())
    }
}
