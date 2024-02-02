package com.susu.feature.community.votedetail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.theme.Gray70
import com.susu.core.designsystem.theme.Orange10
import com.susu.core.designsystem.theme.Orange40
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.community.R
import kotlin.math.roundToInt

private fun Int.safeDiv(parent: Int): Float {
    val result = toFloat() / parent.toFloat()
    return if (result.isNaN()) 0f else result
}

@Composable
fun VoteItem(
    showResult: Boolean = true,
    isPick: Boolean = false,
    voteCount: Long = 0,
    totalVoteCount: Long = 0,
    title: String = "",
    onClick: () -> Unit = {},
) {
    val percent = voteCount.toInt().safeDiv(totalVoteCount.toInt())

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Orange10)
            .fillMaxWidth()
            .height(48.dp)
            .susuClickable(onClick = onClick),
    ) {
        if (showResult) {
            Box(
                modifier = Modifier
                    .background(if (isPick) Orange60 else Orange40)
                    .fillMaxWidth(percent)
                    .height(48.dp),
            )
        }

        Row(
            modifier = Modifier.padding(
                vertical = SusuTheme.spacing.spacing_s,
                horizontal = SusuTheme.spacing.spacing_m,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isPick) {
                Image(
                    painter = painterResource(id = R.drawable.ic_vote_check),
                    contentDescription = null,
                )

                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxxxs))
            }

            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = SusuTheme.typography.title_xxs,
            )

            if (showResult) {
                Text(
                    text = stringResource(R.string.word_people, voteCount),
                    style = SusuTheme.typography.text_xxxs,
                    color = Gray70,
                )
                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxs))
                Text(
                    text = "${(percent * 100).roundToInt()}%",
                    style = SusuTheme.typography.title_xxs,
                )
            }
        }
    }
}

@Preview
@Composable
fun VoteItemPreview() {
    SusuTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            VoteItem(
                isPick = false,
                showResult = false,
                title = "3만원",
            )
            VoteItem(isPick = true, title = "3만원")
            VoteItem(title = "3만원")
        }
    }
}
