package com.susu.feature.received.envelopeadd.content.date

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.bottomsheet.datepicker.SusuDatePickerBottomSheet
import com.susu.core.designsystem.theme.Gray60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.util.AnnotatedText
import com.susu.core.ui.util.currentDate
import com.susu.feature.received.R
import com.susu.feature.received.ledgeradd.content.date.component.SelectDateRow
import java.time.LocalDateTime

@Composable
fun DateContentRoute(
    viewModel: DateViewModel = hiltViewModel(),
    friendName: String,
    updateParentDate: (LocalDateTime?) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is DateSideEffect.UpdateParentDate -> updateParentDate(sideEffect.date)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.updateName(friendName)
        updateParentDate(uiState.date)
    }

    DateContent(
        uiState = uiState,
        onDateItemSelected = viewModel::updateDate,
        onClickDateText = viewModel::showDateBottomSheet,
        onDismissDateBottomSheet = viewModel::hideDateBottomSheet,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateContent(
    uiState: DateState = DateState(),
    onDateItemSelected: (Int, Int, Int) -> Unit = { _, _, _ -> },
    onClickDateText: () -> Unit = {},
    onDismissDateBottomSheet: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = SusuTheme.spacing.spacing_m,
                vertical = SusuTheme.spacing.spacing_xl,
            ),
    ) {
        AnnotatedText(
            originalText = stringResource(R.string.envelope_date_content_title, uiState.name),
            originalTextStyle = SusuTheme.typography.title_m,
            targetTextList = listOf(stringResource(R.string.envelope_date_content_title_highlight, uiState.name)),
            spanStyle = SusuTheme.typography.title_m.copy(Gray60).toSpanStyle(),
        )
        Spacer(
            modifier = Modifier
                .size(SusuTheme.spacing.spacing_m),
        )
        SelectDateRow(
            year = uiState.date?.year,
            month = uiState.date?.monthValue,
            day = uiState.date?.dayOfMonth,
            onClick = onClickDateText,
        )
    }

    if (uiState.showDateBottomSheet) {
        SusuDatePickerBottomSheet(
            initialYear = uiState.date?.year ?: currentDate.year,
            initialMonth = uiState.date?.monthValue ?: currentDate.monthValue,
            initialDay = uiState.date?.dayOfMonth ?: currentDate.dayOfMonth,
            maximumContainerHeight = 346.dp,
            onDismissRequest = { _, _, _ -> onDismissDateBottomSheet() },
            onItemSelected = onDateItemSelected,
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
