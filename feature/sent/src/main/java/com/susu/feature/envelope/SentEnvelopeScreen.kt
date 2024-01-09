package com.susu.feature.envelope

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.badge.BadgeColor
import com.susu.core.designsystem.component.badge.BadgeStyle
import com.susu.core.designsystem.component.badge.SusuBadge
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray20
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.Gray90
import com.susu.core.designsystem.theme.Orange20
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.envelope.component.EnvelopeHistoryItem
import com.susu.feature.sent.R

@Composable
fun SentEnvelopeScreen(
    padding: PaddingValues,
    modifier: Modifier = Modifier,
    name: String,
    clickPadding: Dp = SusuTheme.spacing.spacing_xs,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(padding)
            .background(SusuTheme.colorScheme.background10),
    ) {
        Column {
            SusuDefaultAppBar(
                leftIcon = {
                    Icon(
                        painter = painterResource(id = com.susu.core.designsystem.R.drawable.ic_arrow_left),
                        contentDescription = stringResource(id = com.susu.core.designsystem.R.string.content_description_back_icon),
                        modifier = modifier
                            .susuClickable(
                                rippleEnabled = false,
                                onClick = {},
                            )
                            .padding(clickPadding),
                    )
                },
                title = name,
                actions = {
                    Icon(
                        painter = painterResource(id = com.susu.core.designsystem.R.drawable.ic_appbar_search),
                        contentDescription = stringResource(id = com.susu.core.designsystem.R.string.content_description_search_icon),
                        modifier = modifier
                            .susuClickable(
                                rippleEnabled = false,
                                onClick = {},
                            )
                            .padding(clickPadding),
                    )
                    Icon(
                        painter = painterResource(id = com.susu.core.designsystem.R.drawable.ic_appbar_notification),
                        contentDescription = stringResource(id = com.susu.core.designsystem.R.string.content_description_notification_icon),
                        modifier = modifier
                            .susuClickable(
                                rippleEnabled = false,
                                onClick = {},
                            )
                            .padding(clickPadding),
                    )
                },
            )

            // TODO: text 변경하기
            Column(
                modifier = modifier
                    .padding(
                        horizontal = SusuTheme.spacing.spacing_m,
                        vertical = SusuTheme.spacing.spacing_xl,
                    ),
            ) {
                Text(
                    text = "전체 100,000원",
                    style = SusuTheme.typography.title_m,
                    color = Gray100,
                )
                Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_xxs))
                SusuBadge(
                    color = BadgeColor.Gray30,
                    text = "-40,000원",
                    padding = BadgeStyle.smallBadge,
                )
                Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_xl))
                Row(
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(id = R.string.sent_screen_envelope_sent),
                        style = SusuTheme.typography.title_xxxs,
                        color = Gray90,
                    )
                    Text(
                        text = stringResource(id = R.string.sent_screen_envelope_received),
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
                    modifier = modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "70,000원",
                        style = SusuTheme.typography.title_xxxxs,
                        color = Gray90,
                    )
                    Text(
                        text = "30,000원",
                        style = SusuTheme.typography.title_xxxxs,
                        color = Gray60,
                    )
                }
            }
            HorizontalDivider(
                thickness = 8.dp,
                color = Gray20,
            )

            LazyColumn(
                contentPadding = PaddingValues(vertical = SusuTheme.spacing.spacing_m),
                verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_m),
            ) {
                repeat(10) {
                    item {
                        EnvelopeHistoryItem()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SentEnvelopeScreenPreview() {
    SusuTheme {
        SentEnvelopeScreen(
            padding = PaddingValues(0.dp),
            name = "김철수",
        )
    }
}
