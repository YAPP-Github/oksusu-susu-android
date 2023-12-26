package com.susu.feature.loginsignup.signup

import androidx.lifecycle.viewModelScope
import com.susu.core.model.User
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.repository.AuthRepository
import com.susu.domain.repository.TokenRepository
import com.susu.domain.util.KakaoSdkProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val kakaoSdkProvider: KakaoSdkProvider,
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository,
) : BaseViewModel<SignUpContract.SignUpState, SignUpContract.SignUpEffect>(SignUpContract.SignUpState()) {

    fun updateName(name: String) {
        intent { copy(name = name) }
    }

    fun updateGender(gender: String) {
        intent { copy(gender = gender) }
    }

    fun updateBirth(birth: String) {
        intent { copy(birth = birth) }
    }

    fun signUp() {
        kakaoSdkProvider.getAccessToken {
            viewModelScope.launch {
                if (it != null) {
                    authRepository.signUp(
                        snsAccessToken = it,
                        user = User(
                            name = uiState.value.name,
                            gender = uiState.value.gender,
                            birth = uiState.value.birth.toInt(),
                        ),
                    ).onSuccess {
                        runBlocking { tokenRepository.saveTokens(it) }
                        postSideEffect(SignUpContract.SignUpEffect.NavigateToReceived)
                    }.onFailure {
                        postSideEffect(SignUpContract.SignUpEffect.ShowToast(it.message ?: "에러 발생"))
                    }
                }
            }
        }
    }
}
