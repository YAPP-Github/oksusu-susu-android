package com.susu.feature.navigator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.susu.core.designsystem.component.dialog.SusuDialog
import com.susu.core.designsystem.component.navigation.SusuNavigationBar
import com.susu.core.designsystem.component.navigation.SusuNavigationItem
import com.susu.core.designsystem.component.snackbar.SusuSnackbar
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.community.navigation.communityNavGraph
import com.susu.feature.loginsignup.navigation.loginSignupNavGraph
import com.susu.feature.mypage.navigation.myPageNavGraph
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
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            MainSideEffect.NavigateLogin -> navigator.navigateLogin()
            MainSideEffect.NavigateSent -> navigator.navigateSent()
            MainSideEffect.NavigateSignup -> navigator.navigateSignup()
        }
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = navigator.statusBarColor, darkIcons = true)

    Scaffold(
        modifier = modifier,
        content = { innerPadding ->
            Box(modifier = Modifier.fillMaxSize()) {
                NavHost(
                    navController = navigator.navController,
                    startDestination = navigator.startDestination,
                ) {
                    loginSignupNavGraph(
                        navigateToReceived = navigator::navigateSent,
                        navigateToLogin = navigator::navigateLogin,
                        navigateToSignUp = navigator::navigateSignup,
                        onShowToast = viewModel::onShowToast,
                        padding = innerPadding,
                    )

                    sentNavGraph(
                        padding = innerPadding,
                        popBackStack = navigator::popBackStackIfNotHome,
                        navigateSentEnvelope = navigator::navigateSentEnvelope,
                        navigateSentEnvelopeDetail = navigator::navigateSentEnvelopeDetail,
                        navigateSentEnvelopeEdit = navigator::navigateSentEnvelopeEdit,
                        navigateSentEnvelopeAdd = navigator::navigateSentEnvelopeAdd,
                    )

                    receivedNavGraph(
                        padding = innerPadding,
                        popBackStack = navigator::popBackStackIfNotHome,
                        navigateLedgerSearch = navigator::navigateLedgerSearch,
                        navigateLedgerDetail = navigator::navigateLedgerDetail,
                        navigateLedgerEdit = navigator::navigateLedgerEdit,
                        navigateLedgerFilter = navigator::navigateLedgerFilter,
                        navigateLedgerAdd = navigator::navigateLedgerAdd,
                    )

                    statisticsNavGraph(
                        padding = innerPadding,
                    )

                    communityNavGraph(
                        padding = innerPadding,
                    )

                    myPageNavGraph(
                        padding = innerPadding,
                        navigateToLogin = navigator::navigateLogin,
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
