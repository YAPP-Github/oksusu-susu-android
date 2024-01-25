package com.susu.feature.community.community.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.susu.feature.community.R

@Composable
fun MostPopularVoteCard() {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Gray15)
            .size(
                width = 296.dp,
                height = 156.dp,
            )
            .padding(SusuTheme.spacing.spacing_m),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "결혼식", color = Gray60, style = SusuTheme.typography.title_xxxs)
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = com.susu.core.ui.R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = Gray50,
            )
        }

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxs))

        Text(
            text = "고등학교 동창이고 좀 애매하게 친한 사인데 축의금 얼마 내야 돼?",
            style = SusuTheme.typography.text_xxxs,
        )

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xs))

        SusuGhostButton(
            textModifier = Modifier.weight(1f),
            text = "12,430명 참여 중",
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
        MostPopularVoteCard()
    }
}
