package com.susu.feature.envelopeadd

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.susu.core.ui.extension.susuDefaultAnimatedContentTransitionSpec
import com.susu.feature.envelopeadd.content.DateContent
import com.susu.feature.envelopeadd.content.EventContent
import com.susu.feature.envelopeadd.content.MemoContent
import com.susu.feature.envelopeadd.content.money.MoneyContent
import com.susu.feature.envelopeadd.content.MoreContent
import com.susu.feature.envelopeadd.content.NameContent
import com.susu.feature.envelopeadd.content.PhoneContent
import com.susu.feature.envelopeadd.content.PresentContent
import com.susu.feature.envelopeadd.content.RelationshipContent
import com.susu.feature.envelopeadd.content.VisitedContent
import com.susu.feature.envelopeadd.content.money.MoneyContentRoute
import com.susu.feature.sent.R

@Composable
fun SentEnvelopeAddRoute(
    viewModel: EnvelopeAddViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler {
        viewModel.goPrevStep()
    }

    SentEnvelopeAddScreen(
        uiState = uiState,
        onClickBack = viewModel::goPrevStep,
        onClickNext = viewModel::goNextStep,
        updateParentMoney = viewModel::updateMoney,
    )
}

@Composable
fun SentEnvelopeAddScreen(
    uiState: EnvelopeAddState = EnvelopeAddState(),
    onClickBack: () -> Unit = {},
    onClickNext: () -> Unit = {},
    updateParentMoney: (Long) -> Unit = {},
) {
    // TODO: 수정 필요
    val relationshipList = listOf("친구", "가족", "친척", "동료", "직접 입력")
    val friendList = listOf("김철수", "국영수", "신짱구", "홍길동")
    val eventList = listOf("결혼식", "돌잔치", "장례식", "생일 기념일", "직접 입력")
    val moreList = listOf("방문여부", "선물", "메모", "보낸 이의 연락처")
    val visitedList = listOf("예", "아니요")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SusuTheme.colorScheme.background15),
    ) {
        SusuProgressAppBar(
            leftIcon = {
                BackIcon(
                    onClick = onClickBack,
                )
            },
            currentStep = uiState.currentStep.ordinal + 1,
            entireStep = EnvelopeAddStep.entries.size,
        )
        AnimatedContent(
            modifier = Modifier.weight(1f),
            targetState = uiState.currentStep,
            label = "SentEnvelopeAddScreen",
            transitionSpec = {
                susuDefaultAnimatedContentTransitionSpec(
                    leftDirectionCondition = targetState.ordinal > initialState.ordinal,
                )
            },
        ) { targetState ->
            when (targetState) {
                EnvelopeAddStep.MONEY -> MoneyContentRoute(updateParentMoney = updateParentMoney)
                EnvelopeAddStep.NAME -> NameContent(friendList = friendList)
                EnvelopeAddStep.RELATIONSHIP -> RelationshipContent(relationshipList = relationshipList)
                EnvelopeAddStep.EVENT -> EventContent(eventList = eventList)
                EnvelopeAddStep.DATE -> DateContent(name = "김철수")
                EnvelopeAddStep.MORE -> MoreContent(moreList = moreList)
                EnvelopeAddStep.VISITED -> VisitedContent(
                    event = "결혼식",
                    visitedList = visitedList,
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
            text = stringResource(R.string.sent_envelope_add_next),
            onClick = onClickNext,
            modifier = Modifier
                .fillMaxWidth()
                .imePadding(),
        )
    }
}

@Preview
@Composable
fun SentEnvelopeAddScreenPreview() {
    SusuTheme {
        SentEnvelopeAddScreen()
    }
}
