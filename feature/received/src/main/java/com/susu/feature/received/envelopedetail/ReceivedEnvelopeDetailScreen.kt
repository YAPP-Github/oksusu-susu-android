package com.susu.feature.received.envelopedetail

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
import com.susu.core.ui.DialogToken
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.feature.received.R
import com.susu.feature.received.ReceivedEnvelopedetail.ReceivedReceivedEnvelopeDetailViewModel
import com.susu.feature.received.envelopedetail.component.DetailItem

@Composable
fun ReceivedEnvelopeDetailRoute(
    viewModel: ReceivedReceivedEnvelopeDetailViewModel = hiltViewModel(),
    popBackStackWithDeleteReceivedEnvelopeId: (Long) -> Unit,
    navigateReceivedEnvelopeEdit: () -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
    onShowDialog: (DialogToken) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is ReceivedEnvelopeDetailSideEffect.HandleException -> handleException(sideEffect.throwable, sideEffect.retry)
            is ReceivedEnvelopeDetailSideEffect.NavigateReceivedEnvelopeEdit -> TODO()
            is ReceivedEnvelopeDetailSideEffect.PopBackStackWithDeleteReceivedEnvelopeId -> popBackStackWithDeleteReceivedEnvelopeId(sideEffect.envelopeId)
            is ReceivedEnvelopeDetailSideEffect.PopBackStackWithReceivedEnvelope -> TODO()
            is ReceivedEnvelopeDetailSideEffect.ShowDeleteDialog -> onShowDialog(
                DialogToken(
                    title = context.getString(R.string.dialog_delete_envelope_title),
                    text = context.getString(R.string.dialog_delete_envelope_text),
                    confirmText = context.getString(com.susu.core.ui.R.string.word_delete),
                    dismissText = context.getString(com.susu.core.ui.R.string.word_cancel),
                    onConfirmRequest = sideEffect.onConfirmRequest,
                ),
            )
            ReceivedEnvelopeDetailSideEffect.ShowDeleteSuccessSnackbar -> onShowSnackbar(SnackbarToken(message = context.getString(R.string.toast_delete_envelope_success)))
            is ReceivedEnvelopeDetailSideEffect.ShowSnackbar -> TODO()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getEnvelope()
    }

    ReceivedEnvelopeDetailScreen(
        uiState = uiState,
        onClickEdit = navigateReceivedEnvelopeEdit,
        onClickDelete = viewModel::showDeleteDialog,
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
            // TODO: text 수정
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
                    text = "150,000원",
                    style = SusuTheme.typography.title_xxl,
                    color = Gray100,
                )
                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))
                Column {
                    DetailItem(
                        categoryText = "이름",
                        contentText = "김철수",
                        isEmptyContent = false,
                    )
                    DetailItem(
                        categoryText = "나와의 관계",
                        contentText = "친구",
                        isEmptyContent = false,
                    )
                    DetailItem(
                        categoryText = "방문 여부",
                        contentText = "예",
                        isEmptyContent = false,
                    )
                    DetailItem(
                        categoryText = "선물",
                        contentText = "한끼 식사",
                        isEmptyContent = false,
                    )
                    DetailItem(
                        categoryText = "연락처",
                        contentText = "01012345678",
                        isEmptyContent = false,
                    )
                    DetailItem(
                        categoryText = "메모",
                        contentText = "가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하",
                        isEmptyContent = false,
                    )
                }
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
