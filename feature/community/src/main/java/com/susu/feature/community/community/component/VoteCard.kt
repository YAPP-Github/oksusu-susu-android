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
import com.susu.feature.community.R

@Composable
fun VoteCard() {
    Column(
        modifier = Modifier
            .padding(
                start = SusuTheme.spacing.spacing_m,
                end = SusuTheme.spacing.spacing_m,
                bottom = SusuTheme.spacing.spacing_xxs,
            )
            .fillMaxWidth()
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
                Text(text = "결혼식", color = Orange60, style = SusuTheme.typography.title_xxxs)
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = com.susu.core.ui.R.drawable.ic_arrow_right),
                    contentDescription = null,
                    tint = Orange60,
                )
            }

            Text(
                text = "10분 전",
                style = SusuTheme.typography.text_xxxs,
                color = Gray40,
            )
        }

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxs))

        Text(
            text = "고등학교 동창이고 좀 애매하게 친한 사인데 축의금 얼마 내야 돼?",
            style = SusuTheme.typography.text_xxxs,
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxxxs),
        ) {
            repeat(5) {
                SusuGhostButton(
                    textModifier = Modifier.weight(1f),
                    text = "${it}만원",
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
                text = "8명 참여",
                style = SusuTheme.typography.title_xxxs,
                color = Blue60,
            )

            Image(
                painter = painterResource(id = R.drawable.ic_report),
                contentDescription = stringResource(com.susu.core.ui.R.string.content_description_report_button),
            )
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
