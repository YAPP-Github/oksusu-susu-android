package com.susu.feature.envelopeadd

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
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

enum class EnvelopeAddStep {
    MONEY,
    NAME,
    RELATIONSHIP,
    EVENT,
    DATE,
    MORE,
    VISITED,
    PRESENT,
    MEMO,
    PHONE,
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
                    titleText = "누구에게 보냈나요",
                    placeholder = "이름을 입력해주세요",
                    friendList = friendList
                )
                EnvelopeAddStep.RELATIONSHIP -> CategoryContent(
                    titleText = "나와는\n어떤 사이 인가요",
                    categoryList = categoryList,
                )
                EnvelopeAddStep.EVENT -> CategoryContent(
                    titleText = "어떤 경조였나요",
                    categoryList = eventList,
                )
                EnvelopeAddStep.DATE -> DateContent(
                    titleText = "언제 보냈나요",
                    name = "김철수",
                )
                EnvelopeAddStep.MORE -> CategoryContent(
                    titleText = "더 기록할 내용이 있다면 알려주세요",
                    categoryList = moreList,
                    hasSubTitle = true,
                    subTitleText = "복수로 선택하셔도 좋아요",
                )
                EnvelopeAddStep.VISITED -> CategoryContent(
                    titleText = "방문했나요?",
                    categoryList = visitedList
                )
                EnvelopeAddStep.PRESENT -> InputContent(
                    titleText = "보낸 선물을 알려주세요",
                    placeholder = "무엇을 선물했나요"
                )
                EnvelopeAddStep.PHONE -> InputContent(
                    titleText = "연락처를 남겨주세요",
                    placeholder = "01012345678",
                    name = "김철수"
                )
                EnvelopeAddStep.MEMO -> InputContent(
                    titleText = "추가로 남기실 내용이 있나요",
                    placeholder = "입력해주세요"
                )
            }
        }
        SusuFilledButton(
            color = FilledButtonColor.Black,
            style = MediumButtonStyle.height60,
            shape = RectangleShape,
            text = "다음",
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
