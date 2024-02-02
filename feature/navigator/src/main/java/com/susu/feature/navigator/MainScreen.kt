package com.susu.feature.navigator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.susu.core.designsystem.component.dialog.SusuCheckedDialog
import com.susu.core.designsystem.component.dialog.SusuDialog
import com.susu.core.designsystem.component.navigation.SusuNavigationBar
import com.susu.core.designsystem.component.navigation.SusuNavigationItem
import com.susu.core.designsystem.component.snackbar.SusuSnackbar
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.community.navigation.CommunityRoute
import com.susu.feature.community.navigation.communityNavGraph
import com.susu.feature.loginsignup.navigation.loginSignupNavGraph
import com.susu.feature.mypage.navigation.myPageNavGraph
import com.susu.feature.received.navigation.ReceivedRoute
import com.susu.feature.received.navigation.receivedNavGraph
import com.susu.feature.sent.navigation.sentNavGraph
import com.susu.feature.statistics.navigation.statisticsNavGraph
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navigator: MainNavigator = rememberMainNavigator(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            MainSideEffect.NavigateLogin -> navigator.navigateLogin()
            MainSideEffect.NavigateSent -> navigator.navigateSent()
            MainSideEffect.NavigateSignup -> navigator.navigateSignup()
            is MainSideEffect.ShowNetworkErrorSnackbar -> {
                viewModel.onShowSnackbar(
                    SnackbarToken(
                        message = context.getString(R.string.main_screen_network_error_snackbar),
                        onClickActionButton = sideEffect.retry,
                        actionIcon = R.drawable.ic_refresh,
                        actionIconContentDescription = context.getString(com.susu.core.ui.R.string.content_description_refresh),
                    ),
                )
            }
        }
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = navigator.statusBarColor, darkIcons = true)

    Scaffold(
        modifier = modifier,
        content = { innerPadding ->
            NavHost(
                navController = navigator.navController,
                startDestination = navigator.startDestination,
            ) {
                loginSignupNavGraph(
                    navigateToReceived = navigator::navigateSent,
                    navigateToLogin = navigator::navigateLogin,
                    navigateToSignUp = navigator::navigateSignup,
                    onShowToast = viewModel::onShowSnackbar,
                    padding = innerPadding,
                )

                sentNavGraph(
                    padding = innerPadding,
                    popBackStack = navigator::popBackStackIfNotHome,
                    navigateSentEnvelope = navigator::navigateSentEnvelope,
                    navigateSentEnvelopeDetail = navigator::navigateSentEnvelopeDetail,
                    navigateSentEnvelopeEdit = navigator::navigateSentEnvelopeEdit,
                    navigateSentEnvelopeAdd = navigator::navigateSentEnvelopeAdd,
                    handleException = viewModel::handleException,
                )

                receivedNavGraph(
                    padding = innerPadding,
                    popBackStack = navigator::popBackStackIfNotHome,
                    popBackStackWithLedger = { ledger ->
                        navigator.navController.previousBackStackEntry?.savedStateHandle?.set(
                            ReceivedRoute.LEDGER_ARGUMENT_NAME,
                            ledger,
                        )
                        navigator.popBackStackIfNotHome()
                    },
                    popBackStackWithDeleteLedgerId = { ledgerId ->
                        navigator.navController.previousBackStackEntry?.savedStateHandle?.set(
                            ReceivedRoute.LEDGER_ID_ARGUMENT_NAME,
                            ledgerId,
                        )
                        navigator.popBackStackIfNotHome()
                    },
                    popBackStackWithFilter = { filter ->
                        navigator.navController.previousBackStackEntry?.savedStateHandle?.set(
                            ReceivedRoute.FILTER_ARGUMENT_NAME,
                            filter,
                        )
                        navigator.popBackStackIfNotHome()
                    },
                    popBackStackWithEnvelope = { envelope ->
                        navigator.navController.previousBackStackEntry?.savedStateHandle?.set(
                            ReceivedRoute.ENVELOPE_ARGUMENT_NAME,
                            envelope,
                        )
                        navigator.popBackStackIfNotHome()
                    },
                    popBackStackWithDeleteReceivedEnvelopeId = { id ->
                        navigator.navController.previousBackStackEntry?.savedStateHandle?.set(
                            ReceivedRoute.ENVELOPE_ID_ARGUMENT_NAME,
                            id,
                        )
                        navigator.popBackStackIfNotHome()
                    },
                    navigateLedgerSearch = navigator::navigateLedgerSearch,
                    navigateLedgerDetail = navigator::navigateLedgerDetail,
                    navigateLedgerEdit = navigator::navigateLedgerEdit,
                    navigateLedgerFilter = navigator::navigateLedgerFilter,
                    navigateLedgerAdd = navigator::navigateLedgerAdd,
                    navigateEnvelopAdd = navigator::navigateReceivedEnvelopeAdd,
                    navigateEnvelopeDetail = navigator::navigateReceivedEnvelopeDetail,
                    navigateEnvelopeEdit = navigator::navigateReceivedEnvelopeEdit,
                    onShowSnackbar = viewModel::onShowSnackbar,
                    onShowDialog = viewModel::onShowDialog,
                    handleException = viewModel::handleException,
                )

                statisticsNavGraph(
                    navigateToMyInfo = navigator::navigateMyPageInfo,
                    onShowDialog = viewModel::onShowDialog,
                    handleException = viewModel::handleException,
                )

                communityNavGraph(
                    padding = innerPadding,
                    navigateVoteAdd = navigator::navigateVoteAdd,
                    navigateVoteSearch = navigator::navigateVoteSearch,
                    navigateVoteDetail = navigator::navigateVoteDetail,
                    navigateVoteEdit = navigator::navigateVoteEdit,
                    popBackStack = navigator::popBackStackIfNotHome,
                    popBackStackWithVote = { vote ->
                        navigator.navController.previousBackStackEntry?.savedStateHandle?.set(
                            CommunityRoute.VOTE_ARGUMENT_NAME,
                            vote,
                        )
                        navigator.popBackStackIfNotHome()
                    },
                    popBackStackWithToUpdateVote = { vote ->
                        navigator.navController.previousBackStackEntry?.savedStateHandle?.set(
                            CommunityRoute.TO_UPDATE_VOTE_ARGUMENT_NAME,
                            vote,
                        )
                        navigator.popBackStackIfNotHome()
                    },
                    popBackStackWithDeleteVoteId = { voteId ->
                        navigator.navController.previousBackStackEntry?.savedStateHandle?.set(
                            CommunityRoute.VOTE_ID_ARGUMENT_NAME,
                            voteId,
                        )
                        navigator.popBackStackIfNotHome()
                    },
                    popBackStackWithNeedRefresh = { needRefresh ->
                        navigator.navController.previousBackStackEntry?.savedStateHandle?.set(
                            CommunityRoute.NEED_REFRESH_ARGUMENT_NAME,
                            needRefresh,
                        )
                        navigator.popBackStackIfNotHome()
                    },
                    onShowSnackbar = viewModel::onShowSnackbar,
                    onShowDialog = viewModel::onShowDialog,
                    handleException = viewModel::handleException,
                )

                myPageNavGraph(
                    padding = innerPadding,
                    navigateToLogin = navigator::navigateLogin,
                    navigateToInfo = navigator::navigateMyPageInfo,
                    navigateToSocial = navigator::navigateMyPageSocial,
                    navigateToPrivacyPolicy = navigator::navigateMyPagePrivacyPolicy,
                    popBackStack = navigator::popBackStackIfNotHome,
                    onShowSnackbar = viewModel::onShowSnackbar,
                    onShowDialog = viewModel::onShowDialog,
                    handleException = viewModel::handleException,
                )
            }

            with(uiState) {
                SusuSnackbar(
                    modifier = Modifier.padding(innerPadding),
                    visible = snackbarVisible,
                    message = snackbarToken.message,
                    actionIconId = snackbarToken.actionIcon,
                    actionIconContentDescription = snackbarToken.actionIconContentDescription,
                    actionButtonText = snackbarToken.actionButtonText,
                    onClickActionButton = snackbarToken.onClickActionButton,
                )
            }

            if (uiState.dialogVisible) {
                with(uiState.dialogToken) {
                    if (checkboxText == null) {
                        SusuDialog(
                            title = title,
                            text = text,
                            confirmText = confirmText,
                            dismissText = dismissText,
                            isDimmed = isDimmed,
                            textAlign = textAlign,
                            onConfirmRequest = {
                                onConfirmRequest()
                                viewModel.dismissDialog()
                            },
                            onDismissRequest = {
                                onDismissRequest()
                                viewModel.dismissDialog()
                            },
                        )
                    } else {
                        SusuCheckedDialog(
                            title = title,
                            text = text,
                            confirmText = confirmText,
                            dismissText = dismissText,
                            checkboxText = checkboxText!!,
                            isDimmed = isDimmed,
                            defaultChecked = defaultChecked,
                            textAlign = textAlign,
                            onConfirmRequest = { checked ->
                                if (checked) {
                                    onCheckedAction()
                                }
                                onConfirmRequest()
                                viewModel.dismissDialog()
                            },
                            onDismissRequest = {
                                onDismissRequest()
                                viewModel.dismissDialog()
                            },
                        )
                    }
                }
            }
        },
        bottomBar = {
            MainBottomBar(
                visible = navigator.shouldShowBottomBar(),
                currentTab = navigator.currentTab,
                entries = MainNavigationTab.entries.toImmutableList(),
                onClickItem = navigator::navigate,
            )
        },
    )
}

@Composable
private fun MainBottomBar(
    visible: Boolean,
    currentTab: MainNavigationTab?,
    entries: ImmutableList<MainNavigationTab>,
    onClickItem: (MainNavigationTab) -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideIn { IntOffset(0, it.height) },
        exit = fadeOut() + slideOut { IntOffset(0, it.height) },
    ) {
        SusuNavigationBar {
            entries.forEach { tab ->
                tab.run {
                    SusuNavigationItem(
                        selected = tab == currentTab,
                        label = stringResource(id = labelId),
                        selectedIcon = selectedIconId,
                        unselectedIcon = unselectedIconId,
                        onClick = { onClickItem(tab) },
                    )
                }
            }
        }
    }
}
