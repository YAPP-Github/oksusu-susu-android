package com.susu.feature.received.ledgeradd.content.date

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.bottomsheet.datepicker.SusuLimitDatePickerBottomSheet
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.SmallButtonStyle
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Category
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.util.AnnotatedText
import com.susu.core.ui.util.currentDate
import com.susu.core.ui.util.minDate
import com.susu.feature.received.R
import com.susu.feature.received.ledgeradd.content.date.component.SelectDateRow
import java.time.LocalDateTime

@Composable
fun DateContentRoute(
    viewModel: DateViewModel = hiltViewModel(),
    name: String,
    category: Category?,
    updateParentDate: (LocalDateTime?, LocalDateTime?) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is DateSideEffect.UpdateParentDate -> updateParentDate(sideEffect.startAt, sideEffect.endAt)
        }
    }

    LaunchedEffect(key1 = Unit) {
        updateParentDate(uiState.startAt, uiState.endAt)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.setScreenType(category ?: Category())
        viewModel.updateNameAndCategory(name, category?.customCategory ?: category?.name ?: "")
    }

    DateContent(
        uiState = uiState,
        onStartDateItemSelected = viewModel::updateStartDate,
        onClickStartDateText = viewModel::showStartDateBottomSheet,
        onDismissStartDateBottomSheet = { year, month, day ->
            viewModel.updateStartDate(year, month, day)
            viewModel.hideStartDateBottomSheet()
        },
        onEndDateItemSelected = viewModel::updateEndDate,
        onClickEndDateText = viewModel::showEndDateBottomSheet,
        onDismissEndDateBottomSheet = { year, month, day ->
            viewModel.updateEndDate(year, month, day)
            viewModel.hideEndDateBottomSheet()
        },
        onClickSetDateButton = viewModel::toggleShowOnlyStartAt,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateContent(
    uiState: DateState = DateState(),
    onStartDateItemSelected: (Int, Int, Int) -> Unit = { _, _, _ -> },
    onClickStartDateText: () -> Unit = {},
    onDismissStartDateBottomSheet: (Int, Int, Int) -> Unit = { _, _, _ -> },
    onEndDateItemSelected: (Int, Int, Int) -> Unit = { _, _, _ -> },
    onClickEndDateText: () -> Unit = {},
    onDismissEndDateBottomSheet: (Int, Int, Int) -> Unit = { _, _, _ -> },
    onClickSetDateButton: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = SusuTheme.spacing.spacing_xl,
                start = SusuTheme.spacing.spacing_m,
                end = SusuTheme.spacing.spacing_m,
            ),
    ) {
        AnnotatedText(
            originalText = stringResource(R.string.date_content_title, uiState.name, uiState.categoryName),
            originalTextStyle = SusuTheme.typography.title_m,
            targetTextList = listOf(uiState.name, uiState.categoryName),
            spanStyle = SusuTheme.typography.title_m.copy(
                color = Gray60,
            ).toSpanStyle(),
        )

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))

        SelectDateRow(
            year = uiState.startAt?.year,
            month = uiState.startAt?.monthValue,
            day = uiState.startAt?.dayOfMonth,
            suffix = if (uiState.showOnlyStartAt.not()) stringResource(R.string.ledger_add_screen_from) else "",
            onClick = onClickStartDateText,
        )

        if (uiState.showOnlyStartAt.not()) {
            Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxs))

            SelectDateRow(
                year = uiState.endAt?.year,
                month = uiState.endAt?.monthValue,
                day = uiState.endAt?.dayOfMonth,
                suffix = stringResource(R.string.ledger_add_screen_until),
                onClick = onClickEndDateText,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        SusuGhostButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = GhostButtonColor.Orange,
            style = SmallButtonStyle.height40,
            text = if (uiState.showOnlyStartAt) {
                stringResource(R.string.date_content_show_endat)
            } else {
                stringResource(
                    R.string.date_content_set_only_startat,
                )
            },
            onClick = onClickSetDateButton,
        )

        Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxl))
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
            onDismissRequest = onDismissStartDateBottomSheet,
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
            onDismissRequest = onDismissEndDateBottomSheet,
            onItemSelected = onEndDateItemSelected,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF6F6F6)
@Composable
fun DateContentPreview() {
    SusuTheme {
        DateContent()
    }
}
