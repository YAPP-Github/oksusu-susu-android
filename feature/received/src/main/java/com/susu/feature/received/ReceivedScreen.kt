package com.susu.feature.received

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.bottomsheet.SusuSelectionBottomSheet
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFloatingButton
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.alignList
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.received.component.LedgerAddCard
import com.susu.feature.received.component.LedgerCard
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun ReceivedRoute(
    padding: PaddingValues,
) {
    ReceiveScreen(padding = padding)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiveScreen(
    padding: PaddingValues,
    onClickSearchIcon: () -> Unit = {},
    onClickNotificationIcon: () -> Unit = {},
    onClickAlignButton: () -> Unit = {},
    onClickFilterButton: () -> Unit = {},
    onClickLedgerAddCard: () -> Unit = {},
    onClickLedgerCard: () -> Unit = {},
    onClickFloatingAddButton: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .background(SusuTheme.colorScheme.background15)
            .padding(padding)
            .fillMaxSize(),
    ) {
        Column {
            SusuDefaultAppBar(
                modifier = Modifier.padding(horizontal = SusuTheme.spacing.spacing_xs),
                leftIcon = {
                    Image(
                        modifier = Modifier
                            .width(56.dp)
                            .height(24.dp),
                        painter = painterResource(id = com.susu.core.ui.R.drawable.ic_app_bar_logo),
                        contentDescription = stringResource(R.string.content_description_logo_image),
                    )
                },
                title = stringResource(R.string.received_screen_appbar_title),
                actions = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_l),
                    ) {
                        Icon(
                            modifier = Modifier
                                .susuClickable(
                                    rippleEnabled = false,
                                    onClick = onClickSearchIcon,
                                ),
                            painter = painterResource(id = com.susu.core.ui.R.drawable.ic_appbar_search),
                            contentDescription = stringResource(R.string.content_description_search_icon),
                        )

                        Icon(
                            modifier = Modifier
                                .susuClickable(
                                    rippleEnabled = false,
                                    onClick = onClickNotificationIcon,
                                ),
                            painter = painterResource(id = com.susu.core.ui.R.drawable.ic_appbar_notification),
                            contentDescription = stringResource(R.string.content_description_notification_icon),
                        )
                    }
                },
            )

            Column(
                modifier = Modifier.padding(SusuTheme.spacing.spacing_m),
                verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_m),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                ) {
                    SusuGhostButton(
                        color = GhostButtonColor.Black,
                        style = SmallButtonStyle.height32,
                        text = alignList[0], // TODO State 변환
                        leftIcon = {
                            Icon(
                                painter = painterResource(id = com.susu.core.ui.R.drawable.ic_align),
                                contentDescription = stringResource(R.string.content_description_align_icon),
                            )
                        },
                        onClick = onClickAlignButton,
                    )
                    SusuGhostButton(
                        color = GhostButtonColor.Black,
                        style = SmallButtonStyle.height32,
                        text = stringResource(R.string.word_filter),
                        leftIcon = {
                            Icon(
                                painter = painterResource(id = com.susu.core.ui.R.drawable.ic_filter),
                                contentDescription = stringResource(R.string.content_description_filter_icon),
                            )
                        },
                        onClick = onClickFilterButton,
                    )
                }

                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                    horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                ) {
                    item {
                        LedgerCard(
                            ledgerType = "결혼식",
                            title = "나의 결혼식",
                            currency = 4335000,
                            count = 164,
                            onClick = onClickLedgerCard,
                        )
                    }

                    item {
                        LedgerCard(
                            ledgerType = "결혼식",
                            title = "나의 결혼식",
                            currency = 4335000,
                            count = 164,
                            onClick = onClickLedgerCard,
                        )
                    }

                    item {
                        LedgerAddCard(
                            onClick = onClickLedgerAddCard,
                        )
                    }
                }
            }
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.received_screen_empty_ledger),
            style = SusuTheme.typography.text_s, color = Gray50,
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(SusuTheme.spacing.spacing_l),
        ) {
            SusuFloatingButton(
                onClick = onClickFloatingAddButton,
            )
        }

        // TODO State 변환
        var isSheetOpen by remember {
            mutableStateOf(true)
        }

        if (isSheetOpen) {
            SusuSelectionBottomSheet(
                onDismissRequest = { isSheetOpen = false },
                containerHeight = 250.dp,
                items = alignList.toImmutableList(),
                selectedItemPosition = 0, // TODO State 변환
                onClickItem = {},
            )
        }
    }
}

@Preview
@Composable
fun ReceivedScreenPreview() {
    SusuTheme {
        ReceiveScreen(padding = PaddingValues(0.dp))
    }
}
