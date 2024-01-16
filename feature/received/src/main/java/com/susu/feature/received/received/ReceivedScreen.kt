package com.susu.feature.received.received

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.LogoIcon
import com.susu.core.designsystem.component.appbar.icon.NotificationIcon
import com.susu.core.designsystem.component.appbar.icon.SearchIcon
import com.susu.core.designsystem.component.bottomsheet.SusuSelectionBottomSheet
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFloatingButton
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Ledger
import com.susu.core.ui.alignList
import com.susu.core.ui.extension.OnBottomReached
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.received.R
import com.susu.feature.received.received.component.LedgerAddCard
import com.susu.feature.received.received.component.LedgerCard
import kotlinx.collections.immutable.toImmutableList

@Composable
fun ReceivedRoute(
    viewModel: ReceivedViewModel = hiltViewModel(),
    ledger: String?,
    toDeleteLedgerId: Int,
    padding: PaddingValues,
    navigateLedgerDetail: (Ledger) -> Unit,
    navigateLedgerSearch: () -> Unit,
    navigateLedgerFilter: () -> Unit,
    navigateLedgerAdd: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val ledgerListState = rememberLazyGridState()
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            ReceivedEffect.NavigateLedgerAdd -> navigateLedgerAdd()
            is ReceivedEffect.NavigateLedgerDetail -> navigateLedgerDetail(sideEffect.ledger)
            ReceivedEffect.NavigateLedgerFilter -> navigateLedgerFilter()
            ReceivedEffect.NavigateLedgerSearch -> navigateLedgerSearch()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.updateLedgerIfNeed(ledger = ledger, toDeleteLedgerId = toDeleteLedgerId)
    }

    ledgerListState.OnBottomReached {
        viewModel.getLedgerList()
    }

    ReceiveScreen(
        uiState = uiState,
        ledgerListState = ledgerListState,
        padding = padding,
        onClickLedgerCard = viewModel::navigateLedgerDetail,
        onClickLedgerAddCard = viewModel::navigateLedgerAdd,
        onClickSearchIcon = viewModel::navigateLedgerSearch,
        onClickFloatingAddButton = viewModel::navigateLedgerAdd,
        onClickFilterButton = viewModel::navigateLedgerFilter,
        onClickAlignButton = viewModel::showAlignBottomSheet,
        onDismissAlignBottomSheet = viewModel::hideAlignBottomSheet,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiveScreen(
    uiState: ReceivedState = ReceivedState(),
    ledgerListState: LazyGridState = rememberLazyGridState(),
    padding: PaddingValues,
    onClickSearchIcon: () -> Unit = {},
    onClickNotificationIcon: () -> Unit = {},
    onClickAlignButton: () -> Unit = {},
    onClickFilterButton: () -> Unit = {},
    onClickLedgerAddCard: () -> Unit = {},
    onClickLedgerCard: (Ledger) -> Unit = {},
    onClickFloatingAddButton: () -> Unit = {},
    onDismissAlignBottomSheet: () -> Unit = {},
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
                    LogoIcon()
                },
                title = stringResource(R.string.received_screen_appbar_title),
                actions = {
                    Row {
                        SearchIcon(onClickSearchIcon)
                        NotificationIcon(onClickNotificationIcon)
                    }
                },
            )

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize(),
                state = ledgerListState,
                contentPadding = PaddingValues(
                    SusuTheme.spacing.spacing_m,
                ),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
            ) {
                item(
                    span = { GridItemSpan(2) },
                ) {
                    Row(
                        modifier = Modifier.padding(bottom = SusuTheme.spacing.spacing_xxs),
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
                            text = stringResource(com.susu.core.ui.R.string.word_filter),
                            leftIcon = {
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    painter = painterResource(id = com.susu.core.ui.R.drawable.ic_filter),
                                    contentDescription = stringResource(R.string.content_description_filter_icon),
                                )
                            },
                            onClick = onClickFilterButton,
                        )
                    }
                }

                items(
                    items = uiState.ledgerList,
                    key = { it.id },
                ) { ledger ->
                    LedgerCard(
                        ledgerType = ledger.category.name,
                        title = ledger.title,
                        money = ledger.totalAmounts,
                        count = ledger.totalCounts,
                        onClick = { onClickLedgerCard(ledger) },
                    )
                }

                item {
                    LedgerAddCard(
                        onClick = onClickLedgerAddCard,
                    )
                }
            }
        }

        if (uiState.showEmptyLedger) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.received_screen_empty_ledger),
                style = SusuTheme.typography.text_s,
                color = Gray50,
            )
        }

        SusuFloatingButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(SusuTheme.spacing.spacing_l),
            onClick = onClickFloatingAddButton,
        )

        if (uiState.showAlignBottomSheet) {
            SusuSelectionBottomSheet(
                onDismissRequest = onDismissAlignBottomSheet,
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
