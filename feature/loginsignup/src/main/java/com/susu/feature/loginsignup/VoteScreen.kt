package com.susu.feature.loginsignup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.susu.core.designsystem.component.button.GhostButtonColor
import com.susu.core.designsystem.component.button.MediumButtonStyle
import com.susu.core.designsystem.component.button.SusuGhostButton
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun VoteRoute(navigateToLogin: () -> Unit) {
    VoteScreen(navigateToLogin = navigateToLogin)
}

@Composable
fun VoteScreen(
    navigateToLogin: () -> Unit = {},
) {
    val buttonList = listOf(
        stringResource(R.string.signup_vote_unit_3),
        stringResource(R.string.signup_vote_unit_5),
        stringResource(R.string.signup_vote_unit_10),
        stringResource(R.string.signup_vote_unit_20),
    )
    val transitionState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = transitionState,
        enter = fadeIn(tween(1500)) + slideInVertically(tween(1500), initialOffsetY = { it / 10 }),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = SusuTheme.spacing.spacing_m),

        ) {
            Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxl))
            Text(text = stringResource(R.string.signup_vote_question_1), style = SusuTheme.typography.title_l)
            Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_m))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SusuTheme.typography.title_l.toSpanStyle()) {
                        append(stringResource(R.string.signup_vote_question_2))
                    }
                    withStyle(style = SusuTheme.typography.text_l.toSpanStyle()) {
                        append(stringResource(R.string.signup_vote_question_3))
                    }
                    withStyle(style = SusuTheme.typography.title_l.toSpanStyle()) {
                        append(stringResource(R.string.signup_vote_question_4))
                    }
                    withStyle(style = SusuTheme.typography.text_l.toSpanStyle()) {
                        append(stringResource(R.string.signup_vote_question_5))
                    }
                },
                lineHeight = 40.sp,
            )
            Spacer(modifier = Modifier.height(48.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxs),
            ) {
                buttonList.forEach {
                    SusuGhostButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = it,
                        color = GhostButtonColor.Black,
                        style = MediumButtonStyle.height60,
                        isActive = false,
                        isClickable = true,
                        onClick = navigateToLogin,
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                painter = painterResource(id = com.susu.core.designsystem.R.drawable.ic_susu_logo_weak),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xl))
        }
    }
}

@Preview
@Composable
fun VoteScreenPreview() {
    SusuTheme {
        VoteScreen {
        }
    }
}
