package com.susu.feature.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.LogoIcon
import com.susu.core.designsystem.component.screen.LoadingScreen
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.DialogToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.statistics.component.StatisticsTab
import com.susu.feature.statistics.content.MyStatisticsRoute

@Composable
fun StatisticsRoute(
    viewModel: StatisticsViewModel = hiltViewModel(),
    navigateToMyInfo: () -> Unit,
    onShowDialog: (DialogToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is StatisticsEffect.HandleException -> handleException(sideEffect.throwable, sideEffect.retry)
            StatisticsEffect.ShowAdditionalInfoDialog -> onShowDialog(
                DialogToken(
                    title = "통계를 위한 정보를 알려주세요",
                    text = "나의 평균 거래 상황을 분석하기 위해\n필요한 정보가 있어요",
                    dismissText = "닫기",
                    confirmText = "정보 입력하기",
                    onConfirmRequest = navigateToMyInfo,
                ),
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.checkAdditionalInfo()
    }

    StatisticsScreen(
        uiState = uiState,
    )
}

@Composable
fun StatisticsScreen(
    uiState: StatisticsState = StatisticsState(),
    onTabSelected: (StatisticsTab) -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = SusuTheme.spacing.spacing_m),
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        ) {
            SusuDefaultAppBar(
                modifier = Modifier.padding(horizontal = SusuTheme.spacing.spacing_xs),
                leftIcon = { LogoIcon() },
                title = stringResource(R.string.statistics_word),
            )
            StatisticsTab(
                modifier = Modifier
                    .height(52.dp)
                    .padding(vertical = SusuTheme.spacing.spacing_xxs),
                selectedTab = uiState.currentTab,
                onTabSelect = onTabSelected,
            )
            when (uiState.currentTab) {
                StatisticsTab.MY -> MyStatisticsRoute(
                    isBlind = uiState.isBlind,
                    modifier = Modifier.fillMaxSize(),
                )

                StatisticsTab.AVERAGE -> {}
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
