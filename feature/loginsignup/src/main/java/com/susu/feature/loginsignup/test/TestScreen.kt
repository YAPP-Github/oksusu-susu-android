package com.susu.feature.loginsignup.test

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TestScreen(
    viewModel: TestViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
) {
    LaunchedEffect(key1 = viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                TestContract.TestEffect.NavigateToLogin -> navigateToLogin()
            }
        }
    }

    Column {
        Button(onClick = viewModel::logout) {
            Text(text = "로그아웃")
        }
        Button(
            onClick = viewModel::withdraw,
        ) {
            Text(text = "탈퇴")
        }
    }
}
