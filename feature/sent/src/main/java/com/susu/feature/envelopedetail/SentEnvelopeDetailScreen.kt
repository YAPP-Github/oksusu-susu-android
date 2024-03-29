package com.susu.feature.envelopedetail

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
import com.susu.core.model.EnvelopeDetail
import com.susu.core.ui.DialogToken
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.toMoneyFormat
import com.susu.core.ui.extension.toPhoneNumber
import com.susu.core.ui.util.to_yyyy_korYear_M_korMonth_d_korDay
import com.susu.feature.envelopedetail.component.DetailItem
import com.susu.feature.sent.R
import kotlinx.datetime.toJavaLocalDateTime

@Composable
fun SentEnvelopeDetailRoute(
    viewModel: SentEnvelopeDetailViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    popBackStackWithEditedFriendId: (Long) -> Unit,
    editedFriendId: Long?,
    navigateSentEnvelopeEdit: (EnvelopeDetail) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
    onShowDialog: (DialogToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            SentEnvelopeDetailEffect.PopBackStack -> {
                editedFriendId?.let { popBackStackWithEditedFriendId(it) } ?: popBackStack()
            }

            is SentEnvelopeDetailEffect.NavigateEnvelopeEdit -> navigateSentEnvelopeEdit(sideEffect.envelopeDetail)
            SentEnvelopeDetailEffect.ShowDeleteDialog -> onShowDialog(
                DialogToken(
                    title = context.getString(R.string.sent_envelope_detail_delete_title),
                    text = context.getString(R.string.sent_envelope_detail_delete_description),
                    confirmText = context.getString(com.susu.core.ui.R.string.word_delete),
                    dismissText = context.getString(com.susu.core.ui.R.string.word_cancel),
                    onConfirmRequest = viewModel::deleteEnvelope,
                ),
            )

            SentEnvelopeDetailEffect.ShowDeleteSuccessSnackBar -> onShowSnackbar(
                SnackbarToken(
                    message = context.getString(R.string.sent_envelope_detail_delete_snackbar),
                ),
            )

            is SentEnvelopeDetailEffect.HandleException -> handleException(sideEffect.throwable, sideEffect.retry)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getEnvelopeDetail()
    }

    BackHandler {
        editedFriendId?.let { popBackStackWithEditedFriendId(it) } ?: popBackStack()
    }

    SentEnvelopeDetailScreen(
        uiState = uiState,
        onClickBackIcon = viewModel::popBackStack,
        onClickEdit = viewModel::navigateSentEnvelopeEdit,
        onClickDelete = viewModel::showDeleteDialog,
    )
}

@Composable
fun SentEnvelopeDetailScreen(
    modifier: Modifier = Modifier,
    uiState: SentEnvelopeDetailState = SentEnvelopeDetailState(),
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
                with(uiState.envelopeDetail) {
                    Text(
                        text = envelope.amount.toMoneyFormat() + stringResource(R.string.sent_envelope_card_money_won),
                        style = SusuTheme.typography.title_xxl,
                        color = Gray100,
                    )
                    Spacer(modifier = modifier.size(SusuTheme.spacing.spacing_m))
                    Column {
                        DetailItem(
                            categoryText = stringResource(com.susu.core.ui.R.string.word_event),
                            contentText = if (category.customCategory != null) {
                                category.customCategory!!
                            } else {
                                category.category
                            },
                            isEmptyContent = category.category.isEmpty(),
                        )
                        DetailItem(
                            categoryText = stringResource(com.susu.core.ui.R.string.word_name),
                            contentText = friend.name,
                            isEmptyContent = friend.name.isEmpty(),
                        )
                        DetailItem(
                            categoryText = stringResource(com.susu.core.ui.R.string.word_relationship),
                            contentText = if (friendRelationship.customRelation != null) {
                                friendRelationship.customRelation!!
                            } else {
                                relationship.relation
                            },
                            isEmptyContent = relationship.relation.isEmpty(),
                        )
                        DetailItem(
                            categoryText = stringResource(com.susu.core.ui.R.string.word_date),
                            contentText = envelope.handedOverAt.toJavaLocalDateTime().to_yyyy_korYear_M_korMonth_d_korDay(),
                        )
                        DetailItem(
                            categoryText = stringResource(com.susu.core.ui.R.string.word_is_visited),
                            contentText = if (envelope.hasVisited == true) {
                                stringResource(com.susu.core.ui.R.string.word_yes)
                            } else {
                                stringResource(com.susu.core.ui.R.string.word_no)
                            },
                            isEmptyContent = envelope.hasVisited == null,
                        )
                        DetailItem(
                            categoryText = stringResource(com.susu.core.ui.R.string.word_gift),
                            contentText = envelope.gift ?: "",
                            isEmptyContent = envelope.gift.isNullOrEmpty(),
                        )
                        DetailItem(
                            categoryText = stringResource(com.susu.core.ui.R.string.word_phone_number),
                            contentText = if (friend.phoneNumber.isNullOrEmpty()) "" else friend.phoneNumber.toPhoneNumber(),
                            isEmptyContent = friend.phoneNumber.isEmpty(),
                        )
                        DetailItem(
                            categoryText = stringResource(com.susu.core.ui.R.string.word_memo),
                            contentText = envelope.memo ?: "",
                            isEmptyContent = envelope.memo.isNullOrEmpty(),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxxl))
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
