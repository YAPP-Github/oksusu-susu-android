package com.susu.feature.envelopeadd

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
import com.susu.feature.envelopeadd.content.CategoryContent
import com.susu.feature.envelopeadd.content.DateContent
import com.susu.feature.envelopeadd.content.InputContent
import com.susu.feature.envelopeadd.content.MoneyContent
import com.susu.feature.sent.R

enum class EnvelopeAddStep {
    MONEY,
    NAME,
    RELATIONSHIP,
    EVENT,
    DATE,
    MORE,
    VISITED,
    PRESENT,
    PHONE,
    MEMO,
}

@Composable
fun SentEnvelopeAddRoute(
    popBackStack: () -> Unit,
) {
    var currentStep by remember { mutableStateOf(EnvelopeAddStep.MONEY) }

    SentEnvelopeAddScreen(
        currentStep = currentStep,
        onClickBack = popBackStack,
        onClickNext = {
            // TODO: 수정 필요 (MORE 이후 분리 필요)
            currentStep = when (currentStep) {
                EnvelopeAddStep.MONEY -> EnvelopeAddStep.NAME
                EnvelopeAddStep.NAME -> EnvelopeAddStep.RELATIONSHIP
                EnvelopeAddStep.RELATIONSHIP -> EnvelopeAddStep.EVENT
                EnvelopeAddStep.EVENT -> EnvelopeAddStep.DATE
                EnvelopeAddStep.DATE -> EnvelopeAddStep.MORE
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
fun SentEnvelopeAddScreen(
    modifier: Modifier = Modifier,
    currentStep: EnvelopeAddStep = EnvelopeAddStep.MONEY,
    onClickBack: () -> Unit = {},
    onClickNext: () -> Unit = {},
) {
    // TODO: 수정 필요
    val categoryList = listOf("친구", "가족", "친척", "동료", "직접 입력")
    val friendList = listOf("김철수", "국영수", "신짱구", "홍길동")
    val eventList = listOf("결혼식", "돌잔치", "장례식", "생일 기념일", "직접 입력")
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
                EnvelopeAddStep.NAME -> InputContent(
                    titleText = stringResource(R.string.sent_envelope_add_name_title),
                    placeholder = stringResource(R.string.sent_envelope_add_name_placeholder),
                    friendList = friendList,
                )

                EnvelopeAddStep.RELATIONSHIP -> CategoryContent(
                    titleText = stringResource(R.string.sent_envelope_add_relationship_title),
                    categoryList = categoryList,
                )

                EnvelopeAddStep.EVENT -> CategoryContent(
                    titleText = stringResource(R.string.sent_envelope_add_event_title),
                    categoryList = eventList,
                )

                EnvelopeAddStep.DATE -> DateContent(
                    name = "김철수",
                )

                EnvelopeAddStep.MORE -> CategoryContent(
                    titleText = stringResource(R.string.sent_envelope_add_more_title),
                    categoryList = moreList,
                    subTitleText = stringResource(R.string.sent_envelope_add_more_subtitle),
                )

                EnvelopeAddStep.VISITED -> CategoryContent(
                    titleText = stringResource(R.string.sent_envelope_add_visited_title),
                    categoryList = visitedList,
                )

                EnvelopeAddStep.PRESENT -> InputContent(
                    titleText = stringResource(R.string.sent_envelope_add_present_title),
                    placeholder = stringResource(R.string.sent_envelope_add_present_placeholder),
                )

                EnvelopeAddStep.PHONE -> InputContent(
                    titleText = stringResource(R.string.sent_envelope_add_phone_title),
                    placeholder = stringResource(R.string.sent_envelope_add_phone_placeholder),
                    name = "김철수",
                )

                EnvelopeAddStep.MEMO -> InputContent(
                    titleText = stringResource(R.string.sent_envelope_add_memo_title),
                    placeholder = stringResource(R.string.sent_envelope_add_memo_placeholder),
                )
            }
        }
        SusuFilledButton(
            color = FilledButtonColor.Black,
            style = MediumButtonStyle.height60,
            shape = RectangleShape,
            text = stringResource(R.string.sent_envelope_add_next),
            onClick = onClickNext,
            modifier = modifier
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
