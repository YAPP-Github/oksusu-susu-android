package com.susu.feature.statistics.content.susu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.bottomsheet.SusuSelectionBottomSheet
import com.susu.core.designsystem.component.screen.LoadingScreen
import com.susu.core.designsystem.theme.Blue60
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.Gray40
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.statistics.R
import com.susu.feature.statistics.component.RecentSpentGraph
import com.susu.feature.statistics.component.StatisticsHorizontalItem
import com.susu.feature.statistics.component.StatisticsVerticalItem
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun SusuStatisticsRoute(
    isBlind: Boolean,
    modifier: Modifier = Modifier,
    viewModel: SusuStatisticsViewModel = hiltViewModel(),
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is SusuStatisticsEffect.HandleException -> handleException(sideEffect.throwable, sideEffect.retry)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getStatisticsOption()
    }

    LaunchedEffect(key1 = uiState.age, key2 = uiState.category, key3 = uiState.relationship) {
        viewModel.getSusuStatistics()
    }

    SusuStatisticsScreen(
        uiState = uiState,
        isBlind = isBlind,
        modifier = modifier,
        onClickAge = viewModel::showAgeSheet,
        onClickRelationship = viewModel::showRelationshipSheet,
        onClickCategory = viewModel::showCategorySheet,
        onSelectAge = viewModel::selectAge,
        onSelectCategory = viewModel::selectCategory,
        onSelectRelationship = viewModel::selectRelationship,
        onDismissAge = viewModel::hideAgeSheet,
        onDismissCategory = viewModel::hideCategorySheet,
        onDismissRelationship = viewModel::hideRelationshipSheet,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SusuStatisticsScreen(
    modifier: Modifier = Modifier,
    uiState: SusuStatisticsState = SusuStatisticsState(),
    isBlind: Boolean = true,
    onClickAge: () -> Unit = {},
    onClickRelationship: () -> Unit = {},
    onClickCategory: () -> Unit = {},
    onDismissAge: () -> Unit = {},
    onDismissRelationship: () -> Unit = {},
    onDismissCategory: () -> Unit = {},
    onSelectAge: (StatisticsAge) -> Unit = {},
    onSelectRelationship: (Int) -> Unit = {},
    onSelectCategory: (Int) -> Unit = {},
) {
    val context = LocalContext.current
    val ageItems = remember {
        StatisticsAge.entries.map { context.getString(R.string.word_age_unit, it.num) }.toImmutableList()
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
        ) {
            SusuStatisticsOptionSlot(
                title = stringResource(R.string.statistics_susu_average_title),
                age = stringResource(id = R.string.word_age_unit, uiState.age.num),
                money = uiState.susuStatistics.averageSent,
                relationship = uiState.relationship.relation,
                category = uiState.category.name,
                onAgeClick = onClickAge,
                onCategoryClick = onClickCategory,
                onRelationshipClick = onClickRelationship,
            )
            StatisticsHorizontalItem(
                title = stringResource(R.string.statistics_susu_relationship_average),
                name = uiState.susuStatistics.averageRelationship.title,
                money = uiState.susuStatistics.averageRelationship.value,
                isActive = !isBlind,
            )
            StatisticsHorizontalItem(
                title = stringResource(R.string.statistics_susu_category_average),
                name = uiState.susuStatistics.averageCategory.title,
                money = uiState.susuStatistics.averageCategory.value,
                isActive = !isBlind,
            )

            RecentSpentGraph(
                isActive = !isBlind,
                graphTitle = stringResource(R.string.statistics_susu_this_year_spent),
                spentData = uiState.susuStatistics.recentSpent.toPersistentList(),
                maximumAmount = uiState.susuStatistics.recentMaximumSpent,
                totalAmount = uiState.susuStatistics.recentTotalSpent,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Gray10, shape = RoundedCornerShape(4.dp))
                    .padding(SusuTheme.spacing.spacing_m),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = stringResource(R.string.statistics_most_spent_month), style = SusuTheme.typography.title_xs, color = Gray100)
                if (isBlind) {
                    Text(
                        text = stringResource(R.string.word_month_format, stringResource(id = R.string.word_unknown)),
                        style = SusuTheme.typography.title_xs,
                        color = Gray40,
                    )
                } else {
                    Text(
                        text = stringResource(R.string.word_month_format, uiState.susuStatistics.mostSpentMonth.toString()),
                        style = SusuTheme.typography.title_xs,
                        color = Blue60,
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                StatisticsVerticalItem(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.statistics_most_susu_relationship),
                    content = uiState.susuStatistics.mostRelationship.title,
                    count = uiState.susuStatistics.mostRelationship.value,
                    isActive = !isBlind,
                )
                Spacer(modifier = Modifier.width(SusuTheme.spacing.spacing_xxs))
                StatisticsVerticalItem(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.statistics_most_susu_event),
                    content = uiState.susuStatistics.mostCategory.title,
                    count = uiState.susuStatistics.mostCategory.value,
                    isActive = !isBlind,
                )
            }
        }

        if (uiState.isAgeSheetOpen) {
            SusuSelectionBottomSheet(
                containerHeight = 322.dp,
                items = ageItems,
                selectedItemPosition = uiState.age.ordinal,
                onClickItem = { onSelectAge(StatisticsAge.entries[it]) },
                onDismissRequest = onDismissAge,
            )
        }

        if (uiState.isRelationshipSheetOpen) {
            SusuSelectionBottomSheet(
                containerHeight = 322.dp,
                items = uiState.relationshipConfig.map { it.relation }.toImmutableList(),
                selectedItemPosition = uiState.relationshipConfig.indexOf(uiState.relationship),
                onClickItem = onSelectRelationship,
                onDismissRequest = onDismissRelationship,
            )
        }

        if (uiState.isCategorySheetOpen) {
            SusuSelectionBottomSheet(
                containerHeight = 322.dp,
                items = uiState.categoryConfig.map { it.name }.toImmutableList(),
                selectedItemPosition = uiState.categoryConfig.indexOf(uiState.category),
                onClickItem = onSelectCategory,
                onDismissRequest = onDismissCategory,
            )
        }

        if (uiState.isLoading) {
            LoadingScreen(
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SusuStatisticsScreenPreview() {
    SusuTheme {
        SusuStatisticsScreen(
            isBlind = false,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SusuStatisticsScreenBlindPreview() {
    SusuTheme {
        SusuStatisticsScreen(
            isBlind = true,
        )
    }
}
