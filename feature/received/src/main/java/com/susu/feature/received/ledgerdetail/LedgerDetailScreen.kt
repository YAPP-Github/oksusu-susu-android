package com.susu.feature.received.ledgerdetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.appbar.icon.DeleteText
import com.susu.core.designsystem.component.appbar.icon.EditText
import com.susu.core.designsystem.component.bottomsheet.SusuSelectionBottomSheet
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.FilterButton
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.SelectedFilterButton
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFloatingButton
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray25
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Envelope
import com.susu.core.model.Friend
import com.susu.core.model.Ledger
import com.susu.core.ui.DialogToken
import com.susu.core.ui.R
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.OnBottomReached
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.toMoneyFormat
import com.susu.feature.received.ledgerdetail.component.LedgerDetailEnvelopeContainer
import com.susu.feature.received.ledgerdetail.component.LedgerDetailOverviewColumn
import kotlinx.collections.immutable.toPersistentList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LedgerDetailRoute(
    viewModel: LedgerDetailViewModel = hiltViewModel(),
    envelope: String?,
    envelopeFilter: String?,
    navigateEnvelopeFilter: (String) -> Unit,
    toDeleteEnvelopeId: Long?,
    navigateLedgerEdit: (Ledger) -> Unit,
    navigateEnvelopAdd: (Ledger) -> Unit,
    navigateEnvelopeDetail: (Envelope, Ledger) -> Unit,
    popBackStackWithLedger: (String) -> Unit,
    popBackStackWithDeleteLedgerId: (Long) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
    onShowDialog: (DialogToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val refreshState = rememberPullToRefreshState(
        positionalThreshold = 100.dp
    )
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is LedgerDetailSideEffect.NavigateLedgerEdit -> navigateLedgerEdit(sideEffect.ledger)
            is LedgerDetailSideEffect.PopBackStackWithLedger -> popBackStackWithLedger(sideEffect.ledger)
            is LedgerDetailSideEffect.ShowDeleteDialog -> {
                onShowDialog(
                    DialogToken(
                        title = context.getString(com.susu.feature.received.R.string.ledger_detail_screen_dialog_title),
                        text = context.getString(com.susu.feature.received.R.string.ledger_detail_screen_dialog_description),
                        confirmText = context.getString(R.string.word_delete),
                        dismissText = context.getString(R.string.word_cancel),
                        onConfirmRequest = sideEffect.onConfirmRequest,
                    ),
                )
            }

            LedgerDetailSideEffect.ShowDeleteSuccessSnackbar -> {
                onShowSnackbar(
                    SnackbarToken(
                        message = context.getString(com.susu.feature.received.R.string.ledger_detail_screen_snackbar_message),
                    ),
                )
            }

            is LedgerDetailSideEffect.PopBackStackWithDeleteLedgerId -> popBackStackWithDeleteLedgerId(sideEffect.ledgerId)
            is LedgerDetailSideEffect.HandleException -> handleException(sideEffect.throwable, sideEffect.retry)
            is LedgerDetailSideEffect.ShowSnackbar -> onShowSnackbar(SnackbarToken(message = sideEffect.msg))
            is LedgerDetailSideEffect.NavigateEnvelopeFilter -> navigateEnvelopeFilter(sideEffect.filter)
            is LedgerDetailSideEffect.NavigateEnvelopeAdd -> navigateEnvelopAdd(sideEffect.ledger)
            is LedgerDetailSideEffect.NavigateEnvelopeDetail -> navigateEnvelopeDetail(sideEffect.envelope, sideEffect.ledger)
        }
    }

    LaunchedEffect(key1 = refreshState.isRefreshing) {
        if (refreshState.isRefreshing.not()) return@LaunchedEffect

        viewModel.refreshData {
            refreshState.endRefresh()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getLedger()
        viewModel.initReceivedEnvelopeList()
        viewModel.addEnvelopeIfNeed(envelope)
        viewModel.deleteEnvelopeIfNeed(toDeleteEnvelopeId)
        viewModel.updateEnvelopeIfNeed(envelope)
        viewModel.filterIfNeed(envelopeFilter)
    }

    listState.OnBottomReached(minItemsCount = 4) {
        viewModel.getReceivedEnvelopeList()
    }

    listState.OnBottomReached(minItemsCount = 4) {
        viewModel.getReceivedEnvelopeList()
    }

    BackHandler(onBack = viewModel::popBackStackWithLedger)

    LedgerDetailScreen(
        uiState = uiState,
        listState = listState,
        refreshState = refreshState,
        onClickEdit = viewModel::navigateLedgerEdit,
        onClickDelete = viewModel::showDeleteDialog,
        onClickBack = viewModel::popBackStackWithLedger,
        onClickFloatingButton = viewModel::navigateEnvelopeAdd,
        onClickSeeMoreIcon = viewModel::navigateEnvelopeDetail,
        onClickEnvelopeAddButton = viewModel::navigateEnvelopeAdd,
        onClickFilterButton = viewModel::navigateEnvelopeFilter,
        onClickCloseFriend = viewModel::removeFriend,
        onClickCloseAmount = viewModel::clearAmount,
        onClickAlignButton = viewModel::showAlignBottomSheet,
        onDismissAlignBottomSheet = viewModel::hideAlignBottomSheet,
        onClickAlignBottomSheetItem = viewModel::updateAlignBottomSheet,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LedgerDetailScreen(
    uiState: LedgerDetailState = LedgerDetailState(),
    listState: LazyListState = rememberLazyListState(),
    refreshState: PullToRefreshState = rememberPullToRefreshState(),
    onClickBack: () -> Unit = {},
    onClickEdit: () -> Unit = {},
    onClickDelete: () -> Unit = {},
    onClickFilterButton: () -> Unit = {},
    onClickEnvelopeAddButton: () -> Unit = {},
    onClickFloatingButton: () -> Unit = {},
    onClickSeeMoreIcon: (Envelope) -> Unit = {},
    onClickCloseFriend: (Friend) -> Unit = {},
    onClickCloseAmount: () -> Unit = {},
    onClickAlignButton: () -> Unit = {},
    onDismissAlignBottomSheet: () -> Unit = {},
    onClickAlignBottomSheetItem: (Int) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .background(SusuTheme.colorScheme.background15)
            .fillMaxSize()
            .nestedScroll(refreshState.nestedScrollConnection),
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
                state = listState,
            ) {
                item {
                    with(uiState) {
                        LedgerDetailOverviewColumn(
                            money = money,
                            count = count,
                            eventName = name,
                            eventCategory = category,
                            eventRange = "$startDate - $endDate",
                        )
                    }
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
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                    ) {
                        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxs))
                        SusuGhostButton(
                            color = GhostButtonColor.Black,
                            style = SmallButtonStyle.height32,
                            text = stringResource(id = EnvelopeAlign.entries[uiState.selectedAlignPosition].stringResId),
                            leftIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_align),
                                    contentDescription = null,
                                )
                            },
                            onClick = onClickAlignButton,
                        )

                        FilterButton(uiState.isFiltered, onClickFilterButton)

                        uiState.selectedFriendList.forEach { friend ->
                            SelectedFilterButton(
                                color = FilledButtonColor.Black,
                                style = SmallButtonStyle.height32,
                                name = friend.name,
                                onClickCloseIcon = { onClickCloseFriend(friend) },
                            )
                        }

                        if (uiState.fromAmount != null && uiState.toAmount != null) {
                            SelectedFilterButton(
                                color = FilledButtonColor.Black,
                                style = SmallButtonStyle.height32,
                                name = "${uiState.fromAmount.toMoneyFormat()}~${uiState.toAmount.toMoneyFormat()}",
                                onClickCloseIcon = { onClickCloseAmount() },
                            )
                        }

                        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxs))
                    }
                }

                if (uiState.envelopeList.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 104.dp),
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
                    items(items = uiState.envelopeList, key = { it.envelope.id }) {
                        LedgerDetailEnvelopeContainer(
                            envelope = it,
                            onClickSeeMoreIcon = { onClickSeeMoreIcon(it.envelope) },
                        )
                    }
                }
            }
        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = refreshState,
            containerColor = Gray10,
        )

        if (uiState.showAlignBottomSheet) {
            SusuSelectionBottomSheet(
                onDismissRequest = onDismissAlignBottomSheet,
                containerHeight = 250.dp,
                items = EnvelopeAlign.entries.map { stringResource(id = it.stringResId) }.toPersistentList(),
                selectedItemPosition = uiState.selectedAlignPosition,
                onClickItem = onClickAlignBottomSheetItem,
            )
        }

        SusuFloatingButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(SusuTheme.spacing.spacing_l),
            onClick = onClickFloatingButton,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LedgerDetailScreenPreview() {
    SusuTheme {
        LedgerDetailScreen()
    }
}
