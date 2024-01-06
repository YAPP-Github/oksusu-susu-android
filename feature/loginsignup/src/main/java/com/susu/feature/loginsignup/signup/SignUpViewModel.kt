package com.susu.feature.loginsignup.signup

import androidx.lifecycle.viewModelScope
import com.susu.core.model.User
import com.susu.core.ui.base.BaseViewModel
import com.susu.domain.usecase.loginsignup.SignUpUseCase
import com.susu.feature.loginsignup.social.KakaoLoginHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
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
        KakaoLoginHelper.getAccessToken { oauthAccessToken ->
            viewModelScope.launch {
                if (oauthAccessToken != null) {
                    signUpUseCase(
                        oauthAccessToken = oauthAccessToken,
                        user = User(
                            name = uiState.value.name,
                            gender = uiState.value.gender,
                            birth = uiState.value.birth.toInt(),
                        ),
                    ).onSuccess {
                        postSideEffect(SignUpContract.SignUpEffect.NavigateToReceived)
                    }.onFailure {
                        postSideEffect(SignUpContract.SignUpEffect.ShowToast(it.message ?: "에러 발생"))
                    }
                } else {
                    postSideEffect(SignUpContract.SignUpEffect.ShowToast("카카오톡 로그인 에러 발생"))
                }
            }
        }
    }
}
