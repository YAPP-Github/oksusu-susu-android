package com.susu.feature.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.analytics.FirebaseAnalytics
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.LogoIcon
import com.susu.core.designsystem.component.screen.LoadingScreen
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.DialogToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.statistics.component.StatisticsTab
import com.susu.feature.statistics.content.my.MyStatisticsRoute
import com.susu.feature.statistics.content.susu.SusuStatisticsRoute

@Composable
fun StatisticsRoute(
    padding: PaddingValues,
    viewModel: StatisticsViewModel = hiltViewModel(),
    navigateToMyInfo: () -> Unit,
    navigateToSent: () -> Unit,
    onShowDialog: (DialogToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is StatisticsEffect.HandleException -> handleException(sideEffect.throwable, sideEffect.retry)
        }
    }

    LaunchedEffect(key1 = uiState.currentTab) {
        FirebaseAnalytics.getInstance(context).logEvent(
            FirebaseAnalytics.Event.SCREEN_VIEW,
            bundleOf(
                FirebaseAnalytics.Param.SCREEN_NAME to "statistics_${uiState.currentTab}",
            ),
        )
    }

    StatisticsScreen(
        padding = padding,
        uiState = uiState,
        onTabSelected = viewModel::selectStatisticsTab,
        navigateToMyInfo = navigateToMyInfo,
        navigateToSent = navigateToSent,
        onShowDialog = onShowDialog,
        handleException = handleException,
    )
}

@Composable
fun StatisticsScreen(
    padding: PaddingValues = PaddingValues(),
    uiState: StatisticsState = StatisticsState(),
    onTabSelected: (StatisticsTab) -> Unit = {},
    navigateToMyInfo: () -> Unit = {},
    navigateToSent: () -> Unit = {},
    onShowDialog: (DialogToken) -> Unit = {},
    handleException: (Throwable, () -> Unit) -> Unit = { _, _ -> },
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(padding),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        ) {
            SusuDefaultAppBar(
                modifier = Modifier.padding(horizontal = SusuTheme.spacing.spacing_xs),
                leftIcon = { LogoIcon() },
                title = stringResource(R.string.statistics_word),
            )
            Column(
                modifier = Modifier.padding(horizontal = SusuTheme.spacing.spacing_m),
            ) {
                StatisticsTab(
                    modifier = Modifier
                        .height(52.dp)
                        .padding(vertical = SusuTheme.spacing.spacing_xxs),
                    selectedTab = uiState.currentTab,
                    onTabSelect = onTabSelected,
                )
                when (uiState.currentTab) {
                    StatisticsTab.MY -> MyStatisticsRoute(
                        modifier = Modifier.fillMaxSize(),
                        handleException = handleException,
                        onShowDialog = onShowDialog,
                        navigateToSent = navigateToSent,
                    )

                    StatisticsTab.AVERAGE -> SusuStatisticsRoute(
                        modifier = Modifier.fillMaxSize(),
                        handleException = handleException,
                        onShowDialog = onShowDialog,
                        navigateToMyInfo = navigateToMyInfo,

                    )
                }
            }
        }

        if (uiState.isLoading) {
            LoadingScreen(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SentScreenPreview() {
    SusuTheme {
        StatisticsScreen()
    }
}
