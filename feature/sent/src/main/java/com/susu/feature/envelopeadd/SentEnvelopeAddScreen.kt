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
import com.susu.core.model.Category
import com.susu.core.model.Relationship
import com.susu.core.ui.extension.susuDefaultAnimatedContentTransitionSpec
import com.susu.feature.envelopeadd.content.MemoContent
import com.susu.feature.envelopeadd.content.PhoneContent
import com.susu.feature.envelopeadd.content.PresentContent
import com.susu.feature.envelopeadd.content.category.CategoryContentRoute
import com.susu.feature.envelopeadd.content.date.DateContentRoute
import com.susu.feature.envelopeadd.content.money.MoneyContentRoute
import com.susu.feature.envelopeadd.content.more.MoreContentRoute
import com.susu.feature.envelopeadd.content.name.NameContentRoute
import com.susu.feature.envelopeadd.content.relationship.RelationshipContentRoute
import com.susu.feature.envelopeadd.content.visited.VisitedContentRoute
import com.susu.feature.sent.R
import java.time.LocalDateTime

@Composable
fun SentEnvelopeAddRoute(
    viewModel: EnvelopeAddViewModel = hiltViewModel(),
    popBackStack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var friendName by remember {
        mutableStateOf("")
    }

    var categoryName by remember {
        mutableStateOf("")
    }

    BackHandler {
        viewModel.goPrevStep()
    }

    SentEnvelopeAddScreen(
        uiState = uiState,
        friendName = friendName,
        categoryName = categoryName,
        onClickBack = viewModel::goPrevStep,
        onClickNext = viewModel::goNextStep,
        updateParentMoney = viewModel::updateMoney,
        updateParentName = { name ->
            viewModel.updateName(name)
            friendName = name
        },
        updateParentFriendId = viewModel::updateFriendId,
        updateParentSelectedRelation = viewModel::updateSelectedRelationShip,
        updateParentCategory = { category ->
            viewModel.updateSelectedCategory(category)
            categoryName = category?.name ?: ""
        },
        updateParentDate = viewModel::updateDate,
        updateParentMoreStep = viewModel::updateMoreStep,
        updateParentVisited = viewModel::updateHasVisited,
    )
}

@Composable
fun SentEnvelopeAddScreen(
    uiState: EnvelopeAddState = EnvelopeAddState(),
    friendName: String = "",
    onClickBack: () -> Unit = {},
    onClickNext: () -> Unit = {},
    updateParentMoney: (Long) -> Unit = {},
    updateParentName: (String) -> Unit = {},
    updateParentFriendId: (Long?) -> Unit = {},
    updateParentSelectedRelation: (Relationship?) -> Unit = {},
    updateParentCategory: (Category?) -> Unit = {},
    updateParentDate: (LocalDateTime?) -> Unit = {},
    updateParentMoreStep: (List<EnvelopeAddStep>) -> Unit = {},
    categoryName: String = "",
    updateParentVisited: (Boolean?) -> Unit = {},
) {
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
                EnvelopeAddStep.NAME -> NameContentRoute(
                    updateParentName = updateParentName,
                    updateParentFriendId = updateParentFriendId,
                )

                EnvelopeAddStep.RELATIONSHIP -> RelationshipContentRoute(
                    updateParentSelectedRelation = updateParentSelectedRelation,
                )

                EnvelopeAddStep.EVENT -> CategoryContentRoute(
                    updateParentCategory = updateParentCategory,
                )

                EnvelopeAddStep.DATE -> DateContentRoute(
                    friendName = friendName,
                    updateParentDate = updateParentDate,
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
