package com.susu.feature.received.envelopeadd

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.analytics.FirebaseAnalytics
import com.susu.core.designsystem.component.appbar.SusuProgressAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Relationship
import com.susu.core.ui.SnackbarToken
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.susuDefaultAnimatedContentTransitionSpec
import com.susu.core.ui.util.currentDate
import com.susu.feature.received.envelopeadd.content.date.DateContentRoute
import com.susu.feature.received.envelopeadd.content.memo.MemoContentRoute
import com.susu.feature.received.envelopeadd.content.money.MoneyContentRoute
import com.susu.feature.received.envelopeadd.content.more.MoreContentRoute
import com.susu.feature.received.envelopeadd.content.name.NameContentRoute
import com.susu.feature.received.envelopeadd.content.phone.PhoneContentRoute
import com.susu.feature.received.envelopeadd.content.present.PresentContentRoute
import com.susu.feature.received.envelopeadd.content.relationship.RelationShipContentRoute
import com.susu.feature.received.envelopeadd.content.visited.VisitedContentRoute
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun ReceivedEnvelopeAddRoute(
    viewModel: ReceivedEnvelopeAddViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    popBackStackWithEnvelope: (String) -> Unit,
    onShowSnackbar: (SnackbarToken) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is ReceivedEnvelopeAddSideEffect.HandleException -> handleException(sideEffect.throwable, sideEffect.retry)
            ReceivedEnvelopeAddSideEffect.PopBackStack -> popBackStack()
            is ReceivedEnvelopeAddSideEffect.ShowSnackbar -> onShowSnackbar(SnackbarToken(message = sideEffect.message))
            is ReceivedEnvelopeAddSideEffect.PopBackStackWithEnvelope -> popBackStackWithEnvelope(sideEffect.envelope)
            is ReceivedEnvelopeAddSideEffect.LogClickBackButton -> scope.launch {
                FirebaseAnalytics.getInstance(context).logEvent(
                    FirebaseAnalytics.Event.SELECT_CONTENT,
                    bundleOf(
                        FirebaseAnalytics.Param.CONTENT_TYPE to "received_envelope_add_screen_back_at_${sideEffect.step}",
                    ),
                )
            }
            is ReceivedEnvelopeAddSideEffect.LogClickNextButton -> scope.launch {
                FirebaseAnalytics.getInstance(context).logEvent(
                    FirebaseAnalytics.Event.SELECT_CONTENT,
                    bundleOf(
                        FirebaseAnalytics.Param.CONTENT_TYPE to "received_envelope_add_screen_next_at_${sideEffect.step}",
                    ),
                )
            }
        }
    }

    var friendName by remember {
        mutableStateOf("")
    }

    BackHandler {
        viewModel.goToPrevStep()
    }

    ReceivedEnvelopeAddScreen(
        uiState = uiState,
        onClickBack = {
            viewModel.logBackButtonClickEvent()
            viewModel.goToPrevStep()
        },
        onClickNext = {
            viewModel.logNextButtonClickEvent()
            viewModel.goToNextStep()
        },
        updateParentMoney = viewModel::updateMoney,
        updateParentName = { name ->
            viewModel.updateName(name)
            friendName = name
        },
        updateParentFriendId = viewModel::updateFriendId,
        updateParentSelectedRelationShip = viewModel::updateSelectedRelationShip,
        updateParentMoreStep = viewModel::updateMoreStep,
        initDate = viewModel.initDate,
        updateParentDate = viewModel::updateDate,
        categoryName = viewModel.categoryName,
        updateParentVisited = viewModel::updateHasVisited,
        updateParentPresent = viewModel::updatePresent,
        friendName = friendName,
        updateParentPhoneNumber = viewModel::updatePhoneNumber,
        updateParentMemo = viewModel::updateMemo,
    )
}

@Composable
fun ReceivedEnvelopeAddScreen(
    uiState: ReceivedEnvelopeAddState = ReceivedEnvelopeAddState(),
    onClickBack: () -> Unit = {},
    onClickNext: () -> Unit = {},
    updateParentMoney: (Long) -> Unit = {},
    updateParentName: (String) -> Unit = {},
    updateParentFriendId: (Long?) -> Unit = {},
    updateParentSelectedRelationShip: (Relationship?) -> Unit = {},
    initDate: LocalDateTime = currentDate,
    updateParentDate: (LocalDateTime?) -> Unit = {},
    updateParentMoreStep: (List<EnvelopeAddStep>) -> Unit = {},
    categoryName: String = "",
    updateParentVisited: (Boolean?) -> Unit = {},
    updateParentPresent: (String?) -> Unit = {},
    friendName: String = "",
    updateParentPhoneNumber: (String?) -> Unit = {},
    updateParentMemo: (String?) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .background(SusuTheme.colorScheme.background15)
            .fillMaxSize(),
    ) {
        SusuProgressAppBar(
            leftIcon = {
                BackIcon(
                    onClick = onClickBack,
                )
            },
            currentStep = uiState.progress,
            entireStep = EnvelopeAddStep.entries.size,
        )
        AnimatedContent(
            modifier = Modifier.weight(1f),
            targetState = uiState.currentStep,
            label = "ReceivedEnvelopeAddScreen",
            transitionSpec = {
                susuDefaultAnimatedContentTransitionSpec(
                    leftDirectionCondition = targetState.ordinal > initialState.ordinal,
                )
            },
        ) { targetState ->
            when (targetState) {
                EnvelopeAddStep.MONEY -> MoneyContentRoute(
                    updateParentMoney = updateParentMoney,
                )

                EnvelopeAddStep.NAME -> NameContentRoute(
                    updateParentName = updateParentName,
                    updateParentFriendId = updateParentFriendId,
                )
                EnvelopeAddStep.RELATIONSHIP -> RelationShipContentRoute(
                    updateParentSelectedRelation = updateParentSelectedRelationShip,
                )
                EnvelopeAddStep.DATE -> DateContentRoute(
                    friendName = friendName,
                    initDate = initDate,
                    updateParentDate = updateParentDate,
                )
                EnvelopeAddStep.MORE -> MoreContentRoute(
                    updateParentMoreStep = updateParentMoreStep,
                )
                EnvelopeAddStep.VISITED -> VisitedContentRoute(
                    categoryName = categoryName,
                    updateParentVisited = updateParentVisited,
                )

                EnvelopeAddStep.PRESENT -> PresentContentRoute(
                    updateParentPresent = updateParentPresent,
                )
                EnvelopeAddStep.PHONE -> PhoneContentRoute(
                    friendName = friendName,
                    updateParentPhone = updateParentPhoneNumber,
                )
                EnvelopeAddStep.MEMO -> MemoContentRoute(
                    updateParentMemo = updateParentMemo,
                )
            }
        }
        SusuFilledButton(
            color = FilledButtonColor.Black,
            style = MediumButtonStyle.height60,
            shape = RectangleShape,
            text = stringResource(id = uiState.buttonResId),
            onClick = onClickNext,
            isClickable = uiState.buttonEnabled,
            isActive = uiState.buttonEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .imePadding(),
        )
    }
}

@Preview
@Composable
fun ReceivedEnvelopeAddScreenPreview() {
    SusuTheme {
        ReceivedEnvelopeAddScreen()
    }
}
