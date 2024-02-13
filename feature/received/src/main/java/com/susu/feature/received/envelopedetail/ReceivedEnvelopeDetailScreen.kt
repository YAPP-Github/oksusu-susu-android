package com.susu.feature.received.envelopedetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.susu.core.model.Envelope
import com.susu.core.model.Ledger
import com.susu.core.ui.DialogToken
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.toMoneyFormat
import com.susu.core.ui.util.to_yyyy_korYear_M_korMonth_d_korDay
import com.susu.feature.received.R
import com.susu.feature.received.envelopedetail.component.DetailItem
import kotlinx.datetime.toJavaLocalDateTime

@Composable
fun ReceivedEnvelopeDetailRoute(
    viewModel: ReceivedEnvelopeDetailViewModel = hiltViewModel(),
    popBackStackWithDeleteReceivedEnvelopeId: (Long) -> Unit,
    popBackStackWithReceivedEnvelope: (String) -> Unit,
    navigateReceivedEnvelopeEdit: (Envelope, Ledger) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
    onShowDialog: (DialogToken) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is ReceivedEnvelopeDetailSideEffect.HandleException -> handleException(sideEffect.throwable, sideEffect.retry)
            is ReceivedEnvelopeDetailSideEffect.NavigateReceivedEnvelopeEdit -> navigateReceivedEnvelopeEdit(sideEffect.envelope, sideEffect.ledger)
            is ReceivedEnvelopeDetailSideEffect.PopBackStackWithDeleteReceivedEnvelopeId -> popBackStackWithDeleteReceivedEnvelopeId(
                sideEffect.envelopeId,
            )

            is ReceivedEnvelopeDetailSideEffect.PopBackStackWithReceivedEnvelope -> popBackStackWithReceivedEnvelope(sideEffect.envelope)
            is ReceivedEnvelopeDetailSideEffect.ShowDeleteDialog -> onShowDialog(
                DialogToken(
                    title = context.getString(R.string.dialog_delete_envelope_title),
                    text = context.getString(R.string.dialog_delete_envelope_text),
                    confirmText = context.getString(com.susu.core.ui.R.string.word_delete),
                    dismissText = context.getString(com.susu.core.ui.R.string.word_cancel),
                    onConfirmRequest = sideEffect.onConfirmRequest,
                ),
            )

            ReceivedEnvelopeDetailSideEffect.ShowDeleteSuccessSnackbar -> onShowSnackbar(
                SnackbarToken(message = context.getString(R.string.toast_delete_envelope_success)),
            )

            is ReceivedEnvelopeDetailSideEffect.ShowSnackbar -> TODO()
        }
    }

    BackHandler {
        viewModel.popBackStackWithEnvelope()
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getEnvelope()
    }

    ReceivedEnvelopeDetailScreen(
        uiState = uiState,
        onClickEdit = viewModel::navigateEnvelopeEdit,
        onClickDelete = viewModel::showDeleteDialog,
        onClickBackIcon = viewModel::popBackStackWithEnvelope,
    )
}

@Composable
fun ReceivedEnvelopeDetailScreen(
    uiState: ReceivedEnvelopeDetailState = ReceivedEnvelopeDetailState(),
    onClickBackIcon: () -> Unit = {},
    onClickEdit: () -> Unit = {},
    onClickDelete: () -> Unit = {},
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
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
                    Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))
                    DeleteText(
                        onClick = onClickDelete,
                    )
                    Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))
                },
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = SusuTheme.spacing.spacing_m,
                        end = SusuTheme.spacing.spacing_m,
                        top = SusuTheme.spacing.spacing_xl,
                    )
                    .verticalScroll(scrollState),
            ) {
                Text(
                    text = stringResource(id = com.susu.core.ui.R.string.money_unit_format, uiState.envelope.amount.toMoneyFormat()),
                    style = SusuTheme.typography.title_xxl,
                    color = Gray100,
                )
                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))
                Column {
                    DetailItem(
                        categoryText = stringResource(id = com.susu.core.ui.R.string.word_name),
                        contentText = uiState.envelope.friend.name,
                        isEmptyContent = false,
                    )
                    DetailItem(
                        categoryText = stringResource(com.susu.core.ui.R.string.word_relationship),
                        contentText = uiState.envelope.relationship.customRelation ?: uiState.envelope.relationship.relation,
                        isEmptyContent = false,
                    )
                    DetailItem(
                        categoryText = stringResource(com.susu.core.ui.R.string.word_date),
                        contentText = uiState.envelope.handedOverAt.toJavaLocalDateTime().to_yyyy_korYear_M_korMonth_d_korDay(),
                        isEmptyContent = false,
                    )
                    DetailItem(
                        categoryText = stringResource(com.susu.core.ui.R.string.word_is_visited),
                        contentText = if (uiState.envelope.hasVisited == true) {
                            stringResource(id = com.susu.core.ui.R.string.word_yes)
                        } else {
                            stringResource(
                                id = com.susu.core.ui.R.string.word_no,
                            )
                        },
                        isEmptyContent = uiState.envelope.hasVisited == null,
                    )
                    DetailItem(
                        categoryText = stringResource(com.susu.core.ui.R.string.word_gift),
                        contentText = uiState.envelope.gift ?: "",
                        isEmptyContent = uiState.envelope.gift == null,
                    )
                    DetailItem(
                        categoryText = stringResource(id = com.susu.core.ui.R.string.word_phone_number),
                        contentText = uiState.envelope.friend.phoneNumber,
                        isEmptyContent = uiState.envelope.friend.phoneNumber.isEmpty(),
                    )
                    DetailItem(
                        categoryText = stringResource(id = com.susu.core.ui.R.string.word_memo),
                        contentText = uiState.envelope.memo ?: "",
                        isEmptyContent = uiState.envelope.memo == null,
                    )
                }
                Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxxl))
            }
        }
    }
}

@Preview
@Composable
fun ReceivedEnvelopeDetailScreenPreview() {
    SusuTheme {
        ReceivedEnvelopeDetailScreen()
    }
}
