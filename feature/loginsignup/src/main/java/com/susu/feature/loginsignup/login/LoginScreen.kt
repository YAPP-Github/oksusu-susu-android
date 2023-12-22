package com.susu.feature.loginsignup.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SplashScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is LoginContract.LoginEffect.ExitProcess -> TODO()
                LoginContract.LoginEffect.NavigateToReceived -> TODO()
                LoginContract.LoginEffect.NavigateToSignUp -> TODO()
            }
        }
    }
}

@Composable
fun SplashVote(onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = onClick
        ) {
            Text(text = "신규 유저 투표 UI")
        }
    }
}
