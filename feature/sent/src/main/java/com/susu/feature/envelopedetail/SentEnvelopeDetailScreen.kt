package com.susu.feature.envelopedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.appbar.icon.DeleteText
import com.susu.core.designsystem.component.appbar.icon.EditText
import com.susu.core.designsystem.theme.Gray100
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.toMoneyFormat
import com.susu.feature.envelopedetail.component.DetailItem
import com.susu.feature.sent.R

@Composable
fun SentEnvelopeDetailRoute(
    viewModel: SentEnvelopeDetailViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    navigateSentEnvelopeEdit: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            SentEnvelopeDetailEffect.PopBackStack -> popBackStack()
            is SentEnvelopeDetailEffect.NavigateEnvelopeEdit -> navigateSentEnvelopeEdit()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getEnvelopeDetail()
    }

    SentEnvelopeDetailScreen(
        uiState = uiState,
        onClickBackIcon = viewModel::popBackStack,
        onClickEdit = viewModel::navigateSentEnvelopeEdit,
    )
}

@Composable
fun SentEnvelopeDetailScreen(
    uiState: SentEnvelopeDetailState = SentEnvelopeDetailState(),
    modifier: Modifier = Modifier,
    onClickBackIcon: () -> Unit = {},
    onClickEdit: () -> Unit = {},
    onClickDelete: () -> Unit = {},
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .background(SusuTheme.colorScheme.background10),
    ) {
        Column {
            SusuDefaultAppBar(
                leftIcon = {
                    BackIcon(
                        onClick = onClickBackIcon,
                    )
                },
                actions = {
                    EditText(
                        onClick = onClickEdit,
                    )
                    Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_m))
                    DeleteText(
                        onClick = onClickDelete,
                    )
                    Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_m))
                },
            )

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(
                        start = SusuTheme.spacing.spacing_m,
                        end = SusuTheme.spacing.spacing_m,
                        top = SusuTheme.spacing.spacing_xl,
                    )
                    .verticalScroll(scrollState),
            ) {
                Text(
                    text = uiState.money.toMoneyFormat() + stringResource(R.string.sent_envelope_card_money_won),
                    style = SusuTheme.typography.title_xxl,
                    color = Gray100,
                )
                Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_m))
                Column {
                    DetailItem(
                        categoryText = stringResource(R.string.sent_envelope_detail_category_event),
                        contentText = uiState.event,
                        isEmptyContent = uiState.event.isEmpty(),
                    )
                    DetailItem(
                        categoryText = stringResource(R.string.sent_envelope_detail_category_name),
                        contentText = uiState.name,
                        isEmptyContent = uiState.name.isEmpty(),
                    )
                    DetailItem(
                        categoryText = stringResource(R.string.sent_envelope_detail_category_relationship),
                        contentText = uiState.relationship,
                        isEmptyContent = uiState.relationship.isEmpty(),
                    )
                    DetailItem(
                        categoryText = stringResource(R.string.sent_envelope_detail_category_date),
                        contentText = uiState.date,
                        isEmptyContent = uiState.date.isEmpty(),
                    )
                    DetailItem(
                        categoryText = stringResource(R.string.sent_envelope_detail_category_visited),
                        contentText = if (uiState.visited == true) stringResource(R.string.sent_envelope_detail_category_visited_yes)
                        else stringResource(R.string.sent_envelope_detail_category_visited_no),
                        isEmptyContent = uiState.visited == null,
                    )
                    DetailItem(
                        categoryText = stringResource(R.string.sent_envelope_detail_category_gift),
                        contentText = uiState.gift,
                        isEmptyContent = uiState.gift.isEmpty(),
                    )
                    DetailItem(
                        categoryText = stringResource(R.string.sent_envelope_detail_category_phone),
                        contentText = uiState.phoneNumber,
                        isEmptyContent = uiState.phoneNumber.isEmpty(),
                    )
                    DetailItem(
                        categoryText = stringResource(R.string.sent_envelope_detail_category_memo),
                        contentText = uiState.memo,
                        isEmptyContent = uiState.memo.isEmpty(),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SentEnvelopeDetailScreenPreview() {
    SusuTheme {
        SentEnvelopeDetailScreen()
    }
}
