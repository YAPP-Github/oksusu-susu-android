package com.susu.feature.received.ledgerfilter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.LinedButtonColor
import com.susu.core.designsystem.component.button.RefreshButton
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.button.SusuLinedButton
import com.susu.core.designsystem.component.button.XSmallButtonStyle
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.received.R
import com.susu.feature.received.ledgerfilter.component.DateText

@Composable
fun LedgerFilterRoute(
    @Suppress("unused")
    popBackStack: () -> Unit,
) {
    LedgerFilterScreen(
        onClickBackIcon = popBackStack,
    )
}

@Composable
fun LedgerFilterScreen(
    onClickBackIcon: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .background(SusuTheme.colorScheme.background10)
            .fillMaxSize(),
    ) {
        SusuDefaultAppBar(
            leftIcon = {
                BackIcon(onClickBackIcon)
            },
            title = stringResource(id = com.susu.core.ui.R.string.word_filter),
        )

        Column(
            modifier = Modifier.padding(
                top = SusuTheme.spacing.spacing_xl,
                start = SusuTheme.spacing.spacing_m,
                end = SusuTheme.spacing.spacing_m,
                bottom = SusuTheme.spacing.spacing_xxs,
            ),
        ) {
            Text(text = stringResource(R.string.ledger_filter_screen_event_category), style = SusuTheme.typography.title_xs)
            Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))
            Row(
                horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
            ) {
                SusuLinedButton(
                    color = LinedButtonColor.Black,
                    style = XSmallButtonStyle.height28,
                    isActive = false,
                    text = "결혼식",
                )

                SusuLinedButton(
                    color = LinedButtonColor.Black,
                    style = XSmallButtonStyle.height28,
                    isActive = false,
                    text = "돌잔치",
                )

                SusuLinedButton(
                    color = LinedButtonColor.Black,
                    style = XSmallButtonStyle.height28,
                    isActive = false,
                    text = "장례식",
                )

                SusuLinedButton(
                    color = LinedButtonColor.Black,
                    style = XSmallButtonStyle.height28,
                    isActive = true,
                    text = "생일 기념일",
                )
            }

            Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxxxxxl))
            Text(
                text = stringResource(id = com.susu.core.ui.R.string.word_date),
                style = SusuTheme.typography.title_xs,
            )
            Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                DateText()

                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxxxs))

                Text(
                    text = stringResource(R.string.ledger_filter_screen_from),
                    style = SusuTheme.typography.title_xxs,
                )

                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))

                DateText()

                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxxxs))

                Text(
                    text = stringResource(R.string.ledger_filter_screen_until),
                    style = SusuTheme.typography.title_xxs,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs)
            ) {
                SusuFilledButton(
                    color = FilledButtonColor.Orange,
                    style = XSmallButtonStyle.height28,
                    text = "결혼식",
                    isClickable = false,
                    rightIcon = {
                        Icon(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(12.dp)
                                .susuClickable { /* TODO */ },
                            painter = painterResource(id = com.susu.core.ui.R.drawable.ic_close),
                            contentDescription = stringResource(id = com.susu.core.ui.R.string.content_description_close_icon),
                            tint = Gray10,
                        )
                    },
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_m)
                ) {
                    RefreshButton()

                    SusuFilledButton(
                        modifier = Modifier.fillMaxWidth(),
                        color = FilledButtonColor.Black,
                        style = SmallButtonStyle.height48,
                        isActive = true,
                        text = stringResource(com.susu.core.ui.R.string.word_apply_filter),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LedgerFilterScreenPreview() {
    SusuTheme {
        LedgerFilterScreen()
    }
}
