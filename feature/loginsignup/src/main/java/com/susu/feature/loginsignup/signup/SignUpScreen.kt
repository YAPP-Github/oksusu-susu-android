package com.susu.feature.loginsignup.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateToReceived: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                SignUpContract.SignUpEffect.NavigateToReceived -> {
                    Toast.makeText(context, "가입 성공", Toast.LENGTH_SHORT).show()
                    navigateToReceived()
                }

                is SignUpContract.SignUpEffect.ShowToast -> {
                    Toast.makeText(context, sideEffect.msg, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column {
        TextField(
            value = uiState.name,
            onValueChange = viewModel::updateName,
            label = { Text(text = "이름") },
        )
        TextField(
            value = uiState.gender,
            onValueChange = viewModel::updateGender,
            label = {
                Text(text = "성별 (M/F)")
            },
        )
        TextField(
            value = uiState.birth,
            onValueChange = { viewModel.updateBirth(it) },
            label = { Text(text = "출생년도 (1930 ~ 2030)") },

        )
        Button(onClick = viewModel::signUp) {
            Text(text = "회원가입")
        }
    }
}
