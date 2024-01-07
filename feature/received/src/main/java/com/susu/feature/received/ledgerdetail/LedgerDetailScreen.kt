package com.susu.feature.received.ledgerdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.appbar.icon.DeleteText
import com.susu.core.designsystem.component.appbar.icon.EditText
import com.susu.core.designsystem.component.badge.BadgeColor
import com.susu.core.designsystem.component.badge.BadgeStyle
import com.susu.core.designsystem.component.badge.SusuBadge
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFloatingButton
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray25
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.R
import com.susu.core.ui.alignList
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.received.ledgerdetail.component.LedgerDetailEnvelopeContainer
import com.susu.feature.received.ledgerdetail.component.LedgerDetailOverviewColumn

@Composable
fun LedgerDetailRoute(
    viewModel: LedgerDetailViewModel = hiltViewModel(),
) {
    LedgerDetailScreen()
}

@Composable
fun LedgerDetailScreen(
    onClickBack: () -> Unit = {},
    onClickEdit: () -> Unit = {},
    onClickDelete: () -> Unit = {},
    onClickFilterButton: () -> Unit = {},
    onClickAlignButton: () -> Unit = {},
    onClickEnvelopeAddButton: () -> Unit = {},
    onClickFloatingButton: () -> Unit = {},
    onClickSeeMoreIcon: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .background(SusuTheme.colorScheme.background15)
            .fillMaxSize(),
    ) {
        Column {
            SusuDefaultAppBar(
                leftIcon = {
                    BackIcon(onClick = onClickBack)
                },
                actions = {
                    Row(
                        modifier = Modifier.padding(horizontal = SusuTheme.spacing.spacing_m),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_m),
                    ) {
                        EditText(onClickEdit)
                        DeleteText(onClickDelete)
                    }
                },
            )

            LazyColumn(
                contentPadding = PaddingValues(
                    vertical = SusuTheme.spacing.spacing_xl,
                ),
            ) {
                item {
                    LedgerDetailOverviewColumn(
                        money = 0,
                        count = 0,
                        eventCategory = "장례식",
                        eventName = "고모부 장례",
                        eventRange = "2023.05.12 - 2023.05.15",
                    )
                }

                item {
                    Spacer(
                        modifier = Modifier
                            .padding(vertical = SusuTheme.spacing.spacing_m)
                            .fillMaxWidth()
                            .height(8.dp)
                            .background(Gray25),
                    )
                }

                item {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = SusuTheme.spacing.spacing_m,
                        ),
                        horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                    ) {
                        SusuGhostButton(
                            color = GhostButtonColor.Black,
                            style = SmallButtonStyle.height32,
                            text = stringResource(R.string.word_filter),
                            leftIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_filter),
                                    contentDescription = null,
                                )
                            },
                            onClick = onClickFilterButton,
                        )

                        SusuGhostButton(
                            color = GhostButtonColor.Black,
                            style = SmallButtonStyle.height32,
                            text = alignList[0], // TODO State 변환
                            leftIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_align),
                                    contentDescription = null,
                                )
                            },
                            onClick = onClickAlignButton,
                        )
                    }
                }

                var showEmptyScreen = false // TODO Refactor
                if (showEmptyScreen) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(top = 103.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_m),
                        ) {
                            Text(
                                text = stringResource(com.susu.feature.received.R.string.ledger_detail_screen_empty_envelope),
                                style = SusuTheme.typography.text_s,
                                color = Gray50,
                            )
                            SusuGhostButton(
                                color = GhostButtonColor.Black,
                                style = SmallButtonStyle.height40,
                                text = stringResource(com.susu.feature.received.R.string.ledger_detail_screen_add_envelope),
                                onClick = onClickEnvelopeAddButton,
                            )
                        }
                    }
                } else {
                    items(count = 100) {
                        LedgerDetailEnvelopeContainer(
                            onClickSeeMoreIcon = onClickSeeMoreIcon,
                        )
                    }
                }
            }
        }

        SusuFloatingButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(SusuTheme.spacing.spacing_l),
            onClick = onClickFloatingButton,
        )
    }
}

@Preview
@Composable
fun ReceivedScreenPreview() {
    SusuTheme {
        LedgerDetailScreen()
    }
}
