package com.susu.feature.received.envelopeadd

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.susu.core.designsystem.component.appbar.SusuProgressAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.button.FilledButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SusuFilledButton
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuDefaultAnimatedContentTransitionSpec
import com.susu.feature.received.R
import com.susu.feature.received.envelopeadd.content.MemoContent
import com.susu.feature.received.envelopeadd.content.MoneyContent
import com.susu.feature.received.envelopeadd.content.MoreContent
import com.susu.feature.received.envelopeadd.content.NameContent
import com.susu.feature.received.envelopeadd.content.PhoneContent
import com.susu.feature.received.envelopeadd.content.PresentContent
import com.susu.feature.received.envelopeadd.content.RelationshipContent
import com.susu.feature.received.envelopeadd.content.VisitedContent

enum class EnvelopeAddStep {
    MONEY,
    NAME,
    RELATIONSHIP,
    MORE,
    VISITED,
    PRESENT,
    PHONE,
    MEMO,
}

@Composable
fun ReceivedEnvelopeAddRoute(
    popBackStack: () -> Unit,
) {
    var currentStep by remember { mutableStateOf(EnvelopeAddStep.MONEY) }

    ReceivedEnvelopeAddScreen(
        currentStep = currentStep,
        onClickBack = popBackStack,
        onClickNext = {
            // TODO: 수정 필요 (MORE 이후 분리 필요)
            currentStep = when (currentStep) {
                EnvelopeAddStep.MONEY -> EnvelopeAddStep.NAME
                EnvelopeAddStep.NAME -> EnvelopeAddStep.RELATIONSHIP
                EnvelopeAddStep.RELATIONSHIP -> EnvelopeAddStep.MORE
                EnvelopeAddStep.MORE -> EnvelopeAddStep.VISITED
                EnvelopeAddStep.VISITED -> EnvelopeAddStep.PRESENT
                EnvelopeAddStep.PRESENT -> EnvelopeAddStep.PHONE
                EnvelopeAddStep.PHONE -> EnvelopeAddStep.MEMO
                else -> EnvelopeAddStep.MEMO
            }
        },
    )
}

@Composable
fun ReceivedEnvelopeAddScreen(
    modifier: Modifier = Modifier,
    currentStep: EnvelopeAddStep = EnvelopeAddStep.MONEY,
    onClickBack: () -> Unit = {},
    onClickNext: () -> Unit = {},
) {
    // TODO: 수정 필요
    val relationshipList = listOf("친구", "가족", "친척", "동료", "직접 입력")
    val friendList = listOf("김철수", "국영수", "신짱구", "홍길동")
    val moreList = listOf("방문여부", "선물", "메모", "보낸 이의 연락처")
    val visitedList = listOf("예", "아니요")

    Column(
        modifier = modifier
            .background(SusuTheme.colorScheme.background15)
            .fillMaxSize(),
    ) {
        SusuProgressAppBar(
            leftIcon = {
                BackIcon(
                    onClick = onClickBack,
                )
            },
            currentStep = currentStep.ordinal + 1,
            entireStep = EnvelopeAddStep.entries.size,
        )
        AnimatedContent(
            modifier = modifier.weight(1f),
            targetState = currentStep,
            label = "SentEnvelopeAddScreen",
            transitionSpec = {
                susuDefaultAnimatedContentTransitionSpec(
                    leftDirectionCondition = targetState.ordinal > initialState.ordinal,
                )
            },
        ) { targetState ->
            when (targetState) {
                EnvelopeAddStep.MONEY -> MoneyContent()
                EnvelopeAddStep.NAME -> NameContent(friendList = friendList)
                EnvelopeAddStep.RELATIONSHIP -> RelationshipContent(relationshipList = relationshipList)
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
            text = stringResource(id = com.susu.core.ui.R.string.word_next),
            onClick = onClickNext,
            modifier = modifier
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
