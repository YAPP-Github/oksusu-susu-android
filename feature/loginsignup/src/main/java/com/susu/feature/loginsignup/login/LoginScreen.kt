package com.susu.feature.loginsignup.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.susu.feature.loginsignup.KakaoLoginHelper
import com.susu.feature.loginsignup.R

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    kakaoLoginHelper: KakaoLoginHelper,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is LoginContract.LoginEffect.ShowToast -> {
                    Toast.makeText(context, sideEffect.msg, Toast.LENGTH_SHORT).show()
                }

                LoginContract.LoginEffect.NavigateToReceived -> {
                    Toast.makeText(context, "기존 회원 로그인 성공", Toast.LENGTH_SHORT).show()
                }

                LoginContract.LoginEffect.NavigateToSignUp -> {
                    Toast.makeText(context, "신규 회원 가입 창으로", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (uiState.showVote) {
            SplashVote(onClick = viewModel::selectSignUpVote)
        } else {
            KakaoLogin(
                onClick = {
                    kakaoLoginHelper.login(
                        onSuccess = { viewModel.login(it) },
                        onFailed = { viewModel.showToast(it) },
                    )
                },
            )
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun SplashVote(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.wrapContentHeight(),
    ) {
        Text(text = "신규 유저 투표 UI")
    }
}

@Composable
fun KakaoLogin(onClick: () -> Unit) {
    Button(onClick = onClick, modifier = Modifier.wrapContentHeight().fillMaxWidth()) {
        Image(painter = painterResource(id = R.drawable.btn_kakao_login), contentDescription = null)
    }
}
