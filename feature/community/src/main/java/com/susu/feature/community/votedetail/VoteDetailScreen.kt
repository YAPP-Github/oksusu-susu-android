package com.susu.feature.community.votedetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.susu.core.designsystem.component.appbar.SusuDefaultAppBar
import com.susu.core.designsystem.component.appbar.icon.BackIcon
import com.susu.core.designsystem.component.appbar.icon.DeleteText
import com.susu.core.designsystem.component.appbar.icon.EditText
import com.susu.core.designsystem.component.badge.BadgeColor
import com.susu.core.designsystem.component.badge.BadgeStyle
import com.susu.core.designsystem.component.badge.SusuBadge
import com.susu.core.designsystem.component.screen.LoadingScreen
import com.susu.core.designsystem.theme.Gray15
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.model.Vote
import com.susu.core.ui.extension.collectWithLifecycle
import com.susu.core.ui.extension.susuClickable
import com.susu.core.ui.util.to_yyyy_dot_MM_dot_dd
import com.susu.feature.community.R
import com.susu.feature.community.votedetail.component.VoteItem
import kotlinx.coroutines.delay
import kotlinx.datetime.toJavaLocalDateTime
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Composable
fun VoteDetailRoute(
    viewModel: VoteDetailViewModel = hiltViewModel(),
    popBackStackWithToUpdateVote: (String) -> Unit,
    navigateVoteEdit: (Vote) -> Unit,
    handleException: (Throwable, () -> Unit) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    viewModel.sideEffect.collectWithLifecycle { sideEffect ->
        when (sideEffect) {
            is VoteDetailSideEffect.HandleException -> handleException(sideEffect.throwable, sideEffect.retry)
            is VoteDetailSideEffect.PopBackStackWithToUpdateVote -> popBackStackWithToUpdateVote(sideEffect.vote)
            is VoteDetailSideEffect.NavigateVoteEdit -> navigateVoteEdit(sideEffect.vote)
        }
    }

    var currentTime by remember {
        mutableStateOf(LocalDateTime.now())
    }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(60 * 1000L)
            currentTime = currentTime.plusMinutes(1)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getVoteDetail()
    }

    BackHandler {
        viewModel.popBackStack()
    }

    VoteDetailScreen(
        uiState = uiState,
        currentTime = currentTime,
        onClickBack = viewModel::popBackStack,
        onClickEdit = viewModel::navigateVoteEdit,
        onClickOption = viewModel::vote,
    )
}

@Composable
fun VoteDetailScreen(
    uiState: VoteDetailState = VoteDetailState(),
    currentTime: LocalDateTime = LocalDateTime.now(),
    onClickBack: () -> Unit = {},
    onClickReport: () -> Unit = {},
    onClickEdit: () -> Unit = {},
    onClickDelete: () -> Unit = {},
    onClickOption: (Long, Boolean) -> Unit = { _, _ -> },
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SusuTheme.colorScheme.background10),
    ) {
        SusuDefaultAppBar(
            leftIcon = {
                BackIcon(
                    onClick = onClickBack,
                )
            },
            title = uiState.vote.boardName,
            actions = {
                if (uiState.vote.isMine) {
                    EditText(
                        onClick = onClickEdit,
                    )
                    Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))
                    DeleteText(
                        onClick = onClickDelete,
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .size(24.dp)
                            .susuClickable(rippleEnabled = false, onClick = onClickReport),
                        painter = painterResource(id = R.drawable.ic_report),
                        contentDescription = stringResource(id = com.susu.core.ui.R.string.content_description_report_button),
                    )
                }
                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))
            },
        )

        Column(
            modifier = Modifier
                .padding(SusuTheme.spacing.spacing_m)
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(20.dp)
                        .border(width = 1.dp, color = Gray15, shape = CircleShape),
                    painter = painterResource(id = com.susu.core.ui.R.drawable.img_default_profile),
                    contentDescription = null,
                )

                Text(text = stringResource(R.string.word_anonymous_susu), style = SusuTheme.typography.title_xxxs)

                if (uiState.vote.isMine) {
                    SusuBadge(
                        color = BadgeColor.Gray20,
                        text = stringResource(R.string.word_me),
                        padding = BadgeStyle.extraSmallBadge,
                    )
                }
            }

            Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = uiState.vote.content,
                style = SusuTheme.typography.text_xxs,
            )

            Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xl))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_vote),
                    contentDescription = null,
                    tint = Orange60,
                )

                Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_xxxxs))

                Text(
                    text = stringResource(R.string.vote_card_count, uiState.vote.count),
                    style = SusuTheme.typography.title_xxxs,
                    color = Orange60,
                )

                Spacer(modifier = Modifier.weight(1f))

                val totalMinutes = ChronoUnit.MINUTES.between(uiState.vote.createdAt.toJavaLocalDateTime(), currentTime)

                val writeTime = when {
                    totalMinutes / 60 > 24 -> uiState.vote.createdAt.toJavaLocalDateTime().to_yyyy_dot_MM_dot_dd()
                    totalMinutes / 60 > 0 -> stringResource(R.string.word_before_hour, totalMinutes / 60)
                    totalMinutes / 60 == 0L -> stringResource(R.string.word_before_minute, totalMinutes % 60 + 1)
                    else -> uiState.vote.createdAt.toJavaLocalDateTime().to_yyyy_dot_MM_dot_dd()
                }

                Text(
                    text = writeTime,
                    style = SusuTheme.typography.text_xxxs,
                    color = Gray50,
                )
            }

            Spacer(modifier = Modifier.size(SusuTheme.spacing.spacing_m))

            Column(
                verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
            ) {
                uiState.vote.optionList.forEach {
                    VoteItem(
                        title = it.content,
                        isPick = it.isVoted,
                        voteCount = it.count,
                        totalVoteCount = uiState.vote.count,
                        showResult = uiState.vote.optionList.any { it.isVoted },
                        onClick = { onClickOption(it.id, it.isVoted) },
                    )
                }
            }
        }
    }

    if (uiState.isLoading) {
        LoadingScreen()
    }
}

@Preview
@Composable
fun VoteDetailScreenPreview() {
    SusuTheme {
        VoteDetailScreen()
    }
}
