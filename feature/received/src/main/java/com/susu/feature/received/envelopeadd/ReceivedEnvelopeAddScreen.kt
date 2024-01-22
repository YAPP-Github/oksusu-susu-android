package com.susu.feature.received.envelopeadd

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuProgressAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.RelationShip
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.susuDefaultAnimatedContentTransitionSpec
import com.susu.feature.received.envelopeadd.content.MemoContent
import com.susu.feature.received.envelopeadd.content.PhoneContent
import com.susu.feature.received.envelopeadd.content.PresentContent
import com.susu.feature.received.envelopeadd.content.visited.VisitedContent
import com.susu.feature.received.envelopeadd.content.money.MoneyContentRoute
import com.susu.feature.received.envelopeadd.content.more.MoreContentRoute
import com.susu.feature.received.envelopeadd.content.name.NameContentRoute
import com.susu.feature.received.envelopeadd.content.relationship.RelationShipContentRoute
import com.susu.feature.received.envelopeadd.content.visited.VisitedContentRoute
import com.susu.feature.received.envelopeadd.content.visited.VisitedViewModel

@Composable
fun ReceivedEnvelopeAddRoute(
    viewModel: ReceivedEnvelopeAddViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is ReceivedEnvelopeAddSideEffect.HandleException -> handleException(sideEffect.throwable, sideEffect.retry)
            ReceivedEnvelopeAddSideEffect.PopBackStack -> popBackStack()
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
        onClickBack = viewModel::goToPrevStep,
        onClickNext = viewModel::goToNextStep,
        updateParentMoney = viewModel::updateMoney,
        updateParentName = viewModel::updateName,
        updateParentSelectedRelationShip = viewModel::updateSelectedRelationShip,
        updateParentMoreStep = viewModel::updateMoreStep,
        categoryName = viewModel.categoryName,
        updateParentVisited = viewModel::updateHasVisited,
    )
}

@Composable
fun ReceivedEnvelopeAddScreen(
    uiState: ReceivedEnvelopeAddState = ReceivedEnvelopeAddState(),
    onClickBack: () -> Unit = {},
    onClickNext: () -> Unit = {},
    updateParentMoney: (Long) -> Unit = {},
    updateParentName: (String) -> Unit = {},
    updateParentSelectedRelationShip: (RelationShip?) -> Unit = {},
    updateParentMoreStep: (List<EnvelopeAddStep>) -> Unit = {},
    categoryName: String = "",
    updateParentVisited: (Boolean?) -> Unit = {},
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
                )
                EnvelopeAddStep.RELATIONSHIP -> RelationShipContentRoute(
                    updateParentSelectedRelation = updateParentSelectedRelationShip,
                )
                EnvelopeAddStep.MORE -> MoreContentRoute(
                    updateParentMoreStep = updateParentMoreStep,
                )
                EnvelopeAddStep.VISITED -> VisitedContentRoute(
                    categoryName = categoryName,
                    updateParentVisited = updateParentVisited,
                )

                EnvelopeAddStep.PRESENT -> PresentContent()
                EnvelopeAddStep.PHONE -> PhoneContent(name = "김철수")
                EnvelopeAddStep.MEMO -> MemoContent()
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
