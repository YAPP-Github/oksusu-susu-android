package com.susu.feature.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.susu.core.designsystem.theme.SusuTheme

@Composable
fun MyPageScreen(
    padding: PaddingValues,
    viewModel: MyPageViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
) {
    LaunchedEffect(key1 = viewModel.sideEffect) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                MyPageContract.MyPageEffect.NavigateToLogin -> navigateToLogin()
                is MyPageContract.MyPageEffect.ShowToast -> {
                    //TODO: UI 작업 시 에러 메세지 표시
                    navigateToLogin()
                }
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

@Preview
@Composable
fun MyPageScreenPreview() {
    SusuTheme {
        MyPageScreen(padding = PaddingValues(0.dp)) {}
    }
}
