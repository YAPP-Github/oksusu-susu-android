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
) : BaseViewModel<SignUpState, SignUpEffect>(SignUpState()) {

    fun updateName(name: String) {
        val trimmedName = name.trim()
        val slicedName = if (trimmedName.length > 10) trimmedName.slice(0 until 10) else trimmedName

        intent { copy(name = slicedName, isNameValid = nameRegex.matches(slicedName)) }
    }

    fun updateGender(gender: Gender) {
        intent { copy(gender = gender) }
    }

    fun updateBirth(birth: Int) {
        intent { copy(birth = birth) }
    }

    fun agreeTerm(termId: Int) {
        intent { copy(agreedTerms = agreedTerms + termId) }
    }

    fun disagreeTerm(termId: Int) {
        intent { copy(agreedTerms = agreedTerms - termId) }
    }

    fun agreeAllTerms(entireTermIds: List<Int>) {
        intent { copy(agreedTerms = entireTermIds) }
    }

    fun disagreeAllTerms() {
        intent { copy(agreedTerms = emptyList()) }
    }

    fun goNextStep() {
        when (uiState.value.currentStep) {
            SignUpStep.TERMS -> intent { copy(currentStep = SignUpStep.NAME) }
            SignUpStep.TERM_DETAIL -> intent { copy(currentStep = SignUpStep.TERMS) }
            SignUpStep.NAME -> intent { copy(currentStep = SignUpStep.ADDITIONAL) }
            SignUpStep.ADDITIONAL -> signUp()
        }
    }

    fun goPreviousStep() {
        when (uiState.value.currentStep) {
            SignUpStep.TERMS -> postSideEffect(SignUpEffect.NavigateToLogin)
            SignUpStep.TERM_DETAIL -> intent { copy(currentStep = SignUpStep.TERMS) }
            SignUpStep.NAME -> intent { copy(currentStep = SignUpStep.TERMS) }
            SignUpStep.ADDITIONAL -> intent { copy(currentStep = SignUpStep.NAME) }
        }
    }

    fun goTermDetail() {
        intent { copy(currentStep = SignUpStep.TERM_DETAIL) }
    }

    private fun signUp() {
        KakaoLoginHelper.getAccessToken { oauthAccessToken ->
            viewModelScope.launch {
                intent { copy(isLoading = true) }
                if (oauthAccessToken != null) {
                    signUpUseCase(
                        oauthAccessToken = oauthAccessToken,
                        user = User(
                            name = uiState.value.name,
                            gender = uiState.value.gender.content,
                            birth = uiState.value.birth,
                            termAgreement = uiState.value.agreedTerms
                        ),
                    ).onSuccess {
                        postSideEffect(SignUpEffect.NavigateToReceived)
                    }.onFailure {
                        postSideEffect(SignUpEffect.ShowToast(it.message ?: "에러 발생"))
                    }
                } else {
                    postSideEffect(SignUpEffect.ShowToast("카카오톡 로그인 에러 발생"))
                }
                intent { copy(isLoading = false) }
            }
        }
    }

    companion object {
        private val nameRegex = Regex("[a-zA-Z가-힣]{0,10}")
    }
}
