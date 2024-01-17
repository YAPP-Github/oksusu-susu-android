package com.susu.feature.received.ledgerfilter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.bottomsheet.datepicker.SusuLimitDatePickerBottomSheet
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.LinedButtonColor
import com.susu.core.designsystem.component.button.RefreshButton
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.component.button.SusuLinedButton
import com.susu.core.designsystem.component.button.XSmallButtonStyle
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Category
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.util.currentDate
import com.susu.core.ui.util.minDate
import com.susu.core.ui.util.to_yyyy_dot_MM_dot_dd
import com.susu.feature.received.R
import com.susu.feature.received.ledgerfilter.component.DateText
import com.susu.feature.received.ledgerfilter.component.SelectedFilterButton

@Composable
fun LedgerFilterRoute(
    viewModel: LedgerFilterViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    popBackStackWithFilter: (String) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is LedgerFilterSideEffect.HandleException -> TODO()
            LedgerFilterSideEffect.PopBackStack -> popBackStack()
            is LedgerFilterSideEffect.PopBackStackWithFilter -> popBackStackWithFilter(sideEffect.filter)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.initData()
    }

    LedgerFilterScreen(
        uiState = uiState,
        onClickBackIcon = viewModel::popBackStack,
        onClickApplyFilterButton = viewModel::popBackStackWithFilter,
        onStartDateItemSelected = viewModel::updateStartDate,
        onClickStartDateText = viewModel::showStartDateBottomSheet,
        onDismissStartDateBottomSheet = viewModel::hideStartDateBottomSheet,
        onEndDateItemSelected = viewModel::updateEndDate,
        onClickEndDateText = viewModel::showEndDateBottomSheet,
        onDismissEndDateBottomSheet = viewModel::hideEndDateBottomSheet,
        onClickCategory = viewModel::selectCategory,
        onClickCategoryClose = viewModel::unselectCategory,
        onClickDateClose = viewModel::clearDate,
        onClickRefreshButton = viewModel::clearFilter,
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LedgerFilterScreen(
    uiState: LedgerFilterState = LedgerFilterState(),
    onClickBackIcon: () -> Unit = {},
    onClickApplyFilterButton: () -> Unit = {},
    onStartDateItemSelected: (Int, Int, Int) -> Unit = { _, _, _ -> },
    onClickStartDateText: () -> Unit = {},
    onDismissStartDateBottomSheet: () -> Unit = {},
    onEndDateItemSelected: (Int, Int, Int) -> Unit = { _, _, _ -> },
    onClickEndDateText: () -> Unit = {},
    onDismissEndDateBottomSheet: () -> Unit = {},
    onClickCategory: (Category) -> Unit = {},
    onClickCategoryClose: (Category) -> Unit = {},
    onClickDateClose: () -> Unit = {},
    onClickRefreshButton: () -> Unit = {},
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
                uiState.categoryConfig.forEach { category ->
                    SusuLinedButton(
                        color = LinedButtonColor.Black,
                        style = XSmallButtonStyle.height28,
                        isActive = category in uiState.selectedCategoryList,
                        text = category.name,
                        onClick = { onClickCategory(category) }
                    )
                }
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
                DateText(
                    date = uiState.startAt,
                    onClick = onClickStartDateText,
                )

                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxxxs))

                Text(
                    text = stringResource(R.string.ledger_filter_screen_from),
                    style = SusuTheme.typography.title_xxs,
                )

                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))

                DateText(
                    date = uiState.endAt,
                    onClick = onClickEndDateText
                )

                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxxxs))

                Text(
                    text = stringResource(R.string.ledger_filter_screen_until),
                    style = SusuTheme.typography.title_xxs,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
            ) {
                FlowRow(
                    verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                    horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                ) {
                    uiState.selectedCategoryList.forEach { category ->
                        SelectedFilterButton(
                            name = category.name,
                            onClickCloseIcon = { onClickCategoryClose(category) },
                        )
                    }

                    if (uiState.startAt != null && uiState.endAt != null) {
                        SelectedFilterButton(
                            name = "${uiState.startAt.to_yyyy_dot_MM_dot_dd()}~${uiState.endAt.to_yyyy_dot_MM_dot_dd()}",
                            onClickCloseIcon = onClickDateClose,
                        )
                    }
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_m),
                ) {
                    RefreshButton(onClick = onClickRefreshButton)

                    SusuFilledButton(
                        modifier = Modifier.fillMaxWidth(),
                        color = FilledButtonColor.Black,
                        style = SmallButtonStyle.height48,
                        isActive = true,
                        text = stringResource(com.susu.core.ui.R.string.word_apply_filter),
                        onClick = onClickApplyFilterButton,
                    )
                }
            }
        }
    }

    if (uiState.showStartDateBottomSheet) {
        SusuLimitDatePickerBottomSheet(
            initialYear = uiState.startAt?.year ?: currentDate.year,
            initialMonth = uiState.startAt?.monthValue ?: currentDate.monthValue,
            initialDay = uiState.startAt?.dayOfMonth ?: currentDate.dayOfMonth,
            initialCriteriaYear = uiState.endAt?.year,
            initialCriteriaMonth = uiState.endAt?.monthValue,
            initialCriteriaDay = uiState.endAt?.dayOfMonth,
            afterDate = false,
            maximumContainerHeight = 346.dp,
            onDismissRequest = { _, _, _ -> onDismissStartDateBottomSheet() },
            onItemSelected = onStartDateItemSelected,
        )
    }

    if (uiState.showEndDateBottomSheet) {
        SusuLimitDatePickerBottomSheet(
            initialYear = uiState.endAt?.year ?: currentDate.year,
            initialMonth = uiState.endAt?.monthValue ?: currentDate.monthValue,
            initialDay = uiState.endAt?.dayOfMonth ?: currentDate.dayOfMonth,
            initialCriteriaYear = uiState.startAt?.year ?: minDate.year,
            initialCriteriaMonth = uiState.startAt?.monthValue ?: minDate.monthValue,
            initialCriteriaDay = uiState.startAt?.dayOfMonth ?: minDate.dayOfMonth,
            afterDate = true,
            maximumContainerHeight = 346.dp,
            onDismissRequest = { _, _, _ -> onDismissEndDateBottomSheet() },
            onItemSelected = onEndDateItemSelected,
        )
    }
}

@Preview
@Composable
fun LedgerFilterScreenPreview() {
    SusuTheme {
        LedgerFilterScreen()
    }
}
