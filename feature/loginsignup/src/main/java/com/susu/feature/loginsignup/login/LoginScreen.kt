package com.susu.feature.loginsignup.login

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.susu.core.designsystem.component.util.LoadingScreen
import com.susu.core.designsystem.theme.Gray10
import com.susu.core.designsystem.theme.Gray50
import com.susu.core.designsystem.theme.Orange60
import com.susu.core.designsystem.theme.SusuTheme
import com.susu.core.ui.extension.susuClickable
import com.susu.feature.loginsignup.R
import com.susu.feature.loginsignup.social.KakaoLoginHelper

@Composable
fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToReceived: () -> Unit,
    navigateToSignUp: () -> Unit,
) {
    val context = LocalContext.current
    // TODO: Loading 처리
    val uiState by viewModel.uiState.collectAsState()
    val transitionState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    LaunchedEffect(key1 = viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is LoginContract.LoginEffect.ShowToast -> Toast.makeText(context, sideEffect.msg, Toast.LENGTH_SHORT).show()
                LoginContract.LoginEffect.NavigateToReceived -> navigateToReceived()
                LoginContract.LoginEffect.NavigateToSignUp -> navigateToSignUp()
            }
        }
    }

    LoginScreen(
        uiState = uiState,
        transitionState = transitionState,
        onLoginClick = {
            KakaoLoginHelper.login(
                context = context,
                onSuccess = { viewModel.login(it) },
                onFailed = { viewModel.showToast(it) },
            )
        },
    )
}

@Composable
fun LoginScreen(
    uiState: LoginContract.LoginState = LoginContract.LoginState(),
    transitionState: MutableTransitionState<Boolean> = MutableTransitionState(true),
    onLoginClick: () -> Unit = {},
) {
    AnimatedVisibility(
        visibleState = transitionState,
        enter = fadeIn(tween(1500)) + slideInVertically(tween(1500), initialOffsetY = { it / 10 }),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = SusuTheme.spacing.spacing_m),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(56.dp))
                Text(text = stringResource(R.string.login_header), style = SusuTheme.typography.title_xl)
                Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxxxs))
                Text(text = stringResource(R.string.login_sub_header), style = SusuTheme.typography.title_m, color = Gray50)
                Spacer(modifier = Modifier.height(64.dp))
                LoginArchGraph(modifier = Modifier.size(200.dp))
                Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxxxl))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxxxs),
                ) {
                    Text(text = stringResource(R.string.login_statistics_1), style = SusuTheme.typography.title_s)
                    LoginBlankText(text = "87%", state = transitionState) // TODO: 임시 하드코딩
                    Text(text = stringResource(R.string.login_statistics_2), style = SusuTheme.typography.title_s)
                }
                Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xxs))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(SusuTheme.spacing.spacing_xxxxs),
                ) {
                    LoginBlankText(text = "10만원", state = transitionState) // TODO: 임시 하드코딩
                    Text(text = stringResource(R.string.login_statistics_3), style = SusuTheme.typography.title_s)
                }
                Spacer(modifier = Modifier.height(64.dp))
                KakaoLoginButton(onClick = onLoginClick)
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    painter = painterResource(id = com.susu.core.designsystem.R.drawable.ic_susu_logo_weak),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.height(SusuTheme.spacing.spacing_xl))
            }

            if (uiState.isLoading) {
                LoadingScreen(
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    }
}

@Composable
fun LoginBlankText(
    modifier: Modifier = Modifier,
    text: String = "",
    delay: Int = 2000,
    duration: Int = 1000,
    state: MutableTransitionState<Boolean>,
) {
    Box(
        modifier = modifier
            .background(color = Gray10, shape = RoundedCornerShape(4.dp))
            .padding(horizontal = SusuTheme.spacing.spacing_s),
    ) {
        AnimatedVisibility(
            visibleState = state,
            enter = fadeIn(tween(delayMillis = delay, durationMillis = duration)),
        ) {
            Text(
                text = text,
                style = SusuTheme.typography.title_m,
                color = Orange60,
            )
        }
    }
}

@Composable
fun KakaoLoginButton(
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .background(color = Color(0xFFFEE500), shape = RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .susuClickable(
                rippleEnabled = true, onClick = onClick,
            )
            .padding(
                horizontal = SusuTheme.spacing.spacing_m, vertical = SusuTheme.spacing.spacing_xm,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(36.dp),
            painter = painterResource(id = R.drawable.ic_kakao_login),
            contentDescription = null,
        )
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.login_with_kakao),
            style = SusuTheme.typography.text_xs,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    SusuTheme {
        LoginScreen()
    }
}
